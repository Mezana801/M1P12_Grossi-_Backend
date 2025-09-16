package com.example.grossi.service;

import com.example.grossi.dto.StockDTO;
import com.example.grossi.model.ProduitEntity;
import com.example.grossi.model.StockEntity;
import com.example.grossi.model.TypemouvementstockEntity;
import com.example.grossi.repository.StockEntityRepository;
import com.example.grossi.repository.TypemouvementstockEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class StockEntityService {

    private final StockEntityRepository stockRepository;
    private final TypemouvementstockEntityRepository tmRepository;

    @Autowired
    public StockEntityService(StockEntityRepository stockRepository, TypemouvementstockEntityRepository tmRepository) {
        this.stockRepository = stockRepository;
        this.tmRepository = tmRepository;
    }

    public List<StockDTO> getStocksAvecDisponible() {
        List<StockEntity> allStocks = stockRepository.findAllWithTypeMouvement();

        Map<Integer, BigDecimal> stockMap = new HashMap<>();

        // Calculer le stock dispo par produit
        for (StockEntity s : allStocks) {
            TypemouvementstockEntity tm = tmRepository.findById(s.getTypeMouvementId()).orElseThrow();
            BigDecimal q = s.getQuantite();
            if (tm.getCode().equals("SORTIE") || tm.getCode().equals("RETOUR_FOUR") || tm.getCode().equals("AJUST_NEG")) {
                q = q.negate();
            }
            stockMap.put(s.getProduitId(), stockMap.getOrDefault(s.getProduitId(), BigDecimal.ZERO).add(q));
        }

        // Transformer en DTO
        List<StockDTO> result = new ArrayList<>();
        Set<Integer> seen = new HashSet<>(); // pour éviter les doublons

        for (StockEntity s : allStocks) {
            if (seen.contains(s.getProduitId())) {
                continue; // déjà ajouté
            }

            ProduitEntity produit = s.getProduit();
            if (produit != null) {
                result.add(new StockDTO(
                        produit.getNom(),
                        produit.getPrixActuel(),
                        stockMap.get(s.getProduitId()),
                        produit.getId()
                ));
                seen.add(s.getProduitId()); // marquer comme déjà ajouté
            }
        }


        return result;
    }

}

