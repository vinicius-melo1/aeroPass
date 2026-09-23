package com.example.aeropass.repository;

import com.example.aeropass.entities.Aviao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AviaoRepository extends JpaRepository<Aviao, Long> {
}
