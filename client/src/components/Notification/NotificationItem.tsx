/* eslint-disable jsx-a11y/no-static-element-interactions */
/* eslint-disable jsx-a11y/click-events-have-key-events */
import { useState } from "react";
import Modal from "@/components/Notification/NotificationModal";

interface NotificationItemProps {
	message: string;
	category: string;
	onDelete: () => void;
}

function NotificationItem({
	message,
	category,
	onDelete,
}: NotificationItemProps) {
	const [isModalOpen, setIsModalOpen] = useState(false);
	const [isDeleting, setIsDeleting] = useState(false);

	const handleDelete = () => {
		setIsDeleting(true);
		setTimeout(onDelete, 300);
	};

	return (
		<div>
			<div
				className={`flex justify-between items-center p-4 my-2 mx-4
										border-solid border-royalBlue border-b-2
										transition-transform duration-300 ease-in-out
										${isDeleting ? "transform -translate-x-full" : ""}`}
			>
				<div className="cursor-pointer" onClick={() => setIsModalOpen(true)}>
					{message}
				</div>
				<button type="button" onClick={handleDelete} className="text-royalBlue">
					X
				</button>
			</div>
			<Modal
				isModalOpen={isModalOpen}
				onModalClose={() => setIsModalOpen(false)}
				handleDelete={handleDelete}
				message={message}
				showModalButtons={category === "Live"}
			/>
		</div>
	);
}

export default NotificationItem;
