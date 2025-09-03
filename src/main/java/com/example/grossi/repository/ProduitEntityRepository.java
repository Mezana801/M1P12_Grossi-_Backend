package com.example.grossi.repository;

import com.example.grossi.model.ProduitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitEntityRepository extends JpaRepository<ProduitEntity, Integer> {
}