import { useEffect, useState } from "react";
import ConfettiComponent from "@/utils/Canvas";

interface EndedLiveProps {
	winner: string;
}

function EndedLive({ winner }: EndedLiveProps) {
	const [showWinner, setShowWinner] = useState(false);

	useEffect(() => {
		const timer = setTimeout(() => {
			setShowWinner(true);
		}, 100);

		return () => clearTimeout(timer);
	}, []);

	return (
		<div className="absolute inset-0 flex items-center justify-center z-20 bg-gray-900 bg-opacity-50">
			{showWinner && (
				<ConfettiComponent
					particleCount={200}
					spread={150}
					origin={{ x: 0.5, y: 0.7 }}
				/>
			)}
			<div className="text-6xl font-bold text-white animate-bounce">
				{winner} Wins!
			</div>
		</div>
	);
}

export default EndedLive;
