package com.example.grossi.dto;

import java.math.BigDecimal;
import java.util.List;

public class DashboardKpiDto {

    public static class OutOfStockItem {
        public int productId;
        public String productName;
        public BigDecimal quantity;

        public OutOfStockItem(int productId, String productName, BigDecimal quantity) {
            this.productId = productId;
            this.productName = productName;
            this.quantity = quantity;
        }
    }

    public long salesCountToday;
    public BigDecimal salesAmountToday;
    public long productsInStockCount;
    public long productsOutOfStockCount;
    public List<OutOfStockItem> outOfStockSample;
    public long activeClientsCount;

    public DashboardKpiDto(long salesCountToday,
                           BigDecimal salesAmountToday,
                           long productsInStockCount,
                           long productsOutOfStockCount,
                           List<OutOfStockItem> outOfStockSample,
                           long activeClientsCount) {
        this.salesCountToday = salesCountToday;
        this.salesAmountToday = salesAmountToday;
        this.productsInStockCount = productsInStockCount;
        this.productsOutOfStockCount = productsOutOfStockCount;
        this.outOfStockSample = outOfStockSample;
        this.activeClientsCount = activeClientsCount;
    }
}
