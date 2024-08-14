import { useEffect } from "react";
import { OpinionWithPercentage } from "@/types/vote";

interface VoteGaugeProps {
	voteState: OpinionWithPercentage[];
}

function VoteGauge({ voteState }: VoteGaugeProps) {
	return (
		<div className="relative w-full h-10 flex items-center border-solid border-4 border-black rounded-lg overflow-hidden">
			<span className="absolute left-0 pl-2 text-black font-bold">
				{voteState[0].count}
			</span>
			<div className="w-full h-full flex">
				<div
					className="bg-orange h-full transition-all duration-500"
					style={{ width: `${voteState[0].percentage}%` }}
				/>
				<div
					className="bg-blue h-full transition-all duration-500"
					style={{ width: `${voteState[1].percentage}%` }}
				/>
			</div>
			<span className="absolute right-0 pr-2 text-black font-bold">
				{voteState[1].count}
			</span>
		</div>
	);
}

interface LiveVoteProps {
	userId: number;
	role: number;
	voteState: OpinionWithPercentage[];
	userVoteOpinion: number;
	title: string;
	optionA: string;
	optionB: string;
	handleVote: (userId: number, voteInfoIndex: number) => void;
	onVoteEnd: (winner: string) => void; // 투표가 끝났을 때 호출되는 함수
}

function LiveVote({
	userId,
	role,
	voteState,
	userVoteOpinion,
	title,
	optionA,
	optionB,
	handleVote,
	onVoteEnd,
}: LiveVoteProps) {
	useEffect(() => {
		const winner = voteState[0].count > voteState[1].count ? optionA : optionB;
		onVoteEnd(winner);
	}, [onVoteEnd, optionA, optionB, voteState]);

	return (
		<div className="flex flex-col items-center mb-2">
			<h1 className="text-2xl my-2">{title}</h1>
			<div className="flex flex-row items-center justify-between w-3/4">
				<button
					type="button"
					className={`bg-gray-800 me-3 mb-4 px-3 py-2 rounded-md text-white tracking-wider shadow-xl animate-bounce hover:animate-none ${userVoteOpinion === 0 ? "bg-olive text-white" : ""}`}
					onClick={() => handleVote(userId, 0)}
					disabled={role === 0 || role === 1}
				>
					{optionA}
				</button>
				<VoteGauge voteState={voteState} />
				<button
					type="button"
					className={`bg-gray-800 ms-3 mb-4 px-3 py-2 rounded-md text-white tracking-wider shadow-xl animate-bounce hover:animate-none ${userVoteOpinion === 1 ? "bg-olive text-white" : ""}`}
					onClick={() => handleVote(userId, 1)}
					disabled={role === 0 || role === 1}
				>
					{optionB}
				</button>
			</div>
		</div>
	);
}

export default LiveVote;
