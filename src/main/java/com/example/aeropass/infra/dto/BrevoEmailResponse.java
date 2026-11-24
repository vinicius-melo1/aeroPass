package com.example.aeropass.infra.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BrevoEmailResponse(String messageId) {}