package com.example.grossi.controller;

import com.example.grossi.model.ClientEntity;
import com.example.grossi.model.ProduitEntity;
import com.example.grossi.service.ClientEntityService;
import com.example.grossi.service.ProduitEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
public class ProduitEnityController {

    private final ProduitEntityService produitService;

    @Autowired
    public ProduitEnityController(ProduitEntityService produitService) {
        this.produitService = produitService;
    }

    @GetMapping
    public List<ProduitEntity> getProduit() {
        return produitService.findAllProduit();
    }
}
