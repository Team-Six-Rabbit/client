import { Opponents as StyledOpponents, VS } from "@/assets/styles/modalStyles";
import Opponent from "@/components/Modal/Opponent";
import VoteResultBar from "@/components/Modal/VoteResultBar";
import { Result } from "@/types/Board/modalTypes";

interface UserProps {
	nickname: string;
	imgUrl: string;
	opinion: string;
}

interface OpponentsProps {
	registerUser: UserProps;
	oppositeUser: UserProps;
	speechBubbleColor: string;
	status: "upcoming" | "ended";
	preResult?: Result;
	finalResult?: Result;
}

function Opponents({
	registerUser,
	oppositeUser,
	speechBubbleColor,
	status,
	preResult,
	finalResult,
}: OpponentsProps) {
	return (
		<StyledOpponents>
			<Opponent
				nickname={registerUser.nickname}
				imgUrl={registerUser.imgUrl}
				opinion={registerUser.opinion}
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
			<Opponent
				nickname={oppositeUser.nickname}
				imgUrl={oppositeUser.imgUrl}
				opinion={oppositeUser.opinion}
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
};

export default Opponents;
