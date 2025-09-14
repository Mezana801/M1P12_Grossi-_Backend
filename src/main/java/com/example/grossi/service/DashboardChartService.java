package com.example.grossi.service;

import org.springframework.stereotype.Service;

import com.example.grossi.dto.ChartDto;
import com.example.grossi.repository.DetailVenteRepository;
import com.example.grossi.repository.VenteRepository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardChartService {

    private final VenteRepository venteRepository;
    private final DetailVenteRepository detailVenteRepository;

    public DashboardChartService(VenteRepository venteRepository, DetailVenteRepository detailVenteRepository) {
        this.venteRepository = venteRepository;
        this.detailVenteRepository = detailVenteRepository;
    }

    /**
     * Renvoyer la courbe des ventes (points par jour) sur la période fournie.
     * periodDays : nombre de jours en arrière (ex: 7 pour dernière semaine, 30 pour 1 mois)
     */
    public List<ChartDto.SalesPoint> getSalesCurve(int periodDays, ZoneId zone) {
        LocalDate today = LocalDate.now(zone);
        Instant startInstant = today.minusDays(periodDays - 1).atStartOfDay(zone).toInstant();
        Instant endInstant = today.plusDays(1).atStartOfDay(zone).toInstant();

        Timestamp start = Timestamp.from(startInstant);
        Timestamp end = Timestamp.from(endInstant);

        List<VenteRepository.SalesPointProjection> raw = venteRepository.findSalesPointsByPeriod(start, end);

        // Map SQL projections to DTO, and fill missing dates with zero if desired
        List<ChartDto.SalesPoint> points = raw.stream()
                .map(p -> new ChartDto.SalesPoint(
                        ((java.sql.Date) p.getDay()).toLocalDate(),
                        p.getSalesCount() != null ? p.getSalesCount() : 0L,
                        p.getSalesAmount() != null ? p.getSalesAmount() : BigDecimal.ZERO
                ))
                .collect(Collectors.toList());

        // Optionally fill gaps for days with zero sales
        // Build a map date->point for quick lookup
        var map = points.stream().collect(Collectors.toMap(sp -> sp.date, sp -> sp));
        List<ChartDto.SalesPoint> full = startInstant.atZone(zone).toLocalDate().datesUntil(endInstant.atZone(zone).toLocalDate().plusDays(0))
                .map(d -> map.getOrDefault(d, new ChartDto.SalesPoint(d, 0L, BigDecimal.ZERO)))
                .collect(Collectors.toList());

        return full;
    }

    /**
     * Top N produits par quantité sur période
     */
    public List<ChartDto.ProductSales> getTopProducts(int periodDays, int limit, ZoneId zone) {
        LocalDate today = LocalDate.now(zone);
        Instant startInstant = today.minusDays(periodDays - 1).atStartOfDay(zone).toInstant();
        Instant endInstant = today.plusDays(1).atStartOfDay(zone).toInstant();

        Timestamp start = Timestamp.from(startInstant);
        Timestamp end = Timestamp.from(endInstant);

        List<DetailVenteRepository.ProductSalesProjection> raw = detailVenteRepository.findTopProductsByPeriod(start, end, limit);

        return raw.stream()
                .map(p -> new ChartDto.ProductSales(
                        p.getProductId(),
                        p.getProductName(),
                        p.getQuantity() != null ? p.getQuantity() : BigDecimal.ZERO,
                        p.getAmount() != null ? p.getAmount() : BigDecimal.ZERO
                ))
                .collect(Collectors.toList());
    }

    /**
     * Répartition des ventes par type client
     */
    public List<ChartDto.ClientTypeSales> getSalesByClientType(int periodDays, ZoneId zone) {
        LocalDate today = LocalDate.now(zone);
        Instant startInstant = today.minusDays(periodDays - 1).atStartOfDay(zone).toInstant();
        Instant endInstant = today.plusDays(1).atStartOfDay(zone).toInstant();

        Timestamp start = Timestamp.from(startInstant);
        Timestamp end = Timestamp.from(endInstant);

        List<VenteRepository.ClientTypeProjection> raw = venteRepository.findSalesByClientType(start, end);

        return raw.stream()
                .map(p -> new ChartDto.ClientTypeSales(
                        p.getTypeClientId(),
                        p.getTypeName(),
                        p.getOrdersCount() != null ? p.getOrdersCount() : 0L,
                        p.getTotalAmount() != null ? p.getTotalAmount() : BigDecimal.ZERO
                ))
                .collect(Collectors.toList());
    }
}
