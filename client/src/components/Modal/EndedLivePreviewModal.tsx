import styled from "styled-components";

interface EndedLivePreviewModalProps {
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
	max-width: 800px;
	width: 100%;
	border: 8px solid #8bc34a; /* 녹색 테두리 */
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
	color: #8bc34a;
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
	border: 3px solid #8bc34a;
	border-radius: 15px;
	padding: 10px;
`;

const InfoTextSpan = styled.span`
	font-weight: 400;
	color: #6b7280;
`;

const VoteResult = styled.div`
	display: flex;
	flex-direction: column;
	align-items: center;
	width: 100%;
`;

const VoteColumn = styled.div`
	display: flex;
	flex-direction: column;
	align-items: center;
	width: 100%;
`;

const ProgressBarContainer = styled.div`
	width: 100%;
	margin-bottom: 8px;
	position: relative;
	height: 24px;
	border-radius: 12px;
	overflow: hidden;
	background-color: #f0f0f0;
`;

const ProgressBar = styled.div<{ percentage: number; color: string }>`
	width: ${({ percentage }) => percentage}%;
	height: 24px;
	background-color: ${({ color }) => color};
	position: absolute;
	top: 0;
	left: 0;
`;

const VoteRow = styled.div`
	display: flex;
	justify-content: space-between;
	width: 100%;
	padding: 8px;
`;

const VoteText = styled.p`
	font-size: 1rem;
`;

const Opponents = styled.div`
	display: flex;
	justify-content: space-around;
	align-items: center;
	width: 100%;
`;

const Opponent = styled.div`
	display: flex;
	flex-direction: column;
	align-items: center;
	margin: 0 30px;
`;

const OpponentImage = styled.img`
	width: 150px;
	height: 150px;
	border-radius: 50%;
	margin-bottom: 8px;
`;

const VoteResultsContainer = styled.div`
	display: flex;
	flex-direction: column;
	align-items: center;
	flex-grow: 1;
`;

function EndedLivePreviewModal({
	title,
	regist_user_id,
	opposite_user_id,
	onClose,
}: EndedLivePreviewModalProps) {
	return (
		<ModalBackdrop>
			<ModalContent>
				<ModalHeader>
					<ModalTitle>종료된 라이브 상세보기</ModalTitle>
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
										<VoteText>A 주장 76%</VoteText>
										<VoteText>B 주장 24%</VoteText>
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
										<VoteText>A 주장 24%</VoteText>
										<VoteText>B 주장 76%</VoteText>
									</VoteRow>
								</VoteColumn>
							</VoteResult>
						</VoteResultsContainer>
						<Opponent>
							<OpponentImage src="https://picsum.photos/400/400" alt="User B" />
							<InfoTextSpan>{opposite_user_id}</InfoTextSpan>
						</Opponent>
					</Opponents>
					<InfoText>
						<InfoTextSpan>
							생성된 AI로 재현된 라이브 토론 내용의 요약이 들어갑니다.
						</InfoTextSpan>
					</InfoText>
				</ModalBody>
			</ModalContent>
		</ModalBackdrop>
	);
}

export default EndedLivePreviewModal;
