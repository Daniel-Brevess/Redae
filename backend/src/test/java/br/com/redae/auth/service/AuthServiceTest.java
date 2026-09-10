package br.com.redae.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.redae.auth.config.JwtService;
import br.com.redae.auth.dto.RegisterRequest;
import br.com.redae.user.entity.User;
import br.com.redae.user.entity.UserRole;
import br.com.redae.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
  @Mock UserRepository userRepository;
  @Mock JwtService jwtService;
  @Mock SessionService sessionService;

  private AuthService authService;

  @BeforeEach
  void setUp() {
    authService =
        new AuthService(
            userRepository,
            new BCryptPasswordEncoder(),
            jwtService,
            sessionService,
            "danielbreves1.20@gmail.com");
    when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
  }

  @Test
  void assignsAdminRoleOnlyToConfiguredEmail() {
    when(userRepository.existsByEmail(any())).thenReturn(false);

    User admin =
        authService.register(
            new RegisterRequest("Daniel", " DANIELBREVES1.20@GMAIL.COM ", "password", "password"));
    User student =
        authService.register(
            new RegisterRequest("Student", "student@example.com", "password", "password"));

    assertThat(admin.getRole()).isEqualTo(UserRole.ADMIN);
    assertThat(student.getRole()).isEqualTo(UserRole.STUDENT);
  }

  @Test
  void promotesExistingConfiguredEmailOnLogin() {
    User existingUser =
        new User(
            "Daniel", "danielbreves1.20@gmail.com", new BCryptPasswordEncoder().encode("password"));
    when(userRepository.findByEmail("danielbreves1.20@gmail.com"))
        .thenReturn(java.util.Optional.of(existingUser));

    authService.login(
        new br.com.redae.auth.dto.LoginRequest("danielbreves1.20@gmail.com", "password"));

    assertThat(existingUser.getRole()).isEqualTo(UserRole.ADMIN);
  }
}
