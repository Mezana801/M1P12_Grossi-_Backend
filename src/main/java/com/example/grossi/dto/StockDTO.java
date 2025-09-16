package com.example.grossi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO {

    private int stockId;
    private String produitNom;
    private BigDecimal prixUnitaire;

    private BigDecimal stockDisponible;

    public StockDTO() {
    }



    public StockDTO(String produitNom, BigDecimal prixUnitaire, BigDecimal stockDisponible,int stockId) {
        this.produitNom = produitNom;
        this.prixUnitaire = prixUnitaire;
        this.stockDisponible = stockDisponible;
        this.stockId=stockId;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public String getProduitNom() {
        return produitNom;
    }

    public void setProduitNom(String produitNom) {
        this.produitNom = produitNom;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public BigDecimal getStockDisponible() {
        return stockDisponible;
    }

    public void setStockDisponible(BigDecimal stockDisponible) {
        this.stockDisponible = stockDisponible;
    }
}