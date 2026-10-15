package com.example.aeropass.domain.valueObject;

public class CPF {

    private String cpf;

    public CPF(){
        this.cpf = "";
    }

    public CPF(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString(){
        return cpf.replaceAll("\\D","");
    }

}
