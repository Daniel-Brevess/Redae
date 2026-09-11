package br.com.redae.auth.config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.redae.ai.client.AIClient;
import br.com.redae.auth.repository.EmailVerificationTokenRepository;
import br.com.redae.auth.repository.SessionRepository;
import br.com.redae.evaluation.repository.EvaluationRepository;
import br.com.redae.gateway.repository.CreditPriceRepository;
import br.com.redae.gateway.repository.CreditTransactionRepository;
import br.com.redae.gateway.repository.PaymentTransactionRepository;
import br.com.redae.user.entity.User;
import br.com.redae.user.entity.UserRole;
import br.com.redae.user.repository.UserRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(
    properties = {
      "JWT_SECRET=local-test-secret-with-at-least-32-characters",
      "spring.autoconfigure.exclude="
          + "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,"
          + "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration",
      "spring.flyway.enabled=false"
    })
@AutoConfigureMockMvc
class AdminAuthorizationTest {
  @Autowired MockMvc mockMvc;

  @MockBean UserRepository userRepository;
  @MockBean SessionRepository sessionRepository;
  @MockBean EmailVerificationTokenRepository emailVerificationTokenRepository;
  @MockBean EvaluationRepository evaluationRepository;
  @MockBean PaymentTransactionRepository paymentTransactionRepository;
  @MockBean CreditPriceRepository creditPriceRepository;
  @MockBean CreditTransactionRepository creditTransactionRepository;
  @MockBean AIClient aiClient;

  @Test
  void studentReceivesForbiddenForAdministrativeRoutes() throws Exception {
    mockMvc
        .perform(get("/api/v1/admin/test").with(user("student").roles("STUDENT")))
        .andExpect(status().isForbidden());
  }

  @Test
  void adminIsNotBlockedByAdministrativeRule() throws Exception {
    mockMvc
        .perform(get("/api/v1/admin/test").with(user("admin").roles("ADMIN")))
        .andExpect(status().isNotFound());
  }

  @Test
  void studentReceivesForbiddenForUserCount() throws Exception {
    mockMvc
        .perform(get("/api/v1/admin/users/count").with(user("student").roles("STUDENT")))
        .andExpect(status().isForbidden());
  }

  @Test
  void adminCanReadUserCount() throws Exception {
    when(userRepository.count()).thenReturn(4L);

    mockMvc
        .perform(get("/api/v1/admin/users/count").with(user("admin").roles("ADMIN")))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.totalUsers").value(4));
  }

  @Test
  void adminCanReadPaginatedUsersWithoutSensitiveData() throws Exception {
    User listedUser = new User("Student", "student@example.com", "password-hash", UserRole.STUDENT);
    when(userRepository.findAll(any(Pageable.class)))
        .thenReturn(new PageImpl<>(List.of(listedUser)));

    mockMvc
        .perform(get("/api/v1/admin/users").with(user("admin").roles("ADMIN")))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data[0].name").value("Student"))
        .andExpect(jsonPath("$.data[0].email").value("student@example.com"))
        .andExpect(jsonPath("$.data[0].role").value("STUDENT"))
        .andExpect(jsonPath("$.data[0].passwordHash").doesNotExist())
        .andExpect(jsonPath("$.meta.totalElements").value(1));
  }
}
