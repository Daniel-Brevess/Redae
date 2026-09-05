package br.com.redae.gateway.dto;

public record PaymentCreationResult(
    String externalReference, String qrCode, String qrCodeBase64, boolean approved) {}
