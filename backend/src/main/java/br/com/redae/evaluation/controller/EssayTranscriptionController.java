package br.com.redae.evaluation.controller;

import br.com.redae.evaluation.dto.EssayTranscriptionResponse;
import br.com.redae.evaluation.service.EssayTranscriptionService;
import br.com.redae.shared.error.TraceIdFilter;
import br.com.redae.shared.http.ApiResponse;
import br.com.redae.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/essay-transcriptions")
public class EssayTranscriptionController {
  private final EssayTranscriptionService transcriptionService;

  public EssayTranscriptionController(EssayTranscriptionService transcriptionService) {
    this.transcriptionService = transcriptionService;
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ApiResponse<EssayTranscriptionResponse>> transcribe(
      @AuthenticationPrincipal User user,
      @RequestPart("image") MultipartFile image,
      HttpServletRequest request) {
    String text = transcriptionService.transcribe(image);
    String traceId = (String) request.getAttribute(TraceIdFilter.TRACE_ID_ATTRIBUTE);
    return ResponseEntity.ok(ApiResponse.of(new EssayTranscriptionResponse(text), traceId));
  }
}
