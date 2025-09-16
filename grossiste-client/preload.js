const { contextBridge, ipcRenderer } = require('electron');

contextBridge.exposeInMainWorld('api', {
    // Enregistrer une vente
    enregistrerVente: (venteData) => ipcRenderer.invoke('enregistrer-vente', venteData),

    // Récupérer la liste des produits
    getProduits: () => ipcRenderer.invoke('get-produits'),

    // Récupérer la liste des clients
    getClients: () => ipcRenderer.invoke('get-clients'),

    getStock :() => ipcRenderer.invoke('get-stock'),
    // Nouvelle fonction pour récupérer les prédictions depuis l'API Python
    getStockPrediction: async () => {
        try {
            const response = await fetch('http://127.0.0.1:8000/predict_stock');
            const data = await response.json();
            console.log("DATA "+data);
            return { success: true, data };
        } catch (error) {
            console.error('Erreur API prédiction:', error);
            return { success: false, error: error.message };
        }
    }
});
