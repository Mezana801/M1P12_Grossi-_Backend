package com.example.grossi.repository;

import com.example.grossi.model.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockEntityRepository extends JpaRepository<StockEntity, Integer> {
}