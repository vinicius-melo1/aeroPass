package com.example.aeropass.domain.repository;

import com.example.aeropass.application.DTOs.EnumStatusUsuario;
import com.example.aeropass.domain.entities.Usuario;
import com.example.aeropass.domain.interfaces.IUsuarioUnicidadeRepository;
import com.example.aeropass.domain.valueObject.CPF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario,Long>, IUsuarioUnicidadeRepository {
    boolean existsUsuarioByEmailAndSenha(String email, String senha);
    boolean existsByEmail(String email);
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);


    boolean existsByCpf(CPF cpf);
    boolean existsByCpfAndIdNot(CPF cpf, Long id);

    Optional<List<Usuario>> findByStatusNot(EnumStatusUsuario status);

    List<Usuario> getUsuariosByEmpresa_Id(Long empresa);
}
