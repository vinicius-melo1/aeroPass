package com.example.aeropass.domain.entities;

import com.example.aeropass.application.DTOs.EmpresaRequest;
import com.example.aeropass.application.DTOs.EnumStatusEmpresa;
import com.example.aeropass.domain.valueObject.CNPJ;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String razaoSocial;

    @Embedded
    private CNPJ cnpj;

    private String nomeFantasia;

    @OneToMany(mappedBy =  "empresa")
    private List<Usuario> usuarios;

    @Enumerated(EnumType.STRING)
    private EnumStatusEmpresa status = EnumStatusEmpresa.ATIVO;

    public Empresa(EmpresaRequest empresa) {
        this.setCnpj(new CNPJ(empresa.cnpj()));
        this.setRazaoSocial(empresa.razaoSocial());
        this.setNomeFantasia(empresa.nomeFantasia());
    }

    public void validarCnpjDisponivel(boolean cnpjJaExiste) {
        if (cnpjJaExiste) {
            throw new IllegalArgumentException("Já existe uma empresa cadastrada com este CNPJ!");
        }
    }
}
