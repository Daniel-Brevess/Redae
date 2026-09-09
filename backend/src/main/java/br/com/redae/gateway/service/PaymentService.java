package br.com.redae.gateway.service;

import br.com.redae.gateway.client.PaymentGatewayProvider;
import br.com.redae.gateway.dto.CreatePaymentRequest;
import br.com.redae.gateway.dto.CreditBalanceResponse;
import br.com.redae.gateway.dto.PaymentResponse;
import br.com.redae.gateway.dto.PaymentTransactionResponse;
import br.com.redae.gateway.entity.CreditTransaction;
import br.com.redae.gateway.entity.CreditTransactionType;
import br.com.redae.gateway.entity.PaymentTransaction;
import br.com.redae.gateway.entity.PaymentTransactionStatus;
import br.com.redae.gateway.repository.CreditTransactionRepository;
import br.com.redae.gateway.repository.PaymentTransactionRepository;
import br.com.redae.shared.error.ResourceNotFoundException;
import br.com.redae.user.entity.User;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {
  private static final BigDecimal CREDIT_PRICE = new BigDecimal("3.00");

  private final PaymentTransactionRepository paymentTransactionRepository;
  private final PaymentGatewayProvider paymentGatewayProvider;
  private final CreditTransactionRepository creditTransactionRepository;

  public PaymentService(
      PaymentTransactionRepository paymentTransactionRepository,
      PaymentGatewayProvider paymentGatewayProvider,
      CreditTransactionRepository creditTransactionRepository) {
    this.paymentTransactionRepository = paymentTransactionRepository;
    this.paymentGatewayProvider = paymentGatewayProvider;
    this.creditTransactionRepository = creditTransactionRepository;
  }

  @Transactional
  public PaymentResponse create(User user, CreatePaymentRequest request) {
    BigDecimal amount = CREDIT_PRICE.multiply(BigDecimal.valueOf(request.creditAmount()));
    PaymentTransaction transaction =
        paymentTransactionRepository.save(
            new PaymentTransaction(user, request.creditAmount(), amount));

    var payment = paymentGatewayProvider.createCheckoutSession(transaction);
    transaction.markPending(payment.externalReference());
    if (payment.approved()) {
      transaction.markPaid();
      creditTransactionRepository.save(
          new CreditTransaction(user, transaction, transaction.getTotalCredits()));
    }

    return PaymentResponse.from(paymentTransactionRepository.save(transaction), payment);
  }

  @Transactional(readOnly = true)
  public List<PaymentTransactionResponse> listTransactions(User user) {
    return paymentTransactionRepository.findAllByUserIdOrderByCreatedAtDesc(user.getId()).stream()
        .map(PaymentTransactionResponse::from)
        .toList();
  }

  @Transactional(readOnly = true)
  public CreditBalanceResponse getCreditBalance(User user) {
    long credits =
        creditTransactionRepository.sumQuantityByUserIdAndType(
            user.getId(), CreditTransactionType.COMPRA);
    return new CreditBalanceResponse(credits);
  }

  @Transactional
  public void confirmPayment(String externalReference) {
    PaymentTransaction transaction = findByExternalReference(externalReference);
    if (transaction.getStatus() == PaymentTransactionStatus.PAGA) {
      return;
    }
    transaction.markPaid();
    if (!creditTransactionRepository.existsByPaymentTransactionId(transaction.getId())) {
      creditTransactionRepository.save(
          new CreditTransaction(transaction.getUser(), transaction, transaction.getTotalCredits()));
    }
    paymentTransactionRepository.save(transaction);
  }

  @Transactional
  public void failPayment(String externalReference) {
    PaymentTransaction transaction = findByExternalReference(externalReference);
    if (transaction.getStatus() != PaymentTransactionStatus.PAGA) {
      transaction.markFailed();
      paymentTransactionRepository.save(transaction);
    }
  }

  private PaymentTransaction findByExternalReference(String externalReference) {
    return paymentTransactionRepository
        .findByExternalReference(externalReference)
        .orElseThrow(
            () -> new ResourceNotFoundException("A transação de pagamento não foi encontrada."));
  }
}
