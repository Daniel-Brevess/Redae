package br.com.redae.auth.service;

import br.com.redae.auth.entity.EmailVerificationToken;
import br.com.redae.auth.repository.EmailVerificationTokenRepository;
import br.com.redae.shared.error.ApiException;
import br.com.redae.user.entity.User;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmailVerificationService {
  private static final Duration CODE_VALIDITY = Duration.ofMinutes(15);
  private static final Duration RESEND_COOLDOWN = Duration.ofMinutes(1);
  private static final SecureRandom RANDOM = new SecureRandom();

  private final EmailVerificationTokenRepository tokenRepository;
  private final ResendEmailService emailService;

  public EmailVerificationService(
      EmailVerificationTokenRepository tokenRepository, ResendEmailService emailService) {
    this.tokenRepository = tokenRepository;
    this.emailService = emailService;
  }

  @Transactional
  public void sendCode(User user) {
    if (user.isEmailVerified()) return;
    var previous = tokenRepository.findTopByUserIdAndUsedAtIsNullOrderByCreatedAtDesc(user.getId());
    if (previous.isPresent()
        && previous.get().getCreatedAt().plus(RESEND_COOLDOWN).isAfter(Instant.now())) {
      throw new ApiException(
          HttpStatus.TOO_MANY_REQUESTS,
          "EMAIL_VERIFICATION_RATE_LIMITED",
          "Aguarde um minuto antes de solicitar outro código.");
    }
    String code = String.format("%06d", RANDOM.nextInt(1_000_000));
    tokenRepository.save(
        new EmailVerificationToken(user, hash(code), Instant.now().plus(CODE_VALIDITY)));
    emailService.sendVerificationCode(user.getEmail(), code);
  }

  @Transactional
  public void confirm(User user, String code) {
    if (!emailService.isEnabled()) {
      throw new ApiException(
          HttpStatus.NOT_FOUND,
          "EMAIL_VERIFICATION_DISABLED",
          "A confirmação de e-mail ainda não está disponível.");
    }
    if (user.isEmailVerified()) return;
    var token =
        tokenRepository
            .findTopByUserIdAndUsedAtIsNullOrderByCreatedAtDesc(user.getId())
            .filter(item -> item.isUsable(Instant.now()))
            .orElseThrow(
                () ->
                    new ApiException(
                        HttpStatus.UNAUTHORIZED,
                        "INVALID_EMAIL_VERIFICATION_CODE",
                        "O código é inválido ou expirou."));
    token.registerAttempt();
    if (!token.matches(hash(code))) {
      throw new ApiException(
          HttpStatus.UNAUTHORIZED,
          "INVALID_EMAIL_VERIFICATION_CODE",
          "O código é inválido ou expirou.");
    }
    token.markUsed();
    user.verifyEmail();
  }

  private String hash(String value) {
    try {
      byte[] digest =
          MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8));
      StringBuilder result = new StringBuilder();
      for (byte item : digest) result.append(String.format("%02x", item));
      return result.toString();
    } catch (NoSuchAlgorithmException exception) {
      throw new IllegalStateException("SHA-256 não está disponível", exception);
    }
  }
}
