package br.com.redae.gateway.dto;

public record PaymentCreationResult(
    String externalReference, String checkoutUrl, boolean approved) {}
