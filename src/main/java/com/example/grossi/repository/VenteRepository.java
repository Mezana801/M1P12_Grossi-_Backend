package com.example.grossi.repository;

import com.example.grossi.model.VenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.sql.Timestamp;

public interface VenteRepository extends JpaRepository<VenteEntity, Integer> {

    @Query("SELECT COUNT(v) FROM VenteEntity v WHERE v.dateVente >= :start AND v.dateVente < :end")
    long countSalesBetween(Timestamp start, Timestamp end);

    @Query("SELECT COALESCE(SUM(v.montantTotal),0) FROM VenteEntity v WHERE v.dateVente >= :start AND v.dateVente < :end")
    BigDecimal sumSalesAmountBetween(Timestamp start, Timestamp end);

    @Query("SELECT COUNT(DISTINCT v.clientId) FROM VenteEntity v WHERE v.dateVente >= :since")
    long countActiveClientsSince(Timestamp since);
}
