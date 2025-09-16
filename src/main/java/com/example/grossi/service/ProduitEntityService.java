package com.example.grossi.service;

import com.example.grossi.model.ProduitEntity;
import com.example.grossi.repository.ProduitEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitEntityService {

    private final ProduitEntityRepository produitEntityRepository;

    @Autowired
    public ProduitEntityService(ProduitEntityRepository produitEntityRepository) {
        this.produitEntityRepository = produitEntityRepository;
    }

    public List<ProduitEntity> findAllProduit(){
        return produitEntityRepository.findAll();
    }
}
