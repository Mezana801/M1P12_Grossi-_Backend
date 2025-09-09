package com.example.grossi.service;

import org.springframework.stereotype.Service;

import com.example.grossi.dto.DashboardKpiDto;
import com.example.grossi.repository.StockRepository;
import com.example.grossi.repository.VenteRepository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
public class DashboardService {

    private final VenteRepository venteRepository;
    private final StockRepository stockRepository;

    public DashboardService(VenteRepository venteRepository, StockRepository stockRepository) {
        this.venteRepository = venteRepository;
        this.stockRepository = stockRepository;
    }

    public DashboardKpiDto getKpis(Integer activeClientWindowDays, Integer outOfStockLimit, String tz) {
        ZoneId zoneId = ZoneId.of(tz);
        LocalDate today = LocalDate.now(zoneId);
        Timestamp start = Timestamp.from(today.atStartOfDay(zoneId).toInstant());
        Timestamp end = Timestamp.from(today.plusDays(1).atStartOfDay(zoneId).toInstant());

        long salesCountToday = venteRepository.countSalesBetween(start, end);
        BigDecimal salesAmountToday = venteRepository.sumSalesAmountBetween(start, end);

        int window = activeClientWindowDays != null ? activeClientWindowDays : 30;
        Timestamp since = Timestamp.from(today.minusDays(window).atStartOfDay(zoneId).toInstant());
        long activeClients = venteRepository.countActiveClientsSince(since);

        var stockList = stockRepository.computeCurrentStockPerProduct();
        long inStock = stockList.stream()
                .filter(s -> s.getQuantity() != null && s.getQuantity().compareTo(BigDecimal.ZERO) > 0)
                .count();
        long outOfStock = stockList.stream()
                .filter(s -> s.getQuantity() == null || s.getQuantity().compareTo(BigDecimal.ZERO) <= 0)
                .count();

        int sample = outOfStockLimit != null ? outOfStockLimit : 10;
        List<DashboardKpiDto.OutOfStockItem> outOfStockSample = stockRepository.outOfStockSample(sample)
                .stream()
                .map(s -> new DashboardKpiDto.OutOfStockItem(s.getProductId(), s.getProductName(), s.getQuantity()))
                .toList();

        return new DashboardKpiDto(
                salesCountToday,
                salesAmountToday,
                inStock,
                outOfStock,
                outOfStockSample,
                activeClients
        );
    }
}
