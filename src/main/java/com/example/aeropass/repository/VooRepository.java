package com.example.aeropass.repository;

import com.example.aeropass.entities.Voo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VooRepository extends JpaRepository<Voo,Long> {
}
