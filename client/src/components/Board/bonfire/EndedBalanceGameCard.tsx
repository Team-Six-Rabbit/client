import {
	OptionWrapper,
	OptionText,
	PercentageLabel,
} from "@/assets/styles/balanceGameStyle";

interface EndedBalanceGameCardProps {
	opinions: Array<{ index: number; opinion: string; percentage: number }>;
}

function EndedBalanceGameCard({ opinions }: EndedBalanceGameCardProps) {
	return (
		<>
			{opinions.map((opinion, index) => (
				<OptionWrapper key={opinion.index} disabled>
					<PercentageLabel color={index === 0 ? "#F66C23" : "#0B68EC"}>
						{opinion.percentage}%
					</PercentageLabel>
					<OptionText
						borderColor={index === 0 ? "#F66C23" : "#0B68EC"}
						bgColor={index === 0 ? "#F66C23" : "#0B68EC"}
						width={opinion.percentage}
					>
						{opinion.opinion}
					</OptionText>
				</OptionWrapper>
			))}
		</>
	);
}

export default EndedBalanceGameCard;
