package com.example.grossi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ChartDto {

    // point ventes par jour/semaine
    public static class SalesPoint {
        public LocalDate date;      // date (day) or start of week
        public long salesCount;
        public BigDecimal salesAmount;

        public SalesPoint(LocalDate date, long salesCount, BigDecimal salesAmount) {
            this.date = date;
            this.salesCount = salesCount;
            this.salesAmount = salesAmount;
        }
    }

    // top produits
    public static class ProductSales {
        public int productId;
        public String productName;
        public BigDecimal quantity;   // quantité vendue
        public BigDecimal amount;     // chiffre d'affaires pour ce produit

        public ProductSales(int productId, String productName, BigDecimal quantity, BigDecimal amount) {
            this.productId = productId;
            this.productName = productName;
            this.quantity = quantity;
            this.amount = amount;
        }
    }

    // répartition ventes par type client
    public static class ClientTypeSales {
        public Integer typeClientId;
        public String typeClientName;
        public long ordersCount;
        public BigDecimal totalAmount;

        public ClientTypeSales(Integer typeClientId, String typeClientName, long ordersCount, BigDecimal totalAmount) {
            this.typeClientId = typeClientId;
            this.typeClientName = typeClientName;
            this.ordersCount = ordersCount;
            this.totalAmount = totalAmount;
        }
    }
}