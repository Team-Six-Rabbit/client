import React from "react";
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

interface EndedLivePreviewModalProps {
	title: string;
	regist_user_id: string;
	opposite_user_id: string;
	onClose: () => void;
}

const CustomModalContent = styled(ModalContent)`
	border: 8px solid #8bc34a;
`;

const CustomInfoText = styled(InfoText)`
	border: 3px solid #8bc34a;
`;

const CustomSpeechBubble = styled(SpeechBubble)`
	background: #8bc34a;

	&:after {
		border-top-color: #8bc34a;
	}
`;

function EndedLivePreviewModal({
	title,
	regist_user_id,
	opposite_user_id,
	onClose,
}: EndedLivePreviewModalProps) {
	const handleBackdropClick = (event: React.MouseEvent<HTMLDivElement>) => {
		if (event.target === event.currentTarget) {
			onClose();
		}
	};

	return (
		<ModalBackdrop onClick={handleBackdropClick}>
			<CustomModalContent>
				<ModalHeader>
					<ModalTitle>종료된 라이브 상세보기</ModalTitle>
					<CloseButton type="button" onClick={onClose}>
						X
					</CloseButton>
				</ModalHeader>
				<ModalBody>
					<CustomInfoText>
						<InfoTextSpan>{title}</InfoTextSpan>
					</CustomInfoText>
					<Opponents>
						<Opponent>
							<CustomSpeechBubble>
								마라탕탕후루후루 마라탕탕후루후루
							</CustomSpeechBubble>
							<OpponentImage src="https://picsum.photos/400/400" alt="User A" />
							<InfoTextSpan>{regist_user_id}</InfoTextSpan>
						</Opponent>
						<VoteResultsContainer>
							<VoteResult>
								<VoteColumn>
									<VoteText>사전 투표</VoteText>
									<ProgressBarContainer>
										<ProgressBar percentage={76} color="#FFC7C2" />
										<ProgressBar
											percentage={24}
											color="#BDE3FF"
											style={{ left: "76%" }}
										/>
									</ProgressBarContainer>
									<VoteRow>
										<VoteText>76%</VoteText>
										<VoteText>24%</VoteText>
									</VoteRow>
								</VoteColumn>
								<VoteColumn>
									<VoteText>최종 투표</VoteText>
									<ProgressBarContainer>
										<ProgressBar percentage={24} color="#FFC7C2" />
										<ProgressBar
											percentage={76}
											color="#BDE3FF"
											style={{ left: "24%" }}
										/>
									</ProgressBarContainer>
									<VoteRow>
										<VoteText>24%</VoteText>
										<VoteText>76%</VoteText>
									</VoteRow>
								</VoteColumn>
							</VoteResult>
						</VoteResultsContainer>
						<Opponent>
							<CustomSpeechBubble>
								마라탕탕후루후루 마라탕탕후루후루
							</CustomSpeechBubble>
							<OpponentImage src="https://picsum.photos/400/400" alt="User B" />
							<InfoTextSpan>{opposite_user_id}</InfoTextSpan>
						</Opponent>
					</Opponents>
					<CustomInfoText>
						<InfoTextSpan>
							생성된 AI로 재현된 라이브 토론 내용의 요약이 들어갑니다.
						</InfoTextSpan>
					</CustomInfoText>
				</ModalBody>
			</CustomModalContent>
		</ModalBackdrop>
	);
}

export default EndedLivePreviewModal;
