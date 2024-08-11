import { useEffect, useState } from "react";
import ModalForm from "@/components/Modal/ModalForm";
import ModalContent from "@/components/Modal/ModalContent";
import { liveBattleService } from "@/services/liveBattleService";
import { FinishedLiveBattleDetail } from "@/types/live";
import "@/assets/styles/scrollbar.css";

interface EndedLivePreviewModalProps {
	battleId: number;
	onClose: () => void;
}

function EndedLivePreviewModal({
	battleId,
	onClose,
}: EndedLivePreviewModalProps) {
	const [data, setData] = useState<FinishedLiveBattleDetail | null>(null);
	const [isLoading, setIsLoading] = useState<boolean>(true);

	useEffect(() => {
		const fetchEndDetail = async () => {
			try {
				const response = await liveBattleService.getEndDetail(
					battleId.toString(),
				);
				setData(response.data!); // API 응답 데이터를 상태에 저장
			} catch (error) {
				console.error("Failed to fetch ended live battle detail:", error);
			} finally {
				setIsLoading(false);
			}
		};

		fetchEndDetail();
	}, [battleId]);

	if (isLoading) {
		return <div>Loading...</div>; // 로딩 중 처리
	}

	if (!data) {
		return <div>데이터를 불러오지 못했습니다.</div>; // 데이터가 없을 때 처리
	}

	const { title, registerUser, oppositeUser, preResult, finalResult, summary } =
		data;

	return (
		<ModalForm
			title="종료된 라이브 상세보기"
			infoText={title}
			summary={summary || "상세정보가 없습니다."}
			onClose={onClose}
			borderColor="#8bc34a"
		>
			<ModalContent
				registerUser={{
					nickname: registerUser.nickname,
					imgUrl: registerUser.imgUrl || "",
					opinion: registerUser.opinion || "",
				}}
				oppositeUser={{
					nickname: oppositeUser.nickname,
					imgUrl: oppositeUser.imgUrl || "",
					opinion: oppositeUser.opinion || "",
				}}
				speechBubbleColor="#8bc34a"
				status="ended"
				preResult={preResult}
				finalResult={finalResult}
			/>
		</ModalForm>
	);
}

export default EndedLivePreviewModal;
