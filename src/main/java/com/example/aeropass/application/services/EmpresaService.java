package com.example.aeropass.application.services;

import com.example.aeropass.application.DTOs.*;
import com.example.aeropass.domain.entities.Empresa;
import com.example.aeropass.domain.repository.EmpresaRepository;
import com.example.aeropass.domain.valueObject.CNPJ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {
    @Autowired
    private EmpresaRepository empresaRepository;

    public List<EmpresaResponse> listarTodasEmpresasTable(){

        return empresaRepository.findAll()
                .stream()
                .map(EmpresaResponse::new)
                .toList();
    }

    public EmpresaResponse salvarEmpresa(EmpresaRequest empresa, Long id) {

        if (id == 0L) {

            Empresa empresaBanco = new Empresa(empresa);
            empresaRepository.save(empresaBanco);
            return new EmpresaResponse(empresaBanco);
        } else {
            try{
                Empresa empresaBanco = empresaRepository.findById(id).orElse(null);
                if(empresaBanco != null) {
                    empresaBanco.setCnpj(new CNPJ(empresa.cnpj()));
                    empresaBanco.setRazaoSocial(empresa.razaoSocial());
                    empresaBanco.setNomeFantasia(empresa.nomeFantasia());
                    empresaRepository.save(empresaBanco);
                    return new EmpresaResponse(empresaBanco);
                }

            }catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }
        return null;

    }

    public EmpresaResponse buscarPorId(Long id) {
        var empresa = empresaRepository.findById(id).orElse(null);

        if(empresa == null) {
            return null;
        }
        return new EmpresaResponse(empresa);
    }

    public boolean atualizarStatus(EnumStatusEmpresa status, Long id) {

        Empresa empresaBanco = empresaRepository.findById(id).orElse(null);
        if(empresaBanco != null) {
            empresaBanco.setStatus(status);
            empresaRepository.save(empresaBanco);
            return true;
        }
        return false;

    }
}
