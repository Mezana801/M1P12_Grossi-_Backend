package com.example.grossi.service;

import com.example.grossi.model.ProduitEntity;
import com.example.grossi.repository.ProduitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitService {

    private final ProduitRepository produitRepository;

    public ProduitService(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    public List<ProduitEntity> getAllProduits() {
        return produitRepository.findByActifTrue();
    }

    public ProduitEntity getProduitById(int id) {
        return produitRepository.findById(id).orElse(null);
    }
}
