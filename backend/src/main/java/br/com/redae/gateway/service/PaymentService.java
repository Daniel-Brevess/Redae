package br.com.redae.gateway.service;

import br.com.redae.gateway.client.PaymentGatewayProvider;
import br.com.redae.gateway.dto.CreatePaymentRequest;
import br.com.redae.gateway.dto.CreditBalanceResponse;
import br.com.redae.gateway.dto.PaymentResponse;
import br.com.redae.gateway.dto.PaymentTransactionResponse;
import br.com.redae.gateway.entity.CreditTransaction;
import br.com.redae.gateway.entity.CreditTransactionType;
import br.com.redae.gateway.entity.PaymentTransaction;
import br.com.redae.gateway.repository.CreditPriceRepository;
import br.com.redae.gateway.repository.CreditTransactionRepository;
import br.com.redae.gateway.repository.PaymentTransactionRepository;
import br.com.redae.shared.error.ResourceNotFoundException;
import br.com.redae.user.entity.User;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {
  private final PaymentTransactionRepository paymentTransactionRepository;
  private final PaymentGatewayProvider paymentGatewayProvider;
  private final CreditPriceRepository creditPriceRepository;
  private final CreditTransactionRepository creditTransactionRepository;

  public PaymentService(
      PaymentTransactionRepository paymentTransactionRepository,
      PaymentGatewayProvider paymentGatewayProvider,
      CreditPriceRepository creditPriceRepository,
      CreditTransactionRepository creditTransactionRepository) {
    this.paymentTransactionRepository = paymentTransactionRepository;
    this.paymentGatewayProvider = paymentGatewayProvider;
    this.creditPriceRepository = creditPriceRepository;
    this.creditTransactionRepository = creditTransactionRepository;
  }

  @Transactional
  public PaymentResponse create(User user, CreatePaymentRequest request) {
    var price =
        creditPriceRepository
            .findCurrent(Instant.now())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "O preço atual dos créditos não foi encontrado."));
    BigDecimal amount =
        price.getAmountPerCredit().multiply(BigDecimal.valueOf(request.creditAmount()));
    PaymentTransaction transaction =
        paymentTransactionRepository.save(
            new PaymentTransaction(user, request.creditAmount(), amount));

    var payment = paymentGatewayProvider.createPixPayment(transaction);
    transaction.markPending(payment.externalReference());
    if (payment.approved()) {
      transaction.markPaid();
      creditTransactionRepository.save(
          new CreditTransaction(user, transaction, transaction.getTotalCredits()));
    }

    return PaymentResponse.from(paymentTransactionRepository.save(transaction));
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
}
