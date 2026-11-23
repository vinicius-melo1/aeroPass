package com.example.aeropass.domain.repository;

import com.example.aeropass.domain.entities.Passageiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassageiroRepository extends JpaRepository<Passageiro, Long> {
    Optional<Passageiro> findByUsuarioId(Long usuarioId);
}
