package br.com.redae.gateway.client;

import br.com.redae.gateway.dto.PaymentCreationResult;
import br.com.redae.gateway.entity.PaymentTransaction;

public class MercadoPagoClient implements PaymentGatewayProvider {
  @Override
  public PaymentCreationResult createPixPayment(PaymentTransaction transaction) {
    throw new UnsupportedOperationException("Integração com Mercado Pago ainda não implementada.");
  }

  @Override
  public String providerName() {
    return "MERCADO_PAGO";
  }
}
