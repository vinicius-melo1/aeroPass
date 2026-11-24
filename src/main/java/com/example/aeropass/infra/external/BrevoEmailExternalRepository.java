package com.example.aeropass.infra.external;

import com.example.aeropass.domain.interfaces.IBrevoEmailExternalRepository;
import com.example.aeropass.infra.dto.BrevoEmailRequest;
import com.example.aeropass.infra.dto.BrevoEmailResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

@Component
public class BrevoEmailExternalRepository implements IBrevoEmailExternalRepository {
    private static final String URL = "https://api.brevo.com/v3/smtp/email";

    private final HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${brevo.api-key}")
    private String apiKey;

    @Value("${brevo.sender-email}")
    private String remetenteEmail;

    @Value("${brevo.sender-name}")
    private String remetenteNome;

    @Override
    public BrevoEmailResponse enviarEmail(String destinatario, String assunto, String html) {
        try {
            var corpo = objectMapper.writeValueAsString(new BrevoEmailRequest(
                    new BrevoEmailRequest.Contato(remetenteNome, remetenteEmail),
                    List.of(new BrevoEmailRequest.Contato(null, destinatario)),
                    assunto,
                    html));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .header("api-key", apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(corpo))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) {
                return objectMapper.readValue(response.body(), BrevoEmailResponse.class);
            }
            throw new RuntimeException("Erro ao enviar email: " + response.body());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
