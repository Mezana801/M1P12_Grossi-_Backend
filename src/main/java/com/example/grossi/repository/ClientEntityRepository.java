package com.example.grossi.repository;

import com.example.grossi.model.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientEntityRepository extends JpaRepository<ClientEntity, Integer> {
}