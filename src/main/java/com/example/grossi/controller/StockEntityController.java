package com.example.grossi.controller;

import com.example.grossi.dto.StockDTO;
import com.example.grossi.model.StockEntity;
import com.example.grossi.service.StockEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockEntityController {

    private final StockEntityService stockService;

    @Autowired
    public StockEntityController(StockEntityService stockService) {
        this.stockService = stockService;
    }

    // ✅ GET /api/stocks -> renvoie tous les stocks avec stock disponible
    @GetMapping
    public ResponseEntity<List<StockDTO>> getAllStocks() {
        List<StockDTO> stocks = stockService.getStocksAvecDisponible();
        return ResponseEntity.ok(stocks);
    }

    // ✅ GET /api/stocks/{produitId} -> renvoie le stock disponible d’un produit
    /*@GetMapping("/{produitId}")
    public ResponseEntity<StockDTO> getStockByProduit(@PathVariable int produitId) {
        List<StockDTO> stocks = stockService.getStocksAvecDisponible();

        StockDTO stock = stocks.stream()
                .filter(s -> s.getProduitNom() != null && s.getProduitNom().length() > 0) // sécurité
                .filter(s -> s.get() == produitId) // ⚠️ Ajoute produitId dans ton DTO si tu veux filtrer ainsi
                .findFirst()
                .orElse(null);

        if (stock != null) {
            return ResponseEntity.ok(stock);
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/

}
