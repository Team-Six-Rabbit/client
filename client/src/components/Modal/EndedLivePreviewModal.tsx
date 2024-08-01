import ModalForm from "@/components/Modal/ModalForm";
import { EndedLivePreviewModalType } from "@/types/Board/modalTypes";
import ModalContent from "@/components/Modal/ModalContent";
import "@/assets/styles/scrollbar.css";

interface EndedLivePreviewModalProps {
	data: EndedLivePreviewModalType;
	onClose: () => void;
}

function EndedLivePreviewModal({ data, onClose }: EndedLivePreviewModalProps) {
	if (!data) {
		return null;
	}

	const { title, registerUser, oppositeUser, preResult, finalResult, summary } =
		data;

	return (
		<ModalForm
			title="종료된 라이브 상세보기"
			infoText={title}
			summary={summary}
			onClose={onClose}
			borderColor="#8bc34a"
		>
			<ModalContent
				registerUser={registerUser}
				oppositeUser={oppositeUser}
				speechBubbleColor="#8bc34a"
				status="ended"
				preResult={preResult}
				finalResult={finalResult}
			/>
		</ModalForm>
	);
}

export default EndedLivePreviewModal;
