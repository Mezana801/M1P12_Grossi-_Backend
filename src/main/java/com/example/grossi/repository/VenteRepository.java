package com.example.grossi.repository;

import com.example.grossi.model.VenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.sql.Timestamp;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface VenteRepository extends JpaRepository<VenteEntity, Integer> {

    @Query("SELECT COUNT(v) FROM VenteEntity v WHERE v.dateVente >= :start AND v.dateVente < :end")
    long countSalesBetween(Timestamp start, Timestamp end);

    @Query("SELECT COALESCE(SUM(v.montantTotal),0) FROM VenteEntity v WHERE v.dateVente >= :start AND v.dateVente < :end")
    BigDecimal sumSalesAmountBetween(Timestamp start, Timestamp end);

    @Query("SELECT COUNT(DISTINCT v.clientId) FROM VenteEntity v WHERE v.dateVente >= :since")
    long countActiveClientsSince(Timestamp since);

    // ventes agrégées par jour
        interface SalesPointProjection {
            java.sql.Date getDay(); // will map to date
            Long getSalesCount();
            BigDecimal getSalesAmount();
        }

        @Query(value = """
            SELECT DATE_TRUNC('day', v.date_vente)::date AS day,
                COUNT(*) AS salesCount,
                COALESCE(SUM(v.montant_total),0) AS salesAmount
            FROM vente v
            WHERE v.date_vente >= :start AND v.date_vente < :end
            GROUP BY day
            ORDER BY day
            """, nativeQuery = true)
        List<SalesPointProjection> findSalesPointsByPeriod(@Param("start") Timestamp start, @Param("end") Timestamp end);

        // répartition par type de client
        interface ClientTypeProjection {
            Integer getTypeClientId();
            String getTypeName();
            Long getOrdersCount();
            BigDecimal getTotalAmount();
        }

        @Query(value = """
            SELECT tc.id AS typeClientId,
                tc.type AS typeName,
                COUNT(v.id) AS ordersCount,
                COALESCE(SUM(v.montant_total),0) AS totalAmount
            FROM vente v
            JOIN client c ON c.id = v.client_id
            LEFT JOIN typeclient tc ON tc.id = c.type_client_id
            WHERE v.date_vente >= :start AND v.date_vente < :end
            GROUP BY tc.id, tc.type
            ORDER BY totalAmount DESC
            """, nativeQuery = true)
        List<ClientTypeProjection> findSalesByClientType(@Param("start") Timestamp start, @Param("end") Timestamp end);
}
