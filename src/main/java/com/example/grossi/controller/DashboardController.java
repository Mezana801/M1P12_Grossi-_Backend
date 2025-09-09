package com.example.grossi.controller;

import org.springframework.web.bind.annotation.*;

import com.example.grossi.dto.DashboardKpiDto;
import com.example.grossi.service.DashboardService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService service;

    public DashboardController(DashboardService service) {
        this.service = service;
    }

    @GetMapping("/kpis")
    public DashboardKpiDto getKpis(
            @RequestParam(name = "activeClientWindowDays", required = false) Integer activeClientWindowDays,
            @RequestParam(name = "outOfStockLimit", required = false) Integer outOfStockLimit,
            @RequestParam(name = "tz", required = false, defaultValue = "Indian/Antananarivo") String tz
    ) {
        return service.getKpis(activeClientWindowDays, outOfStockLimit, tz);
    }
}
