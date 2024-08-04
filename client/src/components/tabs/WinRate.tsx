import React from "react";
import { CircularProgressBar } from "@tomickigrzegorz/react-circular-progress-bar";

function WinRate() {
	const styles = {
		winRate: {
			textAlign: "center" as const,
			marginTop: "20px",
			display: "flex",
			justifyContent: "center",
			alignItems: "center",
		},
		winRateCircle: {
			display: "flex",
			justifyContent: "center",
			alignItems: "center",
			margin: "20px 20px 20px 0",
		},
		winRateDetails: {
			listStyle: "none" as const,
			padding: 0,
			fontSize: "18px",
			textAlign: "left" as const,
			marginLeft: "20px",
		},
		winRateDetailsItem: {
			margin: "5px 0",
		},
	};

	return (
		<div style={styles.winRate}>
			<div style={styles.winRateCircle}>
				<CircularProgressBar
					colorCircle="#ededed"
					colorSlice="#1D3D6B"
					percent={79}
					fontColor="#1D3D6B"
					round
					fontSize="20px"
					textPosition="0.35em" // Adjusted this value for better centering
				/>
			</div>
			<ul style={styles.winRateDetails}>
				<li style={styles.winRateDetailsItem}>참여한 토론의 수 : 100</li>
				<li style={styles.winRateDetailsItem}>승리한 토론의 수 : 79</li>
				<li style={styles.winRateDetailsItem}>패배한 토론의 수 : 21</li>
			</ul>
		</div>
	);
}

export default WinRate;
