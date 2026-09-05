package br.com.redae.gateway.client;

import br.com.redae.gateway.dto.PaymentCreationResult;
import br.com.redae.gateway.entity.PaymentTransaction;

public interface PaymentGatewayProvider {
  PaymentCreationResult createPixPayment(PaymentTransaction transaction);

  String providerName();
}
