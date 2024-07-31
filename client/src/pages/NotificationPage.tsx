// src/pages/NotificationPage.tsx
import { useState } from "react";
import Header from "@/components/header";
import NotificationItem from "@/components/Notification/NotificationItem";
import NotificationMenu from "@/components/Notification/NotificationMenu";
import "@/assets/styles/scrollbar.css";

const notificationsData = [
	{
		code: "A1",
		message:
			"‘마라탕후루후훗’님의 라이브 ‘오늘저녁메뉴추천’이 방송 5분 전입니다. url(https://ssafyssafy.com/)",
		category: "Live",
	},
	{
		code: "A2",
		message:
			"‘마라탕후루후훗’님의 라이브 ‘오늘저녁메뉴추천’이 방송 5분 전입니다. url(https://ssafyssafy.com/)",
		category: "Live",
	},
	{
		code: "A3",
		message:
			"‘마라탕후루후훗’님의 라이브 ‘오늘저녁메뉴추천’이 방송 5분 전입니다. url(https://ssafyssafy.com/)",
		category: "Live",
	},
	{
		code: "A4",
		message:
			"‘마라탕후루후훗’님의 라이브 ‘오늘저녁메뉴추천’이 방송 5분 전입니다. url(https://ssafyssafy.com/)",
		category: "Live",
	},
	{
		code: "B1",
		message: "사용자A님이 규칙을 위반하여 징계를 받았습니다.",
		category: "Punishment",
	},
	{
		code: "B2",
		message: "사용자A님이 규칙을 위반하여 징계를 받았습니다.",
		category: "Punishment",
	},
	{
		code: "B3",
		message: "사용자A님이 규칙을 위반하여 징계를 받았습니다.",
		category: "Punishment",
	},
	{
		code: "B4",
		message: "사용자A님이 규칙을 위반하여 징계를 받았습니다.",
		category: "Punishment",
	},
	{
		code: "B5",
		message: "사용자A님이 규칙을 위반하여 징계를 받았습니다.",
		category: "Punishment",
	},
	{
		code: "B6",
		message: "사용자A님이 규칙을 위반하여 징계를 받았습니다.",
		category: "Punishment",
	},
	{
		code: "B7",
		message: "사용자A님이 규칙을 위반하여 징계를 받았습니다.",
		category: "Punishment",
	},
];

function NotificationPage() {
	const [selectedMenu, setSelectedMenu] = useState("Notify");
	const [notifications, setNotifications] = useState(notificationsData);

	const onDelete = (code: string) => {
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
								message={notification.message}
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
