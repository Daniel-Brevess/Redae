package br.com.redae.gateway.client;

import br.com.redae.gateway.dto.PaymentCreationResult;
import br.com.redae.gateway.entity.PaymentTransaction;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import java.math.RoundingMode;

public class StripeClient implements PaymentGatewayProvider {
  private final com.stripe.StripeClient stripeClient;
  private final String successUrl;
  private final String cancelUrl;

  public StripeClient(String secretKey, String successUrl, String cancelUrl) {
    if (secretKey == null || secretKey.isBlank()) {
      throw new IllegalStateException("STRIPE_SECRET_KEY não configurada.");
    }
    if (successUrl == null || successUrl.isBlank()) {
      throw new IllegalStateException("STRIPE_SUCCESS_URL não configurada.");
    }
    if (cancelUrl == null || cancelUrl.isBlank()) {
      throw new IllegalStateException("STRIPE_CANCEL_URL não configurada.");
    }
    this.stripeClient = new com.stripe.StripeClient(secretKey);
    this.successUrl = successUrl;
    this.cancelUrl = cancelUrl;
  }

  @Override
  public PaymentCreationResult createCheckoutSession(PaymentTransaction transaction) {
    long amountInCents =
        transaction
            .getAmount()
            .movePointRight(2)
            .setScale(0, RoundingMode.UNNECESSARY)
            .longValueExact();
    SessionCreateParams params =
        SessionCreateParams.builder()
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl(successUrl)
            .setCancelUrl(cancelUrl)
            .addLineItem(
                SessionCreateParams.LineItem.builder()
                    .setQuantity(1L)
                    .setPriceData(
                        SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency("brl")
                            .setUnitAmount(amountInCents)
                            .setProductData(
                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                    .setName("Créditos Redaê")
                                    .build())
                            .build())
                    .build())
            .putMetadata("payment_transaction_id", transaction.getId().toString())
            .build();
    try {
      Session session = stripeClient.v1().checkout().sessions().create(params);
      return new PaymentCreationResult(
          session.getId(), session.getUrl(), "paid".equals(session.getPaymentStatus()));
    } catch (StripeException exception) {
      throw new IllegalStateException("Não foi possível criar o Checkout da Stripe.", exception);
    }
  }

  @Override
  public String providerName() {
    return "STRIPE";
  }
}
