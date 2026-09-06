package br.com.redae.gateway.client;

import br.com.redae.gateway.dto.PaymentCreationResult;
import br.com.redae.gateway.entity.PaymentTransaction;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import java.math.RoundingMode;

public class StripeClient implements PaymentGatewayProvider {
  private final com.stripe.StripeClient stripeClient;

  public StripeClient(String secretKey) {
    if (secretKey == null || secretKey.isBlank()) {
      throw new IllegalStateException("STRIPE_SECRET_KEY não configurada.");
    }
    this.stripeClient = new com.stripe.StripeClient(secretKey);
  }

  @Override
  public PaymentCreationResult createPixPayment(PaymentTransaction transaction) {
    long amountInCents =
        transaction
            .getAmount()
            .movePointRight(2)
            .setScale(0, RoundingMode.UNNECESSARY)
            .longValueExact();
    PaymentIntentCreateParams params =
        PaymentIntentCreateParams.builder()
            .setAmount(amountInCents)
            .setCurrency("brl")
            .addPaymentMethodType("pix")
            .putMetadata("payment_transaction_id", transaction.getId().toString())
            .build();
    try {
      PaymentIntent paymentIntent = stripeClient.v1().paymentIntents().create(params);
      return new PaymentCreationResult(
          paymentIntent.getId(),
          paymentIntent.getClientSecret(),
          null,
          null,
          "succeeded".equals(paymentIntent.getStatus()));
    } catch (StripeException exception) {
      throw new IllegalStateException("Não foi possível criar o pagamento na Stripe.", exception);
    }
  }

  @Override
  public String providerName() {
    return "STRIPE";
  }
}
