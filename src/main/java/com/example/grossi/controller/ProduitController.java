package com.example.grossi.controller;

import com.example.grossi.model.ProduitEntity;
import com.example.grossi.service.ProduitService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProduitController {

    private final ProduitService produitService;

    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @GetMapping
    public List<ProduitEntity> getProduits() {
        return produitService.getAllProduits();
    }

    @GetMapping("/{id}")
    public ProduitEntity getProduit(@PathVariable int id) {
        return produitService.getProduitById(id);
    }
}
