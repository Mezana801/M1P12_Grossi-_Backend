package com.example.grossi.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "typemouvementstock", schema = "public", catalog = "grossi")
public class TypemouvementstockEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "code")
    private String code;
    @Basic
    @Column(name = "libelle")
    private String libelle;
    @Basic
    @Column(name = "impact_stock")
    private boolean impactStock;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public boolean isImpactStock() {
        return impactStock;
    }

    public void setImpactStock(boolean impactStock) {
        this.impactStock = impactStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypemouvementstockEntity that = (TypemouvementstockEntity) o;
        return id == that.id && impactStock == that.impactStock && Objects.equals(code, that.code) && Objects.equals(libelle, that.libelle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, libelle, impactStock);
    }
}
