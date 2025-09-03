package com.example.grossi.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "devis", schema = "public", catalog = "grossi")
public class DevisEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "client_id")
    private int clientId;
    @Basic
    @Column(name = "date_creation")
    private Timestamp dateCreation;
    @Basic
    @Column(name = "date_expiration")
    private Timestamp dateExpiration;
    @Basic
    @Column(name = "montant_estime")
    private BigDecimal montantEstime;
    @Basic
    @Column(name = "statut_id")
    private Integer statutId;
    @Basic
    @Column(name = "pdf_path")
    private String pdfPath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public Timestamp getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Timestamp dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Timestamp getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(Timestamp dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public BigDecimal getMontantEstime() {
        return montantEstime;
    }

    public void setMontantEstime(BigDecimal montantEstime) {
        this.montantEstime = montantEstime;
    }

    public Integer getStatutId() {
        return statutId;
    }

    public void setStatutId(Integer statutId) {
        this.statutId = statutId;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DevisEntity that = (DevisEntity) o;
        return id == that.id && clientId == that.clientId && Objects.equals(dateCreation, that.dateCreation) && Objects.equals(dateExpiration, that.dateExpiration) && Objects.equals(montantEstime, that.montantEstime) && Objects.equals(statutId, that.statutId) && Objects.equals(pdfPath, that.pdfPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId, dateCreation, dateExpiration, montantEstime, statutId, pdfPath);
    }
}
