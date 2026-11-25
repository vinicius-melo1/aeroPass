package com.example.aeropass.domain.repository;

import com.example.aeropass.application.DTOs.EnumStatusPassagem;
import com.example.aeropass.domain.entities.Passagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PassagemRepository extends JpaRepository<Passagem, Long> {
    List<Passagem> findByVooId(Long vooId);
    List<Passagem> findByVoo_Empresa_Id(Long empresaId);
    List<Passagem> findByPassageiro_Usuario_IdAndStatusNot(Long usuarioId, EnumStatusPassagem status);
    List<Passagem> findByVooIdAndStatusIn(Long vooId, Collection<EnumStatusPassagem> status);
    boolean existsByPassageiro_IdAndVoo_Empresa_Id(Long passageiroId, Long empresaId);
}
