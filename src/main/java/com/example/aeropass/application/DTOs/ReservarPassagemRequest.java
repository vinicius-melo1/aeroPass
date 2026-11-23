package com.example.aeropass.application.DTOs;

import java.util.Set;

public record ReservarPassagemRequest(Set<Long> formasPagamentoIds) {
}