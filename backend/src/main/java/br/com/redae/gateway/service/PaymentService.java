package br.com.redae.gateway.service;

import br.com.redae.gateway.client.PaymentGatewayProvider;
import br.com.redae.gateway.dto.CreatePaymentRequest;
import br.com.redae.gateway.dto.PaymentResponse;
import br.com.redae.gateway.entity.PaymentTransaction;
import br.com.redae.gateway.repository.PaymentTransactionRepository;
import br.com.redae.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {
  private final PaymentTransactionRepository paymentTransactionRepository;
  private final PaymentGatewayProvider paymentGatewayProvider;

  public PaymentService(
      PaymentTransactionRepository paymentTransactionRepository,
      PaymentGatewayProvider paymentGatewayProvider) {
    this.paymentTransactionRepository = paymentTransactionRepository;
    this.paymentGatewayProvider = paymentGatewayProvider;
  }

  @Transactional
  public PaymentResponse create(User user, CreatePaymentRequest request) {
    PaymentTransaction transaction =
        paymentTransactionRepository.save(
            new PaymentTransaction(user, request.creditAmount(), request.amount()));

    var payment = paymentGatewayProvider.createPixPayment(transaction);
    transaction.markPending(payment.externalReference());
    if (payment.approved()) {
      transaction.markPaid();
    }

    return PaymentResponse.from(paymentTransactionRepository.save(transaction));
  }
}
