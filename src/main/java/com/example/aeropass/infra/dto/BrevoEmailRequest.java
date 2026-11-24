package com.example.aeropass.infra.dto;

import java.util.List;

public record BrevoEmailRequest(Contato sender, List<Contato> to, String subject, String htmlContent) {
    public record Contato(String name, String email) {}
}