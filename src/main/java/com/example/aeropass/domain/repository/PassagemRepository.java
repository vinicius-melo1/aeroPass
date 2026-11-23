package com.example.aeropass.domain.repository;

import com.example.aeropass.domain.entities.Passagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassagemRepository extends JpaRepository<Passagem, Long> {
    List<Passagem> findByVooId(Long vooId);
    List<Passagem> findByVoo_Empresa_Id(Long empresaId);
    List<Passagem> findByPassageiro_Usuario_Id(Long usuarioId);
}
