import { useState } from "react";

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
		<div className="w-full h-10 flex border-solid border-4 border-black rounded-lg">
			<div
				className="bg-orange h-full"
				style={{ width: `${rateAPercentage}%` }}
			/>
			<div
				className="bg-blue h-full"
				style={{ width: `${rateBPercentage}%` }}
			/>
		</div>
	);
}

function VotePage({ title, optionA, optionB }: LiveVoteProps) {
	const [votesA, setVotesA] = useState(40);
	const [votesB, setVotesB] = useState(60);

	return (
		<div className="flex flex-col items-center">
			<h1 className="text-2xl my-2">{title}</h1>
			<div className="flex flex-row items-center justify-between w-3/4">
				<button
					type="button"
					className="bg-gray-800 me-3 mb-4 px-3 py-2 rounded-md text-white tracking-wider shadow-xl animate-bounce hover:animate-none"
					onClick={() => setVotesA(votesA + 1)}
				>
					{optionA}
				</button>
				<VoteGauge rateA={votesA} rateB={votesB} />
				<button
					type="button"
					className="bg-gray-800 ms-3 mb-4 px-3 py-2 rounded-md text-white tracking-wider shadow-xl animate-bounce hover:animate-none"
					onClick={() => setVotesB(votesB + 1)}
				>
					{optionB}
				</button>
			</div>
		</div>
	);
}

export default VotePage;
