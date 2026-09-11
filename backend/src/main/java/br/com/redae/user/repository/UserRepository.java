package br.com.redae.user.repository;

import br.com.redae.user.entity.User;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);

  Page<User> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
      String name, String email, Pageable pageable);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select user from User user where user.id = :id")
  Optional<User> findByIdForUpdate(@Param("id") UUID id);
}
