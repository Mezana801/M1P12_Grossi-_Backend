package com.example.grossi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.grossi.model.StockEntity;

import java.util.List;

public interface StockRepository extends JpaRepository<StockEntity, Integer> {

    interface StockProjection {
        int getProductId();
        String getProductName();
        java.math.BigDecimal getQuantity();
    }

    @Query(value = """
        SELECT p.id AS productId,
               p.nom AS productName,
               COALESCE(SUM(
                 CASE
                   WHEN t.impact_stock = true THEN s.quantite
                   ELSE -s.quantite
                 END
               ),0) AS quantity
        FROM produit p
        LEFT JOIN stock s ON s.produit_id = p.id
        LEFT JOIN typemouvementstock t ON t.id = s.type_mouvement_id
        GROUP BY p.id, p.nom
        """, nativeQuery = true)
    List<StockProjection> computeCurrentStockPerProduct();

    @Query(value = """
        SELECT p.id AS productId,
               p.nom AS productName,
               COALESCE(SUM(
                 CASE
                   WHEN t.impact_stock = true THEN s.quantite
                   ELSE -s.quantite
                 END
               ),0) AS quantity
        FROM produit p
        LEFT JOIN stock s ON s.produit_id = p.id
        LEFT JOIN typemouvementstock t ON t.id = s.type_mouvement_id
        GROUP BY p.id, p.nom
        HAVING COALESCE(SUM(
                 CASE
                   WHEN t.impact_stock = true THEN s.quantite
                   ELSE -s.quantite
                 END
               ),0) <= 0
        ORDER BY quantity ASC, p.nom ASC
        LIMIT :limit
        """, nativeQuery = true)
    List<StockProjection> outOfStockSample(int limit);
}