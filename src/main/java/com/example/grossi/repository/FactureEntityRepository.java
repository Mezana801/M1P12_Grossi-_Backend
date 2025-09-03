package com.example.grossi.repository;

import com.example.grossi.model.FactureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FactureEntityRepository extends JpaRepository<FactureEntity, Integer> {
}