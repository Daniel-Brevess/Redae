package br.com.redae.evaluation.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TesseractOcrService {
  private static final Duration TIMEOUT = Duration.ofSeconds(30);

  private final String command;

  public TesseractOcrService(@Value("${ocr.tesseract.command:tesseract}") String command) {
    this.command = command;
  }

  public String transcribe(byte[] image, String contentType) throws IOException {
    Path directory = Files.createTempDirectory("redae-ocr-");
    Path input = directory.resolve("input" + extension(contentType));
    Path outputBase = directory.resolve("output");
    try {
      Files.write(input, cropEssayArea(image));
      Process process =
          new ProcessBuilder(
                  command, input.toString(), outputBase.toString(), "-l", "por", "--psm", "6")
              .redirectError(ProcessBuilder.Redirect.DISCARD)
              .redirectOutput(ProcessBuilder.Redirect.DISCARD)
              .start();
      try {
        if (!process.waitFor(TIMEOUT.toSeconds(), TimeUnit.SECONDS)) {
          process.destroyForcibly();
          throw new IOException("OCR excedeu o tempo limite.");
        }
      } catch (InterruptedException exception) {
        process.destroyForcibly();
        Thread.currentThread().interrupt();
        throw new IOException("OCR interrompido.", exception);
      }
      if (process.exitValue() != 0) {
        throw new IOException("OCR não conseguiu processar a imagem.");
      }
      return Files.readString(outputBase.resolveSibling("output.txt"), StandardCharsets.UTF_8)
          .trim();
    } finally {
      Files.deleteIfExists(outputBase.resolveSibling("output.txt"));
      Files.deleteIfExists(input);
      Files.deleteIfExists(directory);
    }
  }

  private byte[] cropEssayArea(byte[] image) throws IOException {
    BufferedImage source = ImageIO.read(new ByteArrayInputStream(image));
    if (source == null) {
      throw new IOException("Imagem inválida.");
    }
    int left = Math.round(source.getWidth() * 0.045f);
    int top = Math.round(source.getHeight() * 0.24f);
    int right = Math.round(source.getWidth() * 0.98f);
    int bottom = Math.round(source.getHeight() * 0.88f);
    BufferedImage crop = source.getSubimage(left, top, right - left, bottom - top);
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    ImageIO.write(crop, "png", output);
    return output.toByteArray();
  }

  private String extension(String contentType) {
    return "image/png".equals(contentType) ? ".png" : ".jpg";
  }
}
