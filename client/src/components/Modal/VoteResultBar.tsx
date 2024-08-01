import {
	VoteColumn as StyledVoteColumn,
	ProgressBarContainer,
	ProgressBar,
	VoteRow,
	VoteText,
} from "@/assets/styles/modalStyles";

interface VoteResultBarProps {
	title: string;
	percentageRegisterOpinion: number;
	percentageOppositeOpinion: number;
	registerColor: string;
	oppositeColor: string;
}

function VoteResultBar({
	title,
	percentageRegisterOpinion,
	percentageOppositeOpinion,
	registerColor,
	oppositeColor,
}: VoteResultBarProps) {
	return (
		<StyledVoteColumn style={{ width: "100%" }}>
			<VoteText>{title}</VoteText>
			<ProgressBarContainer>
				<ProgressBar
					percentage={percentageRegisterOpinion}
					color={registerColor}
				/>
				<ProgressBar
					percentage={percentageOppositeOpinion}
					color={oppositeColor}
					style={{ left: `${percentageRegisterOpinion}%` }}
				/>
			</ProgressBarContainer>
			<VoteRow>
				<VoteText>{percentageRegisterOpinion}%</VoteText>
				<VoteText>{percentageOppositeOpinion}%</VoteText>
			</VoteRow>
		</StyledVoteColumn>
	);
}

export default VoteResultBar;
