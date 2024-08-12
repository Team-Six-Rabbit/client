import { useEffect, useState } from "react";
import ModalForm from "@/components/Modal/ModalForm";
import { authService, VoteDetail } from "@/services/userAuthService";
import "@/assets/styles/scrollbar.css";

interface ParticipatedVotesModalProps {
	voteId: number;
	onClose: () => void;
}

function ParticipatedVotesModal({
	voteId,
	onClose,
}: ParticipatedVotesModalProps) {
	const [data, setData] = useState<VoteDetail | null>(null);
	const [isLoading, setIsLoading] = useState<boolean>(true);

	useEffect(() => {
		const fetchVoteDetail = async () => {
			try {
				const VoteDetail = await authService.getVoteDetail(voteId);
				setData(VoteDetail); // API 응답 데이터를 상태에 저장
			} catch (error) {
				console.error("Failed to fetch vote detail:", error);
			} finally {
				setIsLoading(false);
			}
		};

		fetchVoteDetail();
	}, [voteId]);

	if (isLoading) {
		return <div>Loading...</div>; // 로딩 중 처리
	}

	if (!data) {
		return <div>데이터를 불러오지 못했습니다.</div>; // 데이터가 없을 때 처리
	}

	return (
		<ModalForm
			title="참여한 투표 결과"
			infoText={`총 ${data.totalCount}명이 참여했습니다.`}
			summary="투표에 대한 의견을 아래에서 확인하세요."
			onClose={onClose}
			borderColor="#3f51b5"
		>
			<div className="vote-results">
				{data.opinions.map((opinion) => (
					<div key={opinion.index} className="vote-opinion">
						<div className="opinion-text">{opinion.opinion}</div>
						<div className="opinion-stats">
							<span>{opinion.count}명</span>
							<span>({opinion.percentage}%)</span>
						</div>
					</div>
				))}
			</div>
		</ModalForm>
	);
}

export default ParticipatedVotesModal;
