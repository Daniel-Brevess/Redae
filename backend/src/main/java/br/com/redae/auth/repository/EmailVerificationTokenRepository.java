package br.com.redae.auth.repository;

import br.com.redae.auth.entity.EmailVerificationToken;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailVerificationTokenRepository
    extends JpaRepository<EmailVerificationToken, UUID> {
  Optional<EmailVerificationToken> findTopByUserIdAndUsedAtIsNullOrderByCreatedAtDesc(UUID userId);
}
