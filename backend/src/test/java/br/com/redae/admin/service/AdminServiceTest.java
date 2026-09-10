package br.com.redae.admin.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.redae.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {
  @Mock UserRepository userRepository;

  @Test
  void countsUsersThroughTheUserRepository() {
    when(userRepository.count()).thenReturn(3L);

    long result = new AdminService(userRepository).countUsers();

    assertThat(result).isEqualTo(3L);
    verify(userRepository).count();
  }
}
