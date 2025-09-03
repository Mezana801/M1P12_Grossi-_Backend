package com.example.grossi.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "produit", schema = "public", catalog = "grossi")
public class ProduitEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "nom")
    private String nom;
    @Basic
    @Column(name = "categorie_id")
    private Integer categorieId;
    @Basic
    @Column(name = "unite_id")
    private Integer uniteId;
    @Basic
    @Column(name = "prix_actuel")
    private BigDecimal prixActuel;
    @Basic
    @Column(name = "actif")
    private Boolean actif;
    @Basic
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Basic
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(Integer categorieId) {
        this.categorieId = categorieId;
    }

    public Integer getUniteId() {
        return uniteId;
    }

    public void setUniteId(Integer uniteId) {
        this.uniteId = uniteId;
    }

    public BigDecimal getPrixActuel() {
        return prixActuel;
    }

    public void setPrixActuel(BigDecimal prixActuel) {
        this.prixActuel = prixActuel;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProduitEntity that = (ProduitEntity) o;
        return id == that.id && Objects.equals(nom, that.nom) && Objects.equals(categorieId, that.categorieId) && Objects.equals(uniteId, that.uniteId) && Objects.equals(prixActuel, that.prixActuel) && Objects.equals(actif, that.actif) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, categorieId, uniteId, prixActuel, actif, createdAt, updatedAt);
    }
}
