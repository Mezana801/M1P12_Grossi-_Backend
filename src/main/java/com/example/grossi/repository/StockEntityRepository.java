package com.example.grossi.repository;

import com.example.grossi.model.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface StockEntityRepository extends JpaRepository<StockEntity, Integer> {

    @Query("""
       SELECT COALESCE(SUM(
           CASE 
               WHEN tm.code IN ('ENTREE', 'RETOUR_CLI', 'AJUST_POS') THEN s.quantite
               WHEN tm.code IN ('SORTIE', 'RETOUR_FOUR', 'AJUST_NEG') THEN -s.quantite
               ELSE 0
           END
       ), 0)
       FROM StockEntity s
       JOIN TypemouvementstockEntity tm ON s.typeMouvementId = tm.id
       WHERE s.produitId = :produitId
       """)
    BigDecimal getStockDisponible(@Param("produitId") Long produitId);
    ;

    @Query("SELECT s FROM StockEntity s JOIN TypemouvementstockEntity tm ON s.typeMouvementId = tm.id JOIN FETCH s.produit")
    List<StockEntity> findAllWithTypeMouvement();
}