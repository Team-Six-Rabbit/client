import { useEffect } from "react";

interface VoteGaugeProps {
	rateAPercentage: number;
	rateBPercentage: number;
}

function VoteGauge({ rateAPercentage, rateBPercentage }: VoteGaugeProps) {
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

interface LiveVoteProps {
	userId: number;
	role: number;
	voteA: number;
	voteB: number;
	title: string;
	optionA: string;
	optionB: string;
	sendVote: (userId: number, voteInfoIndex: number) => void;
	onVoteEnd: (winner: string) => void; // 투표가 끝났을 때 호출되는 함수
}

function LiveVote({
	userId,
	role,
	voteA,
	voteB,
	title,
	optionA,
	optionB,
	sendVote,
	onVoteEnd,
}: LiveVoteProps) {
	useEffect(() => {
		const winner = voteA > voteB ? optionA : optionB;
		onVoteEnd(winner);
	}, [onVoteEnd, optionA, optionB, voteA, voteB]);

	return (
		<div className="flex flex-col items-center mb-2">
			<h1 className="text-2xl my-2">{title}</h1>
			<div className="flex flex-row items-center justify-between w-3/4">
				<button
					type="button"
					className="bg-gray-800 me-3 mb-4 px-3 py-2 rounded-md text-white tracking-wider shadow-xl animate-bounce hover:animate-none"
					onClick={() => sendVote(userId, 0)}
					disabled={role === 0 || role === 1}
				>
					{optionA}
				</button>
				<VoteGauge rateAPercentage={voteA} rateBPercentage={voteB} />
				<button
					type="button"
					className="bg-gray-800 ms-3 mb-4 px-3 py-2 rounded-md text-white tracking-wider shadow-xl animate-bounce hover:animate-none"
					onClick={() => sendVote(userId, 1)}
					disabled={role === 0 || role === 1}
				>
					{optionB}
				</button>
			</div>
		</div>
	);
}

export default LiveVote;
