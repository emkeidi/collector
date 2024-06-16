import { app, BrowserWindow } from 'electron';
import * as path from 'path';
import * as url from 'url';

console.log('Current directory:', __dirname);
function createWindow() {
	const mainWindow = new BrowserWindow({
		width: 800,
		height: 600,
		webPreferences: {
			preload: path.join(__dirname, 'preload.js'),
			nodeIntegration: true,
		},
	});
	const startUrl = url.format({
		pathname: path.join(__dirname, 'public/index.html'),
		protocol: 'file:',
		slashes: true,
	});

	console.log('Loading URL:', startUrl); // Log the URL to verify the path
	mainWindow.loadURL(startUrl);

	mainWindow.webContents.openDevTools();
}

app.on('ready', createWindow);

app.on('window-all-closed', () => {
	if (process.platform !== 'darwin') {
		app.quit();
	}
});

app.on('activate', () => {
	if (BrowserWindow.getAllWindows().length === 0) {
		createWindow();
	}
});
