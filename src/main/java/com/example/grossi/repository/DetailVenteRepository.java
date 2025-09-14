package com.example.grossi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.grossi.model.DetailventeEntity;
import java.math.BigDecimal;
import java.util.List;

public interface DetailVenteRepository extends JpaRepository<DetailventeEntity, Integer> {

    interface ProductSalesProjection {
        Integer getProductId();
        String getProductName();
        BigDecimal getQuantity();
        BigDecimal getAmount();
    }

    @Query(value = """
        SELECT p.id AS productId,
               p.nom AS productName,
               COALESCE(SUM(d.quantite),0) AS quantity,
               COALESCE(SUM(d.sous_total),0) AS amount
        FROM detailvente d
        JOIN produit p ON p.id = d.produit_id
        JOIN vente v ON v.id = d.vente_id
        WHERE v.date_vente >= :start AND v.date_vente < :end
        GROUP BY p.id, p.nom
        ORDER BY COALESCE(SUM(d.quantite),0) DESC
        LIMIT :limit
        """, nativeQuery = true)
    List<ProductSalesProjection> findTopProductsByPeriod(@Param("start") java.sql.Timestamp start,
                                                         @Param("end") java.sql.Timestamp end,
                                                         @Param("limit") int limit);
}