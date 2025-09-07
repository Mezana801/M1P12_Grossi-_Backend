drop database if exists grossi;
Create database grossi;
drop role if exists m1;
CREATE ROLE m1 LOGIN password 'p12';
ALTER DATABASE grossi OWNER TO m1;

-- =========================
-- TABLES DE RÉFÉRENCE
-- =========================

CREATE TABLE Categorie (
                           id SERIAL PRIMARY KEY,
                           nom VARCHAR(100) NOT NULL
);

CREATE TABLE CategorieDescription (
                                      id SERIAL PRIMARY KEY,
                                      categorie_id INT REFERENCES Categorie(id) ON DELETE CASCADE,
                                      description TEXT NOT NULL
);


CREATE TABLE Unite (
                       id SERIAL PRIMARY KEY,
                       nom VARCHAR(50) NOT NULL,
                       symbole VARCHAR(20) NOT NULL
);

CREATE TABLE TypeClient (
                            id SERIAL PRIMARY KEY,
                            type VARCHAR(50) NOT NULL,
                            remise NUMERIC(5,2) DEFAULT 0
);

CREATE TABLE TypeMouvementStock (
                                    id SERIAL PRIMARY KEY,
                                    code VARCHAR(20) UNIQUE NOT NULL,
                                    libelle VARCHAR(100) NOT NULL,
                                    impact_stock BOOLEAN NOT NULL
);

CREATE TABLE TypeReferenceStock (
                                    id SERIAL PRIMARY KEY,
                                    code VARCHAR(20) UNIQUE NOT NULL,
                                    libelle VARCHAR(100) NOT NULL
);

CREATE TABLE StatutPaiement (
                                id SERIAL PRIMARY KEY,
                                code VARCHAR(20) UNIQUE NOT NULL,
                                libelle VARCHAR(100) NOT NULL
);

CREATE TABLE StatutDevis (
                             id SERIAL PRIMARY KEY,
                             code VARCHAR(20) UNIQUE NOT NULL,
                             libelle VARCHAR(100) NOT NULL
);

CREATE TABLE RoleUtilisateur (
                                 id SERIAL PRIMARY KEY,
                                 code VARCHAR(20) UNIQUE NOT NULL,
                                 libelle VARCHAR(100) NOT NULL
);

-- =========================
-- TABLES MÉTIERS
-- =========================

CREATE TABLE Produit (
                         id SERIAL PRIMARY KEY,
                         nom VARCHAR(150) NOT NULL,
                         categorie_id INT REFERENCES CategorieDescription(id) ON DELETE SET NULL,
                         unite_id INT REFERENCES Unite(id) ON DELETE SET NULL,
                         prix_actuel NUMERIC(12,2) NOT NULL,
                         actif BOOLEAN DEFAULT TRUE,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Stock (
                       id SERIAL PRIMARY KEY,
                       produit_id INT NOT NULL REFERENCES Produit(id) ON DELETE CASCADE,
                       type_mouvement_id INT NOT NULL REFERENCES TypeMouvementStock(id),
                       quantite NUMERIC(12,2) NOT NULL,
                       prix_unitaire NUMERIC(12,2),
                       date_mouvement TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       reference_id INT,
                       type_reference_id INT REFERENCES TypeReferenceStock(id),
                       notes TEXT
);

CREATE TABLE Client (
                        id SERIAL PRIMARY KEY,
                        nom VARCHAR(150) NOT NULL,
                        contact VARCHAR(100),
                        type_client_id INT REFERENCES TypeClient(id),
                        adresse TEXT,
                        email VARCHAR(150) UNIQUE,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Vente (
                       id SERIAL PRIMARY KEY,
                       client_id INT REFERENCES Client(id) ON DELETE CASCADE,
                       date_vente TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       montant_total NUMERIC(12,2) NOT NULL,
                       statut_paiement_id INT REFERENCES StatutPaiement(id)
);

CREATE TABLE DetailVente (
                             id SERIAL PRIMARY KEY,
                             vente_id INT NOT NULL REFERENCES Vente(id) ON DELETE CASCADE,
                             produit_id INT NOT NULL REFERENCES Produit(id),
                             quantite NUMERIC(12,2) NOT NULL,
                             prix_unitaire NUMERIC(12,2) NOT NULL,
                             sous_total NUMERIC(12,2) NOT NULL
);

CREATE TABLE Facture (
                         id SERIAL PRIMARY KEY,
                         vente_id INT NOT NULL REFERENCES Vente(id) ON DELETE CASCADE,
                         numero_facture VARCHAR(50) UNIQUE NOT NULL,
                         pdf_path VARCHAR(255),
                         date_emission TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Devis (
                       id SERIAL PRIMARY KEY,
                       client_id INT NOT NULL REFERENCES Client(id) ON DELETE CASCADE,
                       date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       date_expiration TIMESTAMP,
                       montant_estime NUMERIC(12,2),
                       statut_id INT REFERENCES StatutDevis(id),
                       pdf_path VARCHAR(255)
);

CREATE TABLE Utilisateur (
                             id SERIAL PRIMARY KEY,
                             username VARCHAR(50) UNIQUE NOT NULL,
                             mot_de_passe VARCHAR(255) NOT NULL,
                             nom_complet VARCHAR(150),
                             role_id INT REFERENCES RoleUtilisateur(id),
                             actif BOOLEAN DEFAULT TRUE,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
