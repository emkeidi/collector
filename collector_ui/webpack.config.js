const path = require("path");
const CopyWebpackPlugin = require("copy-webpack-plugin");
const MiniCssExtractPlugin = require("mini-css-extract-plugin");

module.exports = [
	{
		entry: "./src/main/main.ts",
		target: "electron-main",
		module: {
			rules: [
				{
					test: /\.tsx?$/,
					include: /src/,
					use: "ts-loader",
				},
				{
					test: /\.module\.css$/,
					use: [
						process.env.NODE_ENV !== "production" ? "style-loader" : MiniCssExtractPlugin.loader,
						{
							loader: "css-loader",
							options: {
								modules: {
									localIdentName: "[name]__[local]___[hash:base64:5]",
								},
							},
						},
					],
				},
				{
					test: /\.css$/,
					exclude: /\.module\.css$/,
					use: [process.env.NODE_ENV !== "production" ? "style-loader" : MiniCssExtractPlugin.loader, "css-loader"],
				},
				{
					test: /\.png$/,
					use: [
						{
							loader: "file-loader",
							options: {
								name: "[path][name].[ext]",
							},
						},
					],
				},
			],
		},
		resolve: {
			extensions: [".ts", ".tsx", ".js", ".json", ".css", ".png"],
		},
		output: {
			path: path.resolve(__dirname, "dist"),
			filename: "main.js",
		},
	},
	{
		entry: "./src/main/preload.js",
		target: "electron-preload",
		module: {
			rules: [
				{
					test: /\.js$/,
					include: /src/,
					use: "babel-loader",
				},
				{
					test: /\.module\.css$/,
					use: [
						process.env.NODE_ENV !== "production" ? "style-loader" : MiniCssExtractPlugin.loader,
						{
							loader: "css-loader",
							options: {
								modules: true,
							},
						},
					],
				},
				{
					test: /\.css$/,
					exclude: /\.module\.css$/,
					use: [process.env.NODE_ENV !== "production" ? "style-loader" : MiniCssExtractPlugin.loader, "css-loader"],
				},
				{
					test: /\.png$/,
					use: [
						{
							loader: "file-loader",
							options: {
								name: "[path][name].[ext]",
							},
						},
					],
				},
			],
		},
		resolve: {
			extensions: [".js", ".css", ".png"],
		},
		output: {
			path: path.resolve(__dirname, "dist"),
			filename: "preload.js",
		},
	},
	{
		entry: "./src/renderer/index.tsx",
		target: "electron-renderer",
		module: {
			rules: [
				{
					test: /\.tsx?$/,
					include: /src/,
					use: "babel-loader",
				},
				{
					test: /\.module\.css$/,
					use: [
						process.env.NODE_ENV !== "production" ? "style-loader" : MiniCssExtractPlugin.loader,
						{
							loader: "css-loader",
							options: {
								modules: {
									localIdentName: "[name]__[local]___[hash:base64:5]",
								},
							},
						},
					],
				},
				{
					test: /\.css$/,
					exclude: /\.module\.css$/,
					use: [process.env.NODE_ENV !== "production" ? "style-loader" : MiniCssExtractPlugin.loader, "css-loader"],
				},
				{
					test: /\.png$/,
					use: [
						{
							loader: "file-loader",
							options: {
								name: "[path][name].[ext]",
							},
						},
					],
				},
			],
		},
		resolve: {
			extensions: [".ts", ".tsx", ".js", ".json", ".css", ".png"],
		},
		output: {
			path: path.resolve(__dirname, "dist"),
			filename: "renderer.js",
		},
		plugins: [
			new MiniCssExtractPlugin({
				filename: "[name].css",
				chunkFilename: "[id].css",
			}),
			new CopyWebpackPlugin({
				patterns: [{ from: "public", to: "public" }],
			}),
		],
	},
];
