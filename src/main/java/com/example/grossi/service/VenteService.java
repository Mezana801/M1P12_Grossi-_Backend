package com.example.grossi.service;

import com.example.grossi.dto.VenteRequest;
import com.example.grossi.model.*;
import com.example.grossi.repository.*;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VenteService {

    @Autowired
    private ClientEntityRepository clientRepository;

    @Autowired
    private ProduitEntityRepository produitRepository;

    @Autowired
    private StockEntityRepository stockRepository;

    @Autowired
    private VenteEntityRepository venteRepository;

    @Autowired
    private DetailventeEntityRepository detailVenteRepository;

    @Autowired
    private FactureEntityRepository factureRepository;

    @Transactional
    public VenteEntity enregistrerVente(Integer clientId, List<VenteRequest.DetailVenteRequest> details) {

        ClientEntity client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));

        // Calcul du montant total
        BigDecimal montantTotal = details.stream()
                .map(d -> d.getPrixUnitaire().multiply(d.getQuantite()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Création de la vente
        VenteEntity vente = new VenteEntity();
        vente.setClientId(clientId);
        vente.setMontantTotal(montantTotal);
        vente = venteRepository.save(vente);

        // Pour chaque détail de vente
        for (VenteRequest.DetailVenteRequest d : details) {
            ProduitEntity produit = produitRepository.findById(Math.toIntExact(d.getProduitId()))
                    .orElseThrow(() -> new RuntimeException("Produit non trouvé : " + d.getProduitId()));

            // Vérifier le stock disponible
            BigDecimal stockDisponible = stockRepository.getStockDisponible((long) produit.getId());
            if (stockDisponible.compareTo(d.getQuantite()) <= 0) {
                throw new RuntimeException("Stock insuffisant pour le produit : " + produit.getNom());
            }

            // Décrémenter directement la quantité dans le produit
            produit.setPrixActuel(produit.getPrixActuel()); // reste le même
            produitRepository.save(produit);

            // Créer le mouvement de sortie
            StockEntity mouvementSortie = new StockEntity();
            mouvementSortie.setProduitId(produit.getId());
            mouvementSortie.setTypeMouvementId(2); // SORTIE
            mouvementSortie.setQuantite(d.getQuantite());
            mouvementSortie.setPrixUnitaire(d.getPrixUnitaire());
            mouvementSortie.setDateMouvement(new Timestamp(System.currentTimeMillis()));
            stockRepository.save(mouvementSortie);

            // Créer le détail de vente
            DetailventeEntity detail = new DetailventeEntity();
            detail.setVenteId(vente.getId());
            detail.setProduitId(produit.getId());
            detail.setQuantite(d.getQuantite());
            detail.setPrixUnitaire(d.getPrixUnitaire());
            detail.setSousTotal(d.getPrixUnitaire().multiply(d.getQuantite()));
            detailVenteRepository.save(detail);
        }

        // Générer la facture PDF
        FactureEntity facture = genererFacturePDF(vente);
        factureRepository.save(facture);

        return vente;
    }

    private FactureEntity genererFacturePDF(VenteEntity vente) {
        // Ici tu mets ton code pour générer un PDF et retourner l'entité Facture
        FactureEntity facture = new FactureEntity();
        facture.setVenteId(vente.getId());
        facture.setNumeroFacture("FAC-" + vente.getId());
        facture.setPdfPath("/factures/fac-" + vente.getId() + ".pdf");
        return facture;
    }
}

