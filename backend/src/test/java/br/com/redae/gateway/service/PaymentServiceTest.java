package br.com.redae.gateway.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.redae.gateway.client.PaymentGatewayProvider;
import br.com.redae.gateway.dto.CreatePaymentRequest;
import br.com.redae.gateway.dto.PaymentCreationResult;
import br.com.redae.gateway.entity.PaymentTransaction;
import br.com.redae.gateway.entity.PaymentTransactionStatus;
import br.com.redae.gateway.repository.PaymentTransactionRepository;
import br.com.redae.user.entity.User;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
  @Mock private PaymentTransactionRepository paymentTransactionRepository;
  @Mock private PaymentGatewayProvider paymentGatewayProvider;

  @InjectMocks private PaymentService paymentService;

  @Test
  void fakeApprovalPersistsPaidTransaction() {
    User user = new User("Student", "student@example.com", "hash");
    var request = new CreatePaymentRequest(2, new BigDecimal("4.00"));
    when(paymentTransactionRepository.save(any(PaymentTransaction.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));
    when(paymentGatewayProvider.createPixPayment(any(PaymentTransaction.class)))
        .thenReturn(new PaymentCreationResult("fake-reference", null, null, true));

    var response = paymentService.create(user, request);

    assertEquals(PaymentTransactionStatus.PAGA, response.status());
    assertEquals(2, response.credits());
    assertEquals("fake-reference", response.externalReference());
    verify(paymentTransactionRepository, org.mockito.Mockito.times(2))
        .save(any(PaymentTransaction.class));
  }
}
