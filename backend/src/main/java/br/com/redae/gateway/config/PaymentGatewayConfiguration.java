package br.com.redae.gateway.config;

import br.com.redae.gateway.client.FakePaymentGatewayClient;
import br.com.redae.gateway.client.PaymentGatewayProvider;
import br.com.redae.gateway.client.StripeClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentGatewayConfiguration {
  @Bean
  PaymentGatewayProvider paymentGatewayProvider(
      @Value("${payment.provider:fake}") String provider,
      @Value("${payment.stripe.secret-key:}") String stripeSecretKey,
      @Value("${payment.stripe.success-url:}") String stripeSuccessUrl,
      @Value("${payment.stripe.cancel-url:}") String stripeCancelUrl) {
    if ("stripe".equalsIgnoreCase(provider)) {
      return new StripeClient(stripeSecretKey, stripeSuccessUrl, stripeCancelUrl);
    }
    if ("fake".equalsIgnoreCase(provider)) {
      return new FakePaymentGatewayClient();
    }
    throw new IllegalStateException("Provedor de pagamento não suportado: " + provider);
  }
}
