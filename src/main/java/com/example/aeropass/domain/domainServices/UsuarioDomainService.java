package com.example.aeropass.domain.domainServices;

import com.example.aeropass.domain.entities.Usuario;
import com.example.aeropass.domain.interfaces.IUsuarioUnicidadeRepository;
import com.example.aeropass.domain.valueObject.CPF;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioDomainService {
    private final IUsuarioUnicidadeRepository repository;

    public void validarUnicidade(Usuario usuario) {
        validarUnicidade(usuario.getId(), usuario.getEmail(), usuario.getCpf());
    }

    public void validarUnicidade(Long id, String email, CPF cpf) {
        boolean emailExiste = id == null
                ? repository.existsByEmailIgnoreCase(email)
                : repository.existsByEmailIgnoreCaseAndIdNot(email, id);

        if (emailExiste) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com este email!");
        }

        boolean cpfExiste = id == null
                ? repository.existsByCpf(cpf)
                : repository.existsByCpfAndIdNot(cpf, id);

        if (cpfExiste) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com este CPF!");
        }
    }
    private void garantirEmailUnico(Usuario usuario) {
        boolean existe = usuario.getId() == null
                ? repository.existsByEmailIgnoreCase(usuario.getEmail())
                : repository.existsByEmailIgnoreCaseAndIdNot(usuario.getEmail(), usuario.getId());

        if (existe) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com este email!");
        }
    }

    private void garantirCpfUnico(Usuario usuario) {
        boolean existe = usuario.getId() == null
                ? repository.existsByCpf(usuario.getCpf())
                : repository.existsByCpfAndIdNot(usuario.getCpf(), usuario.getId());

        if (existe) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com este CPF!");
        }
    }
}
