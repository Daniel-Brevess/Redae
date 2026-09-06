package br.com.redae.gateway.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.redae.gateway.client.PaymentGatewayProvider;
import br.com.redae.gateway.dto.CreatePaymentRequest;
import br.com.redae.gateway.dto.PaymentCreationResult;
import br.com.redae.gateway.entity.CreditPrice;
import br.com.redae.gateway.entity.CreditTransactionType;
import br.com.redae.gateway.entity.PaymentTransaction;
import br.com.redae.gateway.entity.PaymentTransactionStatus;
import br.com.redae.gateway.repository.CreditPriceRepository;
import br.com.redae.gateway.repository.CreditTransactionRepository;
import br.com.redae.gateway.repository.PaymentTransactionRepository;
import br.com.redae.user.entity.User;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
  @Mock private PaymentTransactionRepository paymentTransactionRepository;
  @Mock private PaymentGatewayProvider paymentGatewayProvider;
  @Mock private CreditPriceRepository creditPriceRepository;
  @Mock private CreditTransactionRepository creditTransactionRepository;

  @InjectMocks private PaymentService paymentService;

  @Test
  void fakeApprovalPersistsPaidTransaction() {
    User user = new User("Student", "student@example.com", "hash");
    var request = new CreatePaymentRequest(2);
    CreditPrice price = org.mockito.Mockito.mock(CreditPrice.class);
    when(creditPriceRepository.findCurrent(any())).thenReturn(java.util.Optional.of(price));
    when(price.getAmountPerCredit()).thenReturn(new BigDecimal("2.00"));
    when(paymentTransactionRepository.save(any(PaymentTransaction.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));
    when(paymentGatewayProvider.createPixPayment(any(PaymentTransaction.class)))
        .thenReturn(new PaymentCreationResult("fake-reference", null, null, true));

    var response = paymentService.create(user, request);

    assertEquals(PaymentTransactionStatus.PAGA, response.status());
    assertEquals(2, response.credits());
    assertEquals("fake-reference", response.externalReference());
    verify(creditTransactionRepository).save(any());
    verify(paymentTransactionRepository, org.mockito.Mockito.times(2))
        .save(any(PaymentTransaction.class));
  }

  @Test
  void listsTransactionsForAuthenticatedUser() {
    User user = new User("Student", "student@example.com", "hash");
    PaymentTransaction transaction = new PaymentTransaction(user, 3, new BigDecimal("6.00"));
    when(paymentTransactionRepository.findAllByUserIdOrderByCreatedAtDesc(user.getId()))
        .thenReturn(List.of(transaction));

    var response = paymentService.listTransactions(user);

    assertEquals(1, response.size());
    assertEquals(3, response.getFirst().credits());
    assertEquals(new BigDecimal("6.00"), response.getFirst().amount());
  }

  @Test
  void returnsCreditBalanceFromCreditLedger() {
    User user = new User("Student", "student@example.com", "hash");
    when(creditTransactionRepository.sumQuantityByUserIdAndType(
            user.getId(), CreditTransactionType.COMPRA))
        .thenReturn(7L);

    var response = paymentService.getCreditBalance(user);

    assertEquals(7L, response.credits());
    verify(creditTransactionRepository)
        .sumQuantityByUserIdAndType(user.getId(), CreditTransactionType.COMPRA);
  }
}
