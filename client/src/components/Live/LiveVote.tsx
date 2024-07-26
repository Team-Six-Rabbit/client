import { useEffect, useState } from "react";

interface VoteGaugeProps {
	rateA: number;
	rateB: number;
}

interface LiveVoteProps {
	title: string;
	optionA: string;
	optionB: string;
}

function VoteGauge({ rateA, rateB }: VoteGaugeProps) {
	const totalVotes = rateA + rateB;
	const rateAPercentage = totalVotes > 0 ? (rateA / totalVotes) * 100 : 50;
	const rateBPercentage = totalVotes > 0 ? (rateB / totalVotes) * 100 : 50;

	return (
		<div className="w-full h-10 flex border-solid border-4 border-black rounded-lg overflow-hidden">
			<div
				className="bg-orange h-full transition-all duration-500"
				style={{ width: `${rateAPercentage}%` }}
			/>
			<div
				className="bg-blue h-full transition-all duration-500"
				style={{ width: `${rateBPercentage}%` }}
			/>
		</div>
	);
}

function VotePage({ title, optionA, optionB }: LiveVoteProps) {
	const [votesA, setVotesA] = useState(70);
	const [votesB, setVotesB] = useState(30);
	const [pendingVotesA, setPendingVotesA] = useState(70);
	const [pendingVotesB, setPendingVotesB] = useState(30);

	useEffect(() => {
		const timer = setTimeout(() => {
			setVotesA(pendingVotesA);
			setVotesB(pendingVotesB);
		}, 2000);

		return () => clearTimeout(timer);
	}, [pendingVotesA, pendingVotesB]);

	const handleVoteA = () => {
		setPendingVotesA(pendingVotesA + 1);
	};

	const handleVoteB = () => {
		setPendingVotesB(pendingVotesB + 1);
	};

	return (
		<div className="flex flex-col items-center">
			<h1 className="text-2xl my-2">{title}</h1>
			<div className="flex flex-row items-center justify-between w-3/4">
				<button
					type="button"
					className="bg-gray-800 me-3 mb-4 px-3 py-2 rounded-md text-white tracking-wider shadow-xl animate-bounce hover:animate-ping hover:animate-none"
					onClick={handleVoteA}
				>
					{optionA}
				</button>
				<VoteGauge rateA={votesA} rateB={votesB} />
				<button
					type="button"
					className="bg-gray-800 ms-3 mb-4 px-3 py-2 rounded-md text-white tracking-wider shadow-xl animate-bounce hover:animate-none"
					onClick={handleVoteB}
				>
					{optionB}
				</button>
			</div>
		</div>
	);
}

export default VotePage;
