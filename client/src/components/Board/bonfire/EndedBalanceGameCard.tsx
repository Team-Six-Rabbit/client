import {
	OptionWrapper,
	OptionText,
	PercentageLabel,
} from "@/assets/styles/balanceGameStyle";

interface Opinion {
	index: number;
	opinion: string;
	percentage: number;
}

interface EndedBalanceGameCardProps {
	opinions: Opinion[];
	userVote?: number | null;
}

function EndedBalanceGameCard({
	opinions,
	userVote = null,
}: EndedBalanceGameCardProps) {
	return (
		<>
			{opinions.map((opinion) => {
				// Log userVote and current opinion index for debugging
				console.log(
					`User vote: ${userVote}, Current opinion index: ${opinion.index}`,
				);

				return (
					<OptionWrapper key={opinion.index} disabled>
						<PercentageLabel
							color={opinion.index === 0 ? "#F66C23" : "#0B68EC"}
						>
							{opinion.percentage}%
						</PercentageLabel>
						<OptionText
							borderColor={opinion.index === 0 ? "#F66C23" : "#0B68EC"}
							bgColor={opinion.index === 0 ? "#F66C23" : "#0B68EC"}
							width={opinion.percentage}
							style={{
								boxShadow:
									userVote === opinion.index ? "0 0 10px #FFD700" : "none",
							}}
						>
							{opinion.opinion}
						</OptionText>
					</OptionWrapper>
				);
			})}
		</>
	);
}

EndedBalanceGameCard.defaultProps = {
	userVote: null,
};

export default EndedBalanceGameCard;
