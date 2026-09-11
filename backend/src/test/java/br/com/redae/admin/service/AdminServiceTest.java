package br.com.redae.admin.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.redae.gateway.service.CreditService;
import br.com.redae.user.entity.User;
import br.com.redae.user.repository.UserRepository;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {
  @Mock UserRepository userRepository;
  @Mock CreditService creditService;

  @Test
  void countsUsersThroughTheUserRepository() {
    when(userRepository.count()).thenReturn(3L);

    long result = new AdminService(userRepository, creditService).countUsers();

    assertThat(result).isEqualTo(3L);
    verify(userRepository).count();
  }

  @Test
  void includesLedgerBalanceForEachListedUser() {
    User user = new User("Student", "student@example.com", "hash");
    var pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "createdAt"));
    when(userRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(user), pageable, 1));
    when(creditService.getBalances(List.of(user.getId()))).thenReturn(Map.of(user.getId(), 5L));

    var result = new AdminService(userRepository, creditService).listUsers(PageRequest.of(0, 20));

    assertThat(result.getContent())
        .singleElement()
        .satisfies(response -> assertThat(response.credits()).isEqualTo(5L));
  }
}
