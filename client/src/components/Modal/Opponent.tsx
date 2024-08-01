import {
	Opponent as StyledOpponent,
	OpponentImage,
	SpeechBubble,
	InfoTextSpan,
} from "@/assets/styles/modalStyles";
import styled from "styled-components";

interface OpponentProps {
	nickname: string;
	imgUrl: string;
	opinion: string;
	speechBubbleColor: string;
}

const CustomSpeechBubble = styled(SpeechBubble)<{ color: string }>`
	background: ${({ color }) => color};

	&:after {
		border-top-color: ${({ color }) => color};
	}
`;

function Opponent({
	nickname,
	imgUrl,
	opinion,
	speechBubbleColor,
}: OpponentProps) {
	return (
		<StyledOpponent>
			<CustomSpeechBubble color={speechBubbleColor}>
				{opinion}
			</CustomSpeechBubble>
			<OpponentImage src={imgUrl} alt={nickname} />
			<InfoTextSpan>{nickname}</InfoTextSpan>
		</StyledOpponent>
	);
}

export default Opponent;
