package com.example.grossi.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "unite", schema = "public", catalog = "grossi")
public class UniteEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "nom")
    private String nom;
    @Basic
    @Column(name = "symbole")
    private String symbole;

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

    public String getSymbole() {
        return symbole;
    }

    public void setSymbole(String symbole) {
        this.symbole = symbole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniteEntity that = (UniteEntity) o;
        return id == that.id && Objects.equals(nom, that.nom) && Objects.equals(symbole, that.symbole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, symbole);
    }
}
