package com.example.aeropass.application.DTOs;

import java.util.Set;

public record ReservarPassagemRequest(Long passageiroId, Set<Long> formasPagamentoIds) {
}
