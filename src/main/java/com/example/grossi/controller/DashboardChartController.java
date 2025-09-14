package com.example.grossi.controller;

import org.springframework.web.bind.annotation.*;

import com.example.grossi.dto.ChartDto;
import com.example.grossi.service.DashboardChartService;

import java.time.ZoneId;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/dashboard")
public class DashboardChartController {

    private final DashboardChartService service;

    public DashboardChartController(DashboardChartService service) {
        this.service = service;
    }

    /**
     * GET /api/dashboard/sales-curve?days=7&tz=Indian/Antananarivo
     */
    @GetMapping("/sales-curve")
    public List<ChartDto.SalesPoint> getSalesCurve(
            @RequestParam(name = "days", required = false, defaultValue = "7") int days,
            @RequestParam(name = "tz", required = false, defaultValue = "Indian/Antananarivo") String tz
    ) {
        return service.getSalesCurve(days, ZoneId.of(tz));
    }

    /**
     * GET /api/dashboard/top-products?days=30&limit=5
     */
    @GetMapping("/top-products")
    public List<ChartDto.ProductSales> getTopProducts(
            @RequestParam(name = "days", required = false, defaultValue = "30") int days,
            @RequestParam(name = "limit", required = false, defaultValue = "5") int limit,
            @RequestParam(name = "tz", required = false, defaultValue = "Indian/Antananarivo") String tz
    ) {
        return service.getTopProducts(days, limit, ZoneId.of(tz));
    }

    /**
     * GET /api/dashboard/sales-by-client-type?days=30
     */
    @GetMapping("/sales-by-client-type")
    public List<ChartDto.ClientTypeSales> getSalesByClientType(
            @RequestParam(name = "days", required = false, defaultValue = "30") int days,
            @RequestParam(name = "tz", required = false, defaultValue = "Indian/Antananarivo") String tz
    ) {
        return service.getSalesByClientType(days, ZoneId.of(tz));
    }
}
