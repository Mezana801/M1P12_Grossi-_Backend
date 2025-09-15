package com.example.grossi.repository;

import com.example.grossi.model.ProduitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<ProduitEntity, Integer> {
    List<ProduitEntity> findByActifTrue();
}
