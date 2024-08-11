import { useEffect, useState } from "react";
import Header from "@/components/header";
import NotificationItem from "@/components/Notification/NotificationItem";
import NotificationMenu from "@/components/Notification/NotificationMenu";
import { notificationService } from "@/services/notificationService";
import NotifyCode from "@/constant/notifyCode";
import {
	Notification,
	NotificationInviteDetail,
	NotificationLiveDetail,
} from "@/types/notification";

import "@/assets/styles/scrollbar.css";

function NotificationPage() {
	const [selectedMenu, setSelectedMenu] = useState(2); // 0:invite, 1:live, 2:notify => notifyCode
	const [notifications, setNotifications] = useState<Notification[]>([]);

	useEffect(() => {
		const fetchNotifications = async () => {
			try {
				const data = await notificationService.getNotificationList();
				setNotifications(data);
			} catch (error) {
				console.error("Failed to load notifications:", error);
			}
		};

		fetchNotifications();
	}, []);

	const data =
		NotifyCode.get(selectedMenu) === "Notify"
			? notifications
			: notifications.filter(
					(notification) => notification.notifyCode === selectedMenu,
				);

	const onDelete = async (id: number) => {
		try {
			await notificationService.deleteNotification(id);
		} catch (error) {
			console.error("Failed to delete notification", error);
		}
	};

	const onViewDetail = async (
		id: number,
	): Promise<NotificationLiveDetail | NotificationInviteDetail | null> => {
		try {
			const detail = await notificationService.getNotificationDetail(id);
			return detail.notifyCode === 0 // Code에는 2(전체보기)가 있지만 detail에서는 0과 1만 존재
				? (detail as NotificationInviteDetail)
				: (detail as NotificationLiveDetail);
		} catch (error) {
			console.error("Failed to fetch notification detail:", error);
			return null;
		}
	};

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
						{data.length > 0 &&
							data.map((notification) => (
								<NotificationItem
									key={notification.id}
									notification={notification}
									onDelete={() => onDelete(notification.id)}
									onViewDetail={() => onViewDetail(notification.id)}
								/>
							))}
					</div>
				</div>
			</div>
		</>
	);
}

export default NotificationPage;
