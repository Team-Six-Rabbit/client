import styled from "styled-components";

interface UpcomingLivePreviewModalProps {
	title: string;
	regist_user_id: string;
	opposite_user_id: string;
	onClose: () => void;
}

const ModalBackdrop = styled.div`
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	display: flex;
	align-items: center;
	justify-content: center;
	background-color: rgba(0, 0, 0, 0.5);
	z-index: 50;
`;

const ModalContent = styled.div`
	background-color: white;
	padding: 16px;
	border-radius: 20px;
	box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
	max-width: 640px;
	width: 100%;
	border: 8px solid #000000;
`;

const ModalHeader = styled.div`
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding-bottom: 8px;
	padding-left: 15px;
	padding-top: 15px;
	position: relative;
`;

const ModalTitle = styled.h2`
	font-size: 1.5rem;
`;

const CloseButton = styled.button`
	font-size: 1.5rem;
	background: none;
	border: none;
	cursor: pointer;
	position: absolute;
	top: 0;
	right: 0;
`;

const ModalBody = styled.div`
	display: flex;
	flex-direction: column;
	gap: 16px;
	padding: 8px;
`;

const InfoText = styled.p`
	font-size: 1rem;
	font-weight: 600;
	color: #4b5563;
	border: 3px solid #000000;
	border-radius: 15px;
	padding: 10px;
`;

const InfoTextSpan = styled.span`
	font-weight: 400;
	color: #6b7280;
`;

const Opponents = styled.div`
	display: flex;
	justify-content: space-around;
	align-items: center;
	margin-top: 16px;
`;

const Opponent = styled.div`
	display: flex;
	flex-direction: column;
	align-items: center;
	width: 45%; /* 너비 조정 */

	img {
		width: 100px;
		height: 100px;
		border-radius: 50%;
		margin-bottom: 8px;
	}
`;

const OpinionText = styled.p`
	font-size: 0.9rem;
	font-weight: 400;
	color: #4b5563;
	text-align: center;
	margin-top: 8px;
	border: 3px solid #000000;
	border-radius: 10px;
	padding: 8px;
	background-color: #f9f9f9;
`;

const VS = styled.div`
	font-size: 2rem;
	font-weight: bold;
`;

function UpcomingLivePreviewModal({
	title,
	regist_user_id,
	opposite_user_id,
	onClose,
}: UpcomingLivePreviewModalProps) {
	return (
		<ModalBackdrop>
			<ModalContent>
				<ModalHeader>
					<ModalTitle>예정된 라이브 미리보기</ModalTitle>
					<CloseButton type="button" onClick={onClose}>
						X
					</CloseButton>
				</ModalHeader>
				<ModalBody>
					<InfoText>
						<InfoTextSpan>{title}</InfoTextSpan>
					</InfoText>
					<Opponents>
						<Opponent>
							<InfoTextSpan>{regist_user_id}</InfoTextSpan>
							<img src="https://picsum.photos/400/400" alt="User A" />
							<OpinionText>작성자 주장을 넣어주는 칸</OpinionText>
						</Opponent>
						<VS>VS</VS>
						<Opponent>
							<InfoTextSpan>{opposite_user_id}</InfoTextSpan>
							<img src="https://picsum.photos/400/400" alt="User B" />
							<OpinionText>상대방 주장을 넣어주는 칸</OpinionText>
						</Opponent>
					</Opponents>
					<InfoText>
						<InfoTextSpan>
							예정된 라이브 토론의 상세 정보가 들어갑니다.
						</InfoTextSpan>
					</InfoText>
				</ModalBody>
			</ModalContent>
		</ModalBackdrop>
	);
}

export default UpcomingLivePreviewModal;
