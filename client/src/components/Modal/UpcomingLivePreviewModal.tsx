import React from "react";
import styled from "styled-components";
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
	VS,
} from "@/components/Modal/ModalStyles";

interface UpcomingLivePreviewModalProps {
	title: string;
	regist_user_id: string;
	opposite_user_id: string;
	onClose: () => void;
}

const CustomModalContent = styled(ModalContent)`
	border: 8px solid #0b68ec;
`;

const CustomInfoText = styled(InfoText)`
	border: 3px solid #0b68ec;
`;

const CustomSpeechBubble = styled(SpeechBubble)`
	background: #0b68ec;

	&:after {
		border-top-color: #0b68ec;
	}
`;

function UpcomingLivePreviewModal({
	title,
	regist_user_id,
	opposite_user_id,
	onClose,
}: UpcomingLivePreviewModalProps) {
	const handleBackdropClick = (event: React.MouseEvent<HTMLDivElement>) => {
		if (event.target === event.currentTarget) {
			onClose();
		}
	};

	return (
		<ModalBackdrop onClick={handleBackdropClick}>
			<CustomModalContent>
				<ModalHeader>
					<ModalTitle>예정된 라이브 미리보기</ModalTitle>
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
						<VS>VS</VS>
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
							예정된 라이브 토론의 상세 정보가 들어갑니다.
						</InfoTextSpan>
					</CustomInfoText>
				</ModalBody>
			</CustomModalContent>
		</ModalBackdrop>
	);
}

export default UpcomingLivePreviewModal;
