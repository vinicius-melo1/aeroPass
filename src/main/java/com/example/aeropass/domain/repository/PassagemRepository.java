package com.example.aeropass.domain.repository;

import com.example.aeropass.domain.entities.Passagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassagemRepository extends JpaRepository<Passagem, Long> {
}
