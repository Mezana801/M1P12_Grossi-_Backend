package com.example.grossi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "vente", schema = "public", catalog = "grossi")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VenteEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "client_id")
    private Integer clientId;
    @Basic
    @Column(name = "date_vente")
    private Timestamp dateVente;
    @Basic
    @Column(name = "montant_total")
    private BigDecimal montantTotal;
    @Basic
    @Column(name = "statut_paiement_id")
    private Integer statutPaiementId;


    @OneToMany(mappedBy = "venteId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetailventeEntity> detailsVente = new ArrayList<>();


    public List<DetailventeEntity> getDetailsVente() {
        return detailsVente;
    }

    public void setDetailsVente(List<DetailventeEntity> detailsVente) {
        this.detailsVente = detailsVente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Timestamp getDateVente() {
        return dateVente;
    }

    public void setDateVente(Timestamp dateVente) {
        this.dateVente = dateVente;
    }

    public BigDecimal getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(BigDecimal montantTotal) {
        this.montantTotal = montantTotal;
    }

    public Integer getStatutPaiementId() {
        return statutPaiementId;
    }

    public void setStatutPaiementId(Integer statutPaiementId) {
        this.statutPaiementId = statutPaiementId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VenteEntity that = (VenteEntity) o;
        return id == that.id && Objects.equals(clientId, that.clientId) && Objects.equals(dateVente, that.dateVente) && Objects.equals(montantTotal, that.montantTotal) && Objects.equals(statutPaiementId, that.statutPaiementId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId, dateVente, montantTotal, statutPaiementId);
    }
}
