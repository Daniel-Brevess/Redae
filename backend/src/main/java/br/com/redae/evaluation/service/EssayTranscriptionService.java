package br.com.redae.evaluation.service;

import br.com.redae.ai.client.AIClient;
import br.com.redae.shared.error.ApiException;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EssayTranscriptionService {
  private static final long MAX_IMAGE_SIZE = 8 * 1024 * 1024;
  private static final String TRANSCRIPTION_PROMPT =
      "Transcreva exatamente o texto desta redação em português do Brasil. "
          + "Preserve a ordem das palavras, a pontuação e as quebras de parágrafo. "
          + "Não corrija erros, não explique, não resuma e não invente conteúdo. "
          + "Retorne somente o texto transcrito. Se alguma parte estiver ilegível, "
          + "mantenha apenas o que puder identificar com segurança.";

  private final AIClient aiClient;

  public EssayTranscriptionService(AIClient aiClient) {
    this.aiClient = aiClient;
  }

  public String transcribe(MultipartFile file) {
    validate(file);
    try {
      String text =
          aiClient.transcribeImage(file.getBytes(), file.getContentType(), TRANSCRIPTION_PROMPT);
      if (text.isBlank()) {
        throw new ApiException(
            HttpStatus.BAD_GATEWAY,
            "TRANSCRIPTION_FAILED",
            "A imagem nÃ£o produziu uma transcriÃ§Ã£o vÃ¡lida.");
      }
      return text;
    } catch (IOException exception) {
      throw new ApiException(
          HttpStatus.BAD_REQUEST, "INVALID_IMAGE", "NÃ£o foi possÃ­vel ler a imagem enviada.");
    }
  }

  private void validate(MultipartFile file) {
    if (file == null || file.isEmpty()) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "INVALID_IMAGE", "Envie uma imagem.");
    }
    if (file.getSize() > MAX_IMAGE_SIZE) {
      throw new ApiException(
          HttpStatus.BAD_REQUEST, "IMAGE_TOO_LARGE", "A imagem deve ter no mÃ¡ximo 8 MB.");
    }
    if (!"image/jpeg".equals(file.getContentType()) && !"image/png".equals(file.getContentType())) {
      throw new ApiException(
          HttpStatus.BAD_REQUEST, "UNSUPPORTED_IMAGE", "Envie uma imagem JPG ou PNG.");
    }
  }
}
