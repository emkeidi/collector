import { app, BrowserWindow } from "electron";
import * as path from "path";
import * as url from "url";

console.log("Current directory:", __dirname);
function createWindow() {
	const mainWindow = new BrowserWindow({
		width: 1200,
		height: 800,
		webPreferences: {
			preload: path.join(__dirname, "preload.js"),
			nodeIntegration: true,
		},
		icon: path.join(__dirname, "./public/icons/collector_no_background_mock.png"),
	});
	const startUrl = url.format({
		pathname: path.join(__dirname, "./public/index.html"),
		protocol: "file:",
		slashes: true,
	});

	mainWindow.loadURL(startUrl);
}

app.on("ready", createWindow);

app.on("window-all-closed", () => {
	if (process.platform !== "darwin") {
		app.quit();
	}
});

app.on("activate", () => {
	if (BrowserWindow.getAllWindows().length === 0) {
		createWindow();
	}
});
