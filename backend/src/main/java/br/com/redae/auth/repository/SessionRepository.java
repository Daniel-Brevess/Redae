package br.com.redae.auth.repository;

import br.com.redae.auth.entity.Session;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, UUID> {
  Optional<Session> findByRefreshTokenHash(String refreshTokenHash);
}
