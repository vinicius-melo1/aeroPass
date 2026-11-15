package com.example.aeropass.domain.valueObject;

import jakarta.persistence.Embeddable;

@Embeddable
public class Telefone {

    private String telefone;

    public Telefone() {
        this.telefone = "";
    }

    public Telefone(String telefone) {
        if (!isValidTelefone(telefone)) {
            throw new IllegalArgumentException("Telefone inválido!");
        }
        this.telefone = telefone.replaceAll("\\D", ""); // guarda só dígitos
    }

    public static boolean isValidTelefone(String telefone) {
        if (telefone == null) {
            return false;
        }

        // Remove tudo o que não for dígito (parênteses, espaços, hífen, +)
        String numero = telefone.replaceAll("\\D", "");

        // Remove o código do país (55) se vier informado
        if ((numero.length() == 12 || numero.length() == 13) && numero.startsWith("55")) {
            numero = numero.substring(2);
        }

        // Fixo: DDD + 8 dígitos (10) | Celular: DDD + 9 dígitos (11)
        if (numero.length() != 10 && numero.length() != 11) {
            return false;
        }

        // DDD válido: 11 a 99, sem zero à esquerda
        int ddd = Integer.parseInt(numero.substring(0, 2));
        if (ddd < 11) {
            return false;
        }

        String numeroSemDdd = numero.substring(2);

        // Celular (9 dígitos) precisa começar com 9
        if (numeroSemDdd.length() == 9 && numeroSemDdd.charAt(0) != '9') {
            return false;
        }

        // Fixo (8 dígitos) precisa começar com 2 a 5
        if (numeroSemDdd.length() == 8 && "2345".indexOf(numeroSemDdd.charAt(0)) < 0) {
            return false;
        }

        // Descarta números com todos os dígitos iguais
        return !numeroSemDdd.matches("(\\d)\\1+");
    }

    public String getTelefone() {
        return telefone;
    }

    @Override
    public String toString() {
        String n = telefone.replaceAll("\\D", "");
        if (n.length() == 11) {
            return n.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
        }
        if (n.length() == 10) {
            return n.replaceAll("(\\d{2})(\\d{4})(\\d{4})", "($1) $2-$3");
        }
        return n;
    }
}
