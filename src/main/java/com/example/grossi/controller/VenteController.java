package com.example.grossi.controller;

import com.example.grossi.dto.VenteRequest;
import com.example.grossi.model.VenteEntity;
import com.example.grossi.service.VenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ventes")
@CrossOrigin(origins = "*")
public class VenteController {

    private final VenteService venteService;

    @Autowired
    public VenteController(VenteService venteService) {
        this.venteService = venteService;
    }

    @PostMapping
    public ResponseEntity<VenteEntity> creerVente(@RequestBody VenteRequest request) {
        VenteEntity vente = venteService.enregistrerVente(Math.toIntExact(request.getClientId()), request.getDetails());
        return ResponseEntity.ok(vente);
    }


}
