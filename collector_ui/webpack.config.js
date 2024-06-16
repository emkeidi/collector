const path = require('path');
const CopyWebpackPlugin = require('copy-webpack-plugin');

module.exports = [
	{
		entry: './src/main/main.ts',
		target: 'electron-main',
		module: {
			rules: [
				{
					test: /\.tsx?$/,
					include: /src/,
					use: 'babel-loader', // Use ts-loader for TypeScript in main process
				},
			],
		},
		resolve: {
			extensions: ['.ts', '.tsx', '.js', '.json'],
		},
		output: {
			path: path.resolve(__dirname, 'dist'),
			filename: 'main.js',
		},
	},
	{
		entry: './src/main/preload.js',
		target: 'electron-preload',
		module: {
			rules: [
				{
					test: /\.js$/,
					include: /src/,
					use: 'babel-loader',
				},
			],
		},
		resolve: {
			extensions: ['.js'],
		},
		output: {
			path: path.resolve(__dirname, 'dist'),
			filename: 'preload.js',
		},
	},
	{
		entry: './src/renderer/index.tsx',
		target: 'electron-renderer',
		module: {
			rules: [
				{
					test: /\.tsx?$/,
					include: /src/,
					use: 'babel-loader', // Use babel-loader for TypeScript and JSX in renderer process
				},
			],
		},
		resolve: {
			extensions: ['.ts', '.tsx', '.js', '.json'],
		},
		output: {
			path: path.resolve(__dirname, 'dist'),
			filename: 'renderer.js',
		},
		plugins: [
			new CopyWebpackPlugin({
				patterns: [
					{ from: 'public', to: 'public' }, // Copy everything from public to dist/public
				],
			}),
		],
	},
];
