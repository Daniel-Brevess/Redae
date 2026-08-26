package br.com.redae.auth.service;

import br.com.redae.auth.entity.Session;
import br.com.redae.auth.repository.SessionRepository;
import br.com.redae.identity.entity.User;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
  private final SessionRepository sessionRepository;
  private final long refreshTokenDays;
  private final SecureRandom secureRandom = new SecureRandom();

  public SessionService(
      SessionRepository sessionRepository,
      @Value("${security.jwt.refresh-token-days:30}") long refreshTokenDays) {
    this.sessionRepository = sessionRepository;
    this.refreshTokenDays = refreshTokenDays;
  }

  public String create(User user) {
    String rawToken = newToken();
    Session session =
        new Session(user, hash(rawToken), Instant.now().plus(refreshTokenDays, ChronoUnit.DAYS));
    sessionRepository.save(session);
    return rawToken;
  }

  public Optional<Session> findActive(String rawToken) {
    if (rawToken == null || rawToken.isBlank()) return Optional.empty();
    return sessionRepository.findByRefreshTokenHash(hash(rawToken)).filter(s -> s.isActive(Instant.now()));
  }

  public void revoke(String rawToken) {
    findActive(rawToken).ifPresent(session -> {
      session.revoke();
      sessionRepository.save(session);
    });
  }

  public String rotate(Session current) {
    current.revoke();
    sessionRepository.save(current);
    return create(current.getUser());
  }

  private String newToken() {
    byte[] bytes = new byte[48];
    secureRandom.nextBytes(bytes);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
  }

  private String hash(String value) {
    try {
      byte[] digest = MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(digest);
    } catch (NoSuchAlgorithmException exception) {
      throw new IllegalStateException("SHA-256 não está disponível", exception);
    }
  }
}
