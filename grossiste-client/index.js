const { app, BrowserWindow, ipcMain } = require('electron');
const path = require('path');
const axios = require('axios');

const { autoUpdater } = require('electron-updater');

app.on('ready', () => {
    createWindow();

    autoUpdater.checkForUpdatesAndNotify();
});

autoUpdater.on('update-available', () => {
    console.log('Nouvelle version disponible...');
});

autoUpdater.on('update-downloaded', () => {
    console.log('Mise à jour téléchargée, redémarrage pour installer...');
    autoUpdater.quitAndInstall();
});


function createWindow() {
    const win = new BrowserWindow({
        width: 800,
        height: 600,
        webPreferences: {
            preload: path.join(__dirname, 'preload.js')
        }
    });

    win.loadFile('index.html');
    // Ouvrir la console pour debug
  //  win.webContents.openDevTools();
}

app.whenReady().then(() => {
    createWindow();

    app.on('activate', function () {
        if (BrowserWindow.getAllWindows().length === 0) createWindow();
    });
});

app.on('window-all-closed', function () {
    if (process.platform !== 'darwin') app.quit();
});


ipcMain.handle('enregistrer-vente', async (event, venteData) => {
    try {
        const response = await axios.post('http://localhost:8080/api/ventes', venteData);
        return { success: true, data: response.data };
    } catch (error) {
        console.error(error);
        return { success: false, error: error.message };
    }
});


ipcMain.handle('get-produits', async () => {
    try {
        const response = await axios.get('http://localhost:8080/api/produits');
        return { success: true, data: response.data };
    } catch (error) {
        console.error(error);
        return { success: false, error: error.message };
    }
});

ipcMain.handle('get-stock', async () => {
    try {
        const response = await axios.get('http://localhost:8080/api/stock');
        return { success: true, data: response.data };
    } catch (error) {
        console.error(error);
        return { success: false, error: error.message };
    }
});

// ✅ Charger les clients
ipcMain.handle('get-clients', async () => {
    try {
        const response = await axios.get('http://localhost:8080/api/clients');
        return { success: true, data: response.data };
    } catch (error) {
        console.error(error);
        return { success: false, error: error.message };
    }
});
