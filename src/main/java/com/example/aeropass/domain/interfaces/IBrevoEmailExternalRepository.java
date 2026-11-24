package com.example.aeropass.domain.interfaces;

import com.example.aeropass.infra.dto.BrevoEmailResponse;

public interface IBrevoEmailExternalRepository {
    BrevoEmailResponse enviarEmail(String destinatario, String assunto, String html);
}