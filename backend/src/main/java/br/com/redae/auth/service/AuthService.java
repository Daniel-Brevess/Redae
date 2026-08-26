package br.com.redae.auth.service;

import br.com.redae.auth.config.JwtService;
import br.com.redae.auth.dto.AuthResponse;
import br.com.redae.auth.dto.LoginRequest;
import br.com.redae.auth.dto.RegisterRequest;
import br.com.redae.auth.dto.UserResponse;
import br.com.redae.identity.entity.User;
import br.com.redae.identity.repository.UserRepository;
import br.com.redae.shared.error.ApiException;
import java.util.Locale;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final SessionService sessionService;

  public AuthService(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      JwtService jwtService,
      SessionService sessionService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.sessionService = sessionService;
  }

  @Transactional
  public User register(RegisterRequest request) {
    if (!request.password().equals(request.passwordConfirmation())) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "PASSWORD_MISMATCH", "As senhas não conferem.");
    }
    String email = normalizeEmail(request.email());
    if (userRepository.existsByEmail(email)) {
      throw new ApiException(HttpStatus.CONFLICT, "EMAIL_ALREADY_REGISTERED", "Este email já está cadastrado.");
    }
    return userRepository.save(new User(request.name().trim(), email, passwordEncoder.encode(request.password())));
  }

  @Transactional
  public LoginResult login(LoginRequest request) {
    String email = normalizeEmail(request.email());
    User user = userRepository.findByEmail(email).orElse(null);
    if (user == null || !passwordEncoder.matches(request.password(), user.getPasswordHash())) {
      throw new ApiException(HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS", "Email ou senha inválidos.");
    }
    return new LoginResult(
        new AuthResponse(jwtService.createAccessToken(user), jwtService.getAccessTokenSeconds(), UserResponse.from(user)),
        sessionService.create(user));
  }

  public RefreshResult refresh(String rawRefreshToken) {
    var session = sessionService.findActive(rawRefreshToken)
        .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "INVALID_SESSION", "A sessão é inválida ou expirou."));
    String newRefreshToken = sessionService.rotate(session);
    User user = session.getUser();
    return new RefreshResult(
        new AuthResponse(jwtService.createAccessToken(user), jwtService.getAccessTokenSeconds(), UserResponse.from(user)),
        newRefreshToken);
  }

  public void logout(String rawRefreshToken) {
    sessionService.revoke(rawRefreshToken);
  }

  private String normalizeEmail(String email) {
    return email.trim().toLowerCase(Locale.ROOT);
  }

  public record LoginResult(AuthResponse response, String refreshToken) {}
  public record RefreshResult(AuthResponse response, String refreshToken) {}
}
