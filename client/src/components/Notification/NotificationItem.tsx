// src/components/NotificationItem.tsx
import { useState } from "react";

interface NotificationItemProps {
	message: string;
	onDelete: () => void;
}

function NotificationItem({ message, onDelete }: NotificationItemProps) {
	const [isDeleting, setIsDeleting] = useState(false);

	const handleDelete = () => {
		setIsDeleting(true);
		setTimeout(onDelete, 300);
	};

	return (
		<div
			className={`flex justify-between items-center p-4 my-2 mx-4 border-solid border-royalBlue border-b-2 transition-transform duration-300 ease-in-out ${
				isDeleting ? "transform -translate-x-full" : ""
			}`}
		>
			<div>{message}</div>
			<button type="button" onClick={handleDelete} className="text-royalBlue">
				X
			</button>
		</div>
	);
}

export default NotificationItem;
