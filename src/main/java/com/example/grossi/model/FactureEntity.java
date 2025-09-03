package com.example.grossi.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "facture", schema = "public", catalog = "grossi")
public class FactureEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "vente_id")
    private int venteId;
    @Basic
    @Column(name = "numero_facture")
    private String numeroFacture;
    @Basic
    @Column(name = "pdf_path")
    private String pdfPath;
    @Basic
    @Column(name = "date_emission")
    private Timestamp dateEmission;

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

    public String getNumeroFacture() {
        return numeroFacture;
    }

    public void setNumeroFacture(String numeroFacture) {
        this.numeroFacture = numeroFacture;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public Timestamp getDateEmission() {
        return dateEmission;
    }

    public void setDateEmission(Timestamp dateEmission) {
        this.dateEmission = dateEmission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FactureEntity that = (FactureEntity) o;
        return id == that.id && venteId == that.venteId && Objects.equals(numeroFacture, that.numeroFacture) && Objects.equals(pdfPath, that.pdfPath) && Objects.equals(dateEmission, that.dateEmission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, venteId, numeroFacture, pdfPath, dateEmission);
    }
}
