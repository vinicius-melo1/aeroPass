package com.example.aeropass.domain.valueObject;

import jakarta.persistence.Embeddable;

@Embeddable
public class Passaporte {
    private String passaporte;

    public Passaporte() {
        this.passaporte = "";
    }

    public Passaporte(String passaporte) {
        if (!isValidPassaporte(passaporte)) {
            throw new IllegalArgumentException("Passaporte inválido!");
        }
        this.passaporte = normalizar(passaporte);
    }

    public static boolean isValidPassaporte(String passaporte) {
        if (passaporte == null) {
            return false;
        }

        String valor = normalizar(passaporte);


        // Valida somente passaportes brasileiros
        if (!valor.matches("[A-Z]{2}\\\\d{6}")) {
            return false;
        }

        // Precisa ter pelo menos um dígito (descarta palavras como "PASSPORT")
        if (!valor.matches(".*\\d.*")) {
            return false;
        }

        // Descarta sequências com todos os caracteres iguais
        return !valor.matches("(.)\\1+");
    }

    // Remove espaços e hífens e padroniza em maiúsculas
    private static String normalizar(String valor) {
        return valor.replaceAll("[\\s-]", "").toUpperCase();
    }

    public String getPassaporte() {
        return passaporte;
    }

    @Override
    public String toString() {
        return passaporte;
    }
}
