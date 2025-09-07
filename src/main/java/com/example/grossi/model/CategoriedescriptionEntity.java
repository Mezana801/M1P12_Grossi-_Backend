package com.example.grossi.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "categoriedescription", schema = "public", catalog = "grossi")
public class CategoriedescriptionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "categorie_id")
    private Integer categorieId;
    @Basic
    @Column(name = "description")
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(Integer categorieId) {
        this.categorieId = categorieId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriedescriptionEntity that = (CategoriedescriptionEntity) o;
        return id == that.id && Objects.equals(categorieId, that.categorieId) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categorieId, description);
    }
}
