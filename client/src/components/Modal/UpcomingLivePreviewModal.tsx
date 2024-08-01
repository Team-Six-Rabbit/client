import ModalContent from "@/components/Modal/ModalContent";
import ModalForm from "@/components/Modal/ModalForm";
import { UpcomingLivePreviewModalType } from "@/types/Board/modalTypes";

interface UpcomingLivePreviewModalProps {
	data: UpcomingLivePreviewModalType;
	onClose: () => void;
}

function UpcomingLivePreviewModal({
	data,
	onClose,
}: UpcomingLivePreviewModalProps) {
	if (!data) {
		return null;
	}

	const { title, registerUser, oppositeUser, summary } = data;

	return (
		<ModalForm
			title="예정된 라이브 미리보기"
			infoText={title}
			summary={summary}
			onClose={onClose}
			borderColor="#0b68ec"
		>
			<ModalContent
				registerUser={registerUser}
				oppositeUser={oppositeUser}
				speechBubbleColor="#0b68ec"
				status="upcoming"
			/>
		</ModalForm>
	);
}

export default UpcomingLivePreviewModal;
