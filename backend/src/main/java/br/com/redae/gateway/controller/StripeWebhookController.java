package br.com.redae.gateway.controller;

import br.com.redae.gateway.service.PaymentService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/webhooks/stripe")
public class StripeWebhookController {
  private final PaymentService paymentService;
  private final String webhookSecret;

  public StripeWebhookController(
      PaymentService paymentService,
      @Value("${payment.stripe.webhook-secret:}") String webhookSecret) {
    this.paymentService = paymentService;
    this.webhookSecret = webhookSecret;
  }

  @PostMapping
  public ResponseEntity<Void> receive(
      @RequestBody String payload, @RequestHeader("Stripe-Signature") String signature) {
    if (webhookSecret.isBlank()) {
      throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
    }
    Event event;
    try {
      event = Webhook.constructEvent(payload, signature, webhookSecret);
    } catch (SignatureVerificationException exception) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Webhook inválido.");
    }
    if ("payment_intent.succeeded".equals(event.getType())) {
      paymentIntent(event).ifPresent(intent -> paymentService.confirmPayment(intent.getId()));
    } else if ("payment_intent.payment_failed".equals(event.getType())) {
      paymentIntent(event).ifPresent(intent -> paymentService.failPayment(intent.getId()));
    }
    return ResponseEntity.ok().build();
  }

  private java.util.Optional<PaymentIntent> paymentIntent(Event event) {
    return event.getDataObjectDeserializer().getObject().flatMap(this::asPaymentIntent);
  }

  private java.util.Optional<PaymentIntent> asPaymentIntent(StripeObject object) {
    return object instanceof PaymentIntent intent
        ? java.util.Optional.of(intent)
        : java.util.Optional.empty();
  }
}
