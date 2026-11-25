package com.example.aeropass.domain.repository;

import com.example.aeropass.domain.entities.Voo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VooRepository extends JpaRepository<Voo,Long> {
    List<Voo> getVoosByEmpresa_Id(Long empresa);
}
