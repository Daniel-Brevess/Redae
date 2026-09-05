package br.com.redae.gateway.config;

import br.com.redae.gateway.client.FakePaymentGatewayClient;
import br.com.redae.gateway.client.MercadoPagoClient;
import br.com.redae.gateway.client.PaymentGatewayProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentGatewayConfiguration {
  @Bean
  PaymentGatewayProvider paymentGatewayProvider(
      @Value("${payment.provider:fake}") String provider) {
    if ("mercadopago".equalsIgnoreCase(provider)) {
      return new MercadoPagoClient();
    }
    if ("fake".equalsIgnoreCase(provider)) {
      return new FakePaymentGatewayClient();
    }
    throw new IllegalStateException("Provedor de pagamento não suportado: " + provider);
  }
}
