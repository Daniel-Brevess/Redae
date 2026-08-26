package br.com.redae.auth.service;

import br.com.redae.shared.error.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Service
public class ResendEmailService {
  private final String apiKey;
  private final String from;
  private final boolean enabled;
  private final RestClient client;

  public ResendEmailService(
      @Value("${resend.api-key:}") String apiKey,
      @Value("${resend.from-email:}") String from,
      @Value("${resend.enabled:false}") boolean enabled,
      RestClient.Builder restClientBuilder) {
    this.apiKey = apiKey;
    this.from = from;
    this.enabled = enabled;
    this.client = restClientBuilder.baseUrl("https://api.resend.com").build();
  }

  public void sendVerificationCode(String recipient, String code) {
    if (!enabled) {
      throw new ApiException(
          HttpStatus.NOT_FOUND,
          "EMAIL_VERIFICATION_DISABLED",
          "A confirmação de e-mail ainda não está disponível.");
    }
    if (apiKey.isBlank() || from.isBlank()) {
      throw new ApiException(
          HttpStatus.SERVICE_UNAVAILABLE,
          "EMAIL_PROVIDER_NOT_CONFIGURED",
          "O serviço de e-mail ainda não está configurado.");
    }
    try {
      client
          .post()
          .uri("/emails")
          .header("Authorization", "Bearer " + apiKey)
          .contentType(MediaType.APPLICATION_JSON)
          .body(
              new EmailPayload(
                  from,
                  recipient,
                  "Confirme seu e-mail no Redaê",
                  "<p>Seu código de confirmação é:</p><h1>"
                      + code
                      + "</h1><p>Ele expira em 15 minutos.</p>"))
          .retrieve()
          .toBodilessEntity();
    } catch (RestClientException exception) {
      throw new ApiException(
          HttpStatus.BAD_GATEWAY,
          "EMAIL_PROVIDER_ERROR",
          "Não foi possível enviar o e-mail de confirmação.");
    }
  }

  public boolean isEnabled() {
    return enabled;
  }

  private record EmailPayload(String from, String to, String subject, String html) {}
}
