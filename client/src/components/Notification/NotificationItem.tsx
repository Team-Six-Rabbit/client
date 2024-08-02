/* eslint-disable jsx-a11y/no-static-element-interactions */
/* eslint-disable jsx-a11y/click-events-have-key-events */
import { useState } from "react";
import NotificationModal from "@/components/Notification/NotificationModal";
import { Notification } from "@/types/notification";

interface NotificationItemProps {
	notification: Notification;
	onDelete: () => void;
}

function NotificationItem({ notification, onDelete }: NotificationItemProps) {
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
					{notification.message}
				</div>
				<button type="button" onClick={handleDelete} className="text-royalBlue">
					X
				</button>
			</div>
			<NotificationModal
				isModalOpen={isModalOpen}
				onModalClose={() => setIsModalOpen(false)}
				handleDelete={handleDelete}
				notification={notification}
			/>
		</div>
	);
}

export default NotificationItem;
