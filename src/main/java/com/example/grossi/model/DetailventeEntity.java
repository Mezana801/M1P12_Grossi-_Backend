package com.example.grossi.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "detailvente", schema = "public", catalog = "grossi")
public class DetailventeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "vente_id")
    private int venteId;
    @Basic
    @Column(name = "produit_id")
    private int produitId;
    @Basic
    @Column(name = "quantite")
    private BigDecimal quantite;
    @Basic
    @Column(name = "prix_unitaire")
    private BigDecimal prixUnitaire;
    @Basic
    @Column(name = "sous_total")
    private BigDecimal sousTotal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVenteId() {
        return venteId;
    }

    public void setVenteId(int venteId) {
        this.venteId = venteId;
    }

    public int getProduitId() {
        return produitId;
    }

    public void setProduitId(int produitId) {
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

    public BigDecimal getSousTotal() {
        return sousTotal;
    }

    public void setSousTotal(BigDecimal sousTotal) {
        this.sousTotal = sousTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetailventeEntity that = (DetailventeEntity) o;
        return id == that.id && venteId == that.venteId && produitId == that.produitId && Objects.equals(quantite, that.quantite) && Objects.equals(prixUnitaire, that.prixUnitaire) && Objects.equals(sousTotal, that.sousTotal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, venteId, produitId, quantite, prixUnitaire, sousTotal);
    }
}
