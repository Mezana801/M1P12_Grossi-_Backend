package com.example.grossi.dto;

import java.math.BigDecimal;
import java.util.List;

public class VenteRequest {

    private Long clientId;
    private List<DetailVenteRequest> details;

    // Getters et Setters
    public Long getClientId() {
        return clientId;
    }
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
    public List<DetailVenteRequest> getDetails() {
        return details;
    }
    public void setDetails(List<DetailVenteRequest> details) {
        this.details = details;
    }

    // Classe interne pour chaque détail de vente
    public static class DetailVenteRequest {
        private Long produitId;
        private BigDecimal quantite;
        private BigDecimal prixUnitaire;

        public Long getProduitId() {
            return produitId;
        }
        public void setProduitId(Long produitId) {
            this.produitId = produitId;
        }
        public BigDecimal getQuantite() {
            return quantite;
        }
        public void setQuantite(BigDecimal quantite) {
            this.quantite = quantite;
        }
        public BigDecimal getPrixUnitaire() {
            return prixUnitaire;
        }
        public void setPrixUnitaire(BigDecimal prixUnitaire) {
            this.prixUnitaire = prixUnitaire;
        }
    }
}
