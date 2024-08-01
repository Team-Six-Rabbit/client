import { Opponents as StyledOpponents, VS } from "@/assets/styles/modalStyles";
import Opponent from "@/components/Modal/Opponent";
import VoteResultBar from "@/components/Modal/VoteResultBar";
import { Result, Opinion } from "@/types/Board/modalTypes";

interface UserProps {
	nickname: string;
	imgUrl: string;
	opinion: string;
}

interface OpponentsProps {
	registerUser: UserProps;
	oppositeUser: UserProps;
	speechBubbleColor: string;
	status: "upcoming" | "ended" | "balance";
	preResult?: Result;
	finalResult?: Result;
	opinions?: Opinion[];
}

function Opponents({
	registerUser,
	oppositeUser,
	speechBubbleColor,
	status,
	preResult,
	finalResult,
	opinions,
}: OpponentsProps) {
	return (
		<StyledOpponents>
			<Opponent
				nickname={registerUser.nickname}
				imgUrl={registerUser.imgUrl}
				opinion={opinions?.at(0)?.opinion || ""}
				speechBubbleColor={speechBubbleColor}
			/>
			{status === "upcoming" && <VS>VS</VS>}
			{status === "ended" && preResult && finalResult && (
				<div style={{ width: "100%" }}>
					<VoteResultBar
						title="사전 투표"
						percentageRegisterOpinion={preResult.percentageRegisterOpinion}
						percentageOppositeOpinion={preResult.percentageOppositeOpinion}
						registerColor="#FFC7C2"
						oppositeColor="#BDE3FF"
					/>
					<VoteResultBar
						title="최종 투표"
						percentageRegisterOpinion={finalResult.percentageRegisterOpinion}
						percentageOppositeOpinion={finalResult.percentageOppositeOpinion}
						registerColor="#FFC7C2"
						oppositeColor="#BDE3FF"
					/>
				</div>
			)}
			{status === "balance" && opinions && (
				<div style={{ width: "100%" }}>
					<VoteResultBar
						title="투표 결과"
						percentageRegisterOpinion={opinions[0].percentage}
						percentageOppositeOpinion={opinions[1].percentage}
						registerColor="#F66C23"
						oppositeColor="#0B68EC"
					/>
				</div>
			)}
			<Opponent
				nickname={oppositeUser.nickname}
				imgUrl={oppositeUser.imgUrl}
				opinion={opinions?.at(1)?.opinion || ""}
				speechBubbleColor={speechBubbleColor}
			/>
		</StyledOpponents>
	);
}

Opponents.defaultProps = {
	preResult: {
		percentageRegisterOpinion: 50,
		percentageOppositeOpinion: 50,
	},
	finalResult: {
		percentageRegisterOpinion: 50,
		percentageOppositeOpinion: 50,
	},
	opinions: [
		{ index: 0, opinion: "Option 1", count: 0, percentage: 50 },
		{ index: 1, opinion: "Option 2", count: 0, percentage: 50 },
	],
};

export default Opponents;
