package br.com.redae.auth.config;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.redae.ai.client.AIClient;
import br.com.redae.auth.repository.EmailVerificationTokenRepository;
import br.com.redae.auth.repository.SessionRepository;
import br.com.redae.evaluation.repository.EvaluationRepository;
import br.com.redae.gateway.repository.CreditPriceRepository;
import br.com.redae.gateway.repository.CreditTransactionRepository;
import br.com.redae.gateway.repository.PaymentTransactionRepository;
import br.com.redae.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
}
