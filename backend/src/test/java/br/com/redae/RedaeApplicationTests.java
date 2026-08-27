package br.com.redae;

import br.com.redae.auth.repository.EmailVerificationTokenRepository;
import br.com.redae.auth.repository.SessionRepository;
import br.com.redae.evaluation.repository.EvaluationRepository;
import br.com.redae.identity.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(
    properties = {
      "JWT_SECRET=local-test-secret-with-at-least-32-characters",
      "spring.autoconfigure.exclude="
          + "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,"
          + "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration",
      "spring.flyway.enabled=false"
    })
class RedaeApplicationTests {
  @MockBean UserRepository userRepository;
  @MockBean SessionRepository sessionRepository;
  @MockBean EmailVerificationTokenRepository emailVerificationTokenRepository;
  @MockBean EvaluationRepository evaluationRepository;

  @Test
  void contextLoads() {}
}
