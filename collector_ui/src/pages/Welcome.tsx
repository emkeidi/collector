import React from "react";
import * as styles from "./Welcome.module.css";
import logo from "../../public/icons/collector_no_background_mock.png";

const Welcome: React.FC = () => {
	console.log(styles);
	return (
		<div className={styles.welcomeDiv}>
			<img className={styles.logo} src={logo} alt='Collector Logo' />
			<h1>Collector</h1>
		</div>
	);
};

export default Welcome;
