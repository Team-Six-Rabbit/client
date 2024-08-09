import { useState } from "react";
import Header from "@/components/header";
import NotificationItem from "@/components/Notification/NotificationItem";
import NotificationMenu from "@/components/Notification/NotificationMenu";
import { Notification } from "@/types/notification";
import { notificationsData } from "@/components/Notification/NotificationData";
import "@/assets/styles/scrollbar.css";

function NotificationPage() {
	const [selectedMenu, setSelectedMenu] = useState("Notify");
	const [notifications, setNotifications] =
		useState<Notification[]>(notificationsData);

	const onDelete = (code: string) => {
		// 삭제 요청 api
		//
		setNotifications(notifications.filter((n) => n.code !== code));
	};

	const filteredNotifications =
		selectedMenu === "Notify"
			? notifications
			: notifications.filter(
					(notification) => notification.category === selectedMenu,
				);

	return (
		<>
			<Header />
			<div className="flex flex-col pt-28 px-20">
				<div className="text-4xl text-royalBlue mb-5">알림</div>
				<div className="bg-royalBlue w-1/5 h-2 rounded-lg mb-10" />
				<div className="flex flex-row">
					<div>
						<NotificationMenu
							selectedMenu={selectedMenu}
							onSelectMenu={setSelectedMenu}
						/>
					</div>
					<div className="ml-16 h-128 flex-1 border-solid border-royalBlue border-4 rounded-lg overflow-hidden overflow-y-auto custom-scrollbar">
						{filteredNotifications.map((notification) => (
							<NotificationItem
								key={notification.code}
								notification={notification}
								onDelete={() => onDelete(notification.code)}
							/>
						))}
					</div>
				</div>
			</div>
		</>
	);
}

export default NotificationPage;
