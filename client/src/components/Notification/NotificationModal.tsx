/* eslint-disable jsx-a11y/no-static-element-interactions */
/* eslint-disable jsx-a11y/click-events-have-key-events */
interface ModalProps {
	isModalOpen: boolean;
	onModalClose: () => void;
	handleDelete: () => void;
	message: string;
	showModalButtons: boolean;
}

function Modal({
	isModalOpen,
	onModalClose,
	handleDelete,
	message,
	showModalButtons,
}: ModalProps) {
	if (!isModalOpen) return null;

	const handleBackgroundClick = (e: React.MouseEvent) => {
		if (e.target === e.currentTarget) {
			onModalClose();
		}
	};

	return (
		<div
			className="fixed inset-0 bg-gray-900 bg-opacity-50 flex justify-center items-center z-50"
			onClick={handleBackgroundClick}
		>
			<div className="bg-white p-4 rounded shadow-lg max-w-lg w-full">
				<div>{message}</div>
				{showModalButtons && (
					<div className="flex justify-end mt-4">
						<button
							type="button"
							className="bg-green-500 text-white px-4 py-2 rounded mr-2"
							onClick={() => {
								onModalClose();
								handleDelete();
							}}
						>
							Yes
						</button>
						<button
							type="button"
							className="bg-red-500 text-white px-4 py-2 rounded"
							onClick={() => {
								onModalClose();
								handleDelete();
							}}
						>
							No
						</button>
					</div>
				)}
			</div>
		</div>
	);
}

export default Modal;
