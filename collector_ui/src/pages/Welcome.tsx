import React from "react";
import * as styles from "./Welcome.module.css";

const Welcome: React.FC = () => {
	console.log(styles);
	return (
		<div className={styles.welcomeDiv}>
			<h1>Collector</h1>
			<p>Welcome to Collector! This is a simple application that allows you to collect data from users.</p>
			<p>Click on the "New Form" button to create a new form, or click on the "View Forms" button to view existing forms.</p>
		</div>
	);
};

export default Welcome;
