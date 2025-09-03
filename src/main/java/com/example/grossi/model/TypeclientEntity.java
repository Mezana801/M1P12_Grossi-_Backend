package com.example.grossi.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "typeclient", schema = "public", catalog = "grossi")
public class TypeclientEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "remise")
    private BigDecimal remise;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getRemise() {
        return remise;
    }

    public void setRemise(BigDecimal remise) {
        this.remise = remise;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeclientEntity that = (TypeclientEntity) o;
        return id == that.id && Objects.equals(type, that.type) && Objects.equals(remise, that.remise);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, remise);
    }
}
