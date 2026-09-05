package br.com.redae.gateway.client;

import br.com.redae.gateway.dto.PaymentCreationResult;
import br.com.redae.gateway.entity.PaymentTransaction;
import java.util.UUID;

public class FakePaymentGatewayClient implements PaymentGatewayProvider {
  @Override
  public PaymentCreationResult createPixPayment(PaymentTransaction transaction) {
    return new PaymentCreationResult("fake-" + UUID.randomUUID(), null, null, true);
  }

  @Override
  public String providerName() {
    return "FAKE";
  }
}
