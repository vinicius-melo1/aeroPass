package com.example.aeropass.domain.repository;

import com.example.aeropass.domain.entities.Piloto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PilotoRepository extends JpaRepository<Piloto, Long> {
    List<Piloto> findByEmpresa_Id(Long empresaId);
}
