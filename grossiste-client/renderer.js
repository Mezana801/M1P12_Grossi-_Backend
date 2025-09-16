// Quand on clique sur le bouton "Enregistrer la vente"
document.getElementById('submitBtn').addEventListener('click', async () => {
    const clientId = parseInt(document.getElementById('clientId').value);
    const produitId = parseInt(document.getElementById('produitId').value);
    const quantite = parseInt(document.getElementById('quantite').value);
    const prixUnitaire = parseInt(document.getElementById('prixUnitaire').value);

    const venteData = {
        clientId: clientId,
        details: [
            {
                produitId: produitId,
                quantite: quantite,
                prixUnitaire: prixUnitaire
            }
        ]
    };

    // Appel à l'API via preload
    const result = await window.api.enregistrerVente(venteData);
    //document.getElementById('result').innerText = JSON.stringify(result, null, 2);

    if(result.success){
        Swal.fire({
            icon: 'success',
            title: 'Vente enregistrée !',
            text: 'Les données ont été enregistrées avec succès.',
            timer: 3000,
            timerProgressBar: true,
            showConfirmButton: false
        });

        // Réinitialiser les champs après 3 secondes
        setTimeout(() => {
            //document.getElementById('clientId').selectedIndex = 0;
            //document.getElementById('produitId').selectedIndex = 0;
            document.getElementById('quantite').value = 1;
            //document.getElementById('prixUnitaire').value = 0;
            chargerStocks();
        }, 3000);
    } else {
        Swal.fire({
            icon: 'error',
            title: 'Erreur',
            text: result.error || 'Impossible d\'enregistrer la vente.'
        });
    }
});

// Charger les clients dans le <select>
async function chargerClients() {
    const result = await window.api.getClients();
    if (result.success) {
        const selectClient = document.getElementById('clientId');
        selectClient.innerHTML = ""; // reset

        result.data.forEach(client => {
            const option = document.createElement('option');
            option.value = client.id;   // ⚠️ Vérifie si c'est bien `id` ou `clientId` dans ta BDD
            option.textContent = client.nom; // ⚠️ Adapte selon ton entité Client
            selectClient.appendChild(option);
        });
    } else {
        console.error("Erreur chargement clients:", result.error);
    }
}

let produitsData = [];
// Charger les produits dans le <select>
async function chargerProduits() {
    const result = await window.api.getProduits();
    if (result.success) {
        const selectProduit = document.getElementById('produitId');
        selectProduit.innerHTML = ""; // reset
        produitsData = result.data; // sauvegarder les produits
        result.data.forEach(produit => {
            const option = document.createElement('option');
            option.value = produit.id;   // ⚠️ Vérifie si c'est bien `id` ou `produitId`
            option.textContent = produit.nom + " - " + produit.prixActuel + " Ar";
            selectProduit.appendChild(option);
        });
        // Initialiser le prixUnitaire avec le premier produit
        if (produitsData.length > 0) {
            document.getElementById('prixUnitaire').value = produitsData[0].prixActuel;
            document.getElementById('prixUnitaire').readOnly = true;
        }

    } else {
        console.error("Erreur chargement produits:", result.error);
    }
}

// Ajouter le onchange pour mettre à jour le prixUnitaire
document.getElementById('produitId').addEventListener('change', (event) => {
    const produitId = parseInt(event.target.value);
    const produit = produitsData.find(p => p.id === produitId);
    if (produit) {
        const prixInput = document.getElementById('prixUnitaire');
        prixInput.value = produit.prixActuel;
        prixInput.readOnly = true;
    }
});

// Charger stocks
async function chargerStocks() {
    const result = await window.api.getStock();
    if (result.success) {
        afficherStocks(result.data);
    }
}

function afficherStocks() {
    const tbody = document.querySelector('#stocksTable tbody');
    tbody.innerHTML = "";

    window.api.getStock().then(stockResp => {
        if (!stockResp.success) {
            console.error('Erreur chargement des stocks');
            return;
        }

        window.api.getStockPrediction().then(predictionResp => {
            if (!predictionResp.success) {
                console.error('Erreur chargement des prédictions');
                return;
            }

            const stocks = stockResp.data;
            const predictions = predictionResp.data;

            stocks.forEach(stock => {
                const pred = predictions.find(p => p.produit_id === stock.stockId);
                const stockPrevJ1 = pred ? pred.stock_prev.toFixed(2) : "-";

                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${stock.produitNom}</td>
                    <td>${stock.stockDisponible}</td>
                    <td>${stock.prixUnitaire}</td>
                    <td>${pred ? Math.round(pred.stock_prev) : 'N/A'}</td>
                `;
                tbody.appendChild(tr);
            });
        }).catch(err => console.error("Erreur API prédiction:", err));
    }).catch(err => console.error("Erreur API stocks:", err));
}




// utilitaire : normaliser (retire accents + passe en minuscule)
function normalizeText(s) {
    if (!s) return '';
    return s.normalize('NFD').replace(/[\u0300-\u036f]/g, '').toLowerCase().trim();
}

let searchTimeout = null;
document.getElementById('searchStock').addEventListener('input', function () {
    clearTimeout(searchTimeout);
    const query = this.value;
    // debounce pour éviter filtrage trop rapide à chaque frappe
    searchTimeout = setTimeout(() => filterStocks(query), 200);
});

function filterStocks(rawQuery) {
    const query = normalizeText(rawQuery || '');
    const rows = document.querySelectorAll('#stocksTable tbody tr');

    if (!query) {
        rows.forEach(r => r.style.display = '');
        return;
    }

    const tokens = query.split(/\s+/).filter(t => t.length > 0);

    rows.forEach(row => {
        const nameCell = row.querySelector('td:nth-child(1)');
        const name = normalizeText(nameCell ? nameCell.textContent : '');
        const nameWords = name.split(/\s+/).filter(w => w);

        const matches = tokens.every(token => {
            // préfixe sur le nom complet ou un mot du nom
            return name.startsWith(token) || nameWords.some(w => w.startsWith(token));
        });

        row.style.display = matches ? '' : 'none';
    });
}


// Charger au démarrage
window.addEventListener('DOMContentLoaded', async () => {
    await chargerClients();
    await chargerProduits();
    await chargerStocks();
});
