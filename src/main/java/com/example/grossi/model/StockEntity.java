package com.example.grossi.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "stock", schema = "public", catalog = "grossi")
public class StockEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "produit_id")
    private int produitId;
    @Basic
    @Column(name = "type_mouvement_id")
    private int typeMouvementId;
    @Basic
    @Column(name = "quantite")
    private BigDecimal quantite;
    @Basic
    @Column(name = "prix_unitaire")
    private BigDecimal prixUnitaire;
    @Basic
    @Column(name = "date_mouvement")
    private Timestamp dateMouvement;
    @Basic
    @Column(name = "reference_id")
    private Integer referenceId;
    @Basic
    @Column(name = "type_reference_id")
    private Integer typeReferenceId;
    @Basic
    @Column(name = "notes")
    private String notes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduitId() {
        return produitId;
    }

    public void setProduitId(int produitId) {
        this.produitId = produitId;
    }

    public int getTypeMouvementId() {
        return typeMouvementId;
    }

    public void setTypeMouvementId(int typeMouvementId) {
        this.typeMouvementId = typeMouvementId;
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

    public Timestamp getDateMouvement() {
        return dateMouvement;
    }

    public void setDateMouvement(Timestamp dateMouvement) {
        this.dateMouvement = dateMouvement;
    }

    public Integer getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Integer referenceId) {
        this.referenceId = referenceId;
    }

    public Integer getTypeReferenceId() {
        return typeReferenceId;
    }

    public void setTypeReferenceId(Integer typeReferenceId) {
        this.typeReferenceId = typeReferenceId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockEntity that = (StockEntity) o;
        return id == that.id && produitId == that.produitId && typeMouvementId == that.typeMouvementId && Objects.equals(quantite, that.quantite) && Objects.equals(prixUnitaire, that.prixUnitaire) && Objects.equals(dateMouvement, that.dateMouvement) && Objects.equals(referenceId, that.referenceId) && Objects.equals(typeReferenceId, that.typeReferenceId) && Objects.equals(notes, that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, produitId, typeMouvementId, quantite, prixUnitaire, dateMouvement, referenceId, typeReferenceId, notes);
    }
}
