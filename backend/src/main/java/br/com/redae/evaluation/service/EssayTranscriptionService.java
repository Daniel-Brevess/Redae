package br.com.redae.evaluation.service;

import br.com.redae.shared.error.ApiException;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EssayTranscriptionService {
  private static final long MAX_IMAGE_SIZE = 8 * 1024 * 1024;

  private final TesseractOcrService ocrService;

  public EssayTranscriptionService(TesseractOcrService ocrService) {
    this.ocrService = ocrService;
  }

  public String transcribe(MultipartFile file) {
    validate(file);
    try {
      String text = ocrService.transcribe(file.getBytes(), file.getContentType());
      if (text.isBlank()) {
        throw new ApiException(
            HttpStatus.BAD_GATEWAY,
            "TRANSCRIPTION_FAILED",
            "A imagem não produziu uma transcrição válida.");
      }
      return text;
    } catch (IOException exception) {
      throw new ApiException(
          HttpStatus.BAD_REQUEST, "INVALID_IMAGE", "Não foi possível ler a imagem enviada.");
    }
  }

  private void validate(MultipartFile file) {
    if (file == null || file.isEmpty()) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "INVALID_IMAGE", "Envie uma imagem.");
    }
    if (file.getSize() > MAX_IMAGE_SIZE) {
      throw new ApiException(
          HttpStatus.BAD_REQUEST, "IMAGE_TOO_LARGE", "A imagem deve ter no máximo 8 MB.");
    }
    if (!"image/jpeg".equals(file.getContentType()) && !"image/png".equals(file.getContentType())) {
      throw new ApiException(
          HttpStatus.BAD_REQUEST, "UNSUPPORTED_IMAGE", "Envie uma imagem JPG ou PNG.");
    }
  }
}
