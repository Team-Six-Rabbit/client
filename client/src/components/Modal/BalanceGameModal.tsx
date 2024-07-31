import React from "react";
import person_orange from "@/assets/images/person_orange.png";
import person_blue from "@/assets/images/person_blue.png";
import {
	ModalBackdrop,
	ModalContent,
	ModalHeader,
	ModalTitle,
	CloseButton,
	ModalBody,
	InfoText,
	InfoTextSpan,
	Opponents,
	Opponent,
	OpponentImage,
	SpeechBubble,
	VoteResult,
	VoteColumn,
	ProgressBarContainer,
	ProgressBar,
	VoteRow,
	VoteText,
	VoteResultsContainer,
} from "@/components/Modal/ModalStyles";
import styled from "styled-components";
import { BalanceGameCardType } from "@/types/Board/balancegameCard";

interface BalanceGameModalProps {
	data: BalanceGameCardType;
	onClose: () => void;
}

const CustomModalContent = styled(ModalContent)`
	border: 8px solid #fbca27;
`;

const CustomInfoText = styled(InfoText)`
	border: 3px solid #fbca27;
`;

const CustomSpeechBubble = styled(SpeechBubble)`
	background: #fbca27;

	&:after {
		border-top-color: #fbca27;
	}
`;

function BalanceGameModal({ data, onClose }: BalanceGameModalProps) {
	const handleBackdropClick = (event: React.MouseEvent<HTMLDivElement>) => {
		if (event.target === event.currentTarget) {
			onClose();
		}
	};

	return (
		<ModalBackdrop onClick={handleBackdropClick}>
			<CustomModalContent>
				<ModalHeader>
					<ModalTitle>밸런스게임 상세보기</ModalTitle>
					<CloseButton type="button" onClick={onClose}>
						X
					</CloseButton>
				</ModalHeader>
				<ModalBody>
					<CustomInfoText>
						<InfoTextSpan>{data.title}</InfoTextSpan>
					</CustomInfoText>
					<Opponents>
						<Opponent>
							<CustomSpeechBubble>
								{data.opinions[0].opinion}
							</CustomSpeechBubble>
							<OpponentImage src={person_orange} alt="User A" />
						</Opponent>
						<VoteResultsContainer>
							<VoteResult>
								<VoteColumn>
									<VoteText>투표 현황</VoteText>
									<ProgressBarContainer>
										<ProgressBar
											percentage={data.opinions[0].percentage}
											color="#F66C23"
										/>
										<ProgressBar
											percentage={data.opinions[1].percentage}
											color="#0B68EC"
											style={{ left: `${data.opinions[0].percentage}%` }}
										/>
									</ProgressBarContainer>
									<VoteRow>
										<VoteText>{data.opinions[0].percentage}%</VoteText>
										<VoteText>{data.opinions[1].percentage}%</VoteText>
									</VoteRow>
								</VoteColumn>
							</VoteResult>
						</VoteResultsContainer>
						<Opponent>
							<CustomSpeechBubble>
								{data.opinions[1].opinion}
							</CustomSpeechBubble>
							<OpponentImage src={person_blue} alt="User B" />
						</Opponent>
					</Opponents>
					<CustomInfoText>
						<InfoTextSpan>{data.detail}</InfoTextSpan>
					</CustomInfoText>
				</ModalBody>
			</CustomModalContent>
		</ModalBackdrop>
	);
}

export default BalanceGameModal;
