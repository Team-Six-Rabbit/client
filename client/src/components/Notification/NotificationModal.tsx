/* eslint-disable jsx-a11y/control-has-associated-label */
/* eslint-disable jsx-a11y/no-static-element-interactions */
/* eslint-disable jsx-a11y/click-events-have-key-events */
import React, { useState } from "react";
import { Notification } from "@/types/notification";

interface NotificationModalProps {
	isModalOpen: boolean;
	onModalClose: () => void;
	handleDelete: () => void;
	notification: Notification;
}

function NotificationModal({
	isModalOpen,
	onModalClose,
	handleDelete,
	notification,
}: NotificationModalProps) {
	const [inputValue, setInputValue] = useState("");
	const [isAccepted, setIsAccepted] = useState(true);

	if (!isModalOpen) return null;

	const handleBackgroundClick = (e: React.MouseEvent) => {
		if (e.target === e.currentTarget) {
			onModalClose();
		}
	};

	const handleParticipateClick = () => {
		setIsAccepted(true);
		setInputValue("");
	};

	const handleRejectClick = () => {
		setIsAccepted(false);
		setInputValue("");
	};

	const handleSendClick = () => {
		// 추후에 소켓 연결 처리 추가
		handleDelete();
	};

	return (
		<div
			className="fixed inset-0 bg-gray-900 bg-opacity-50 flex justify-center items-center z-50"
			onClick={handleBackgroundClick}
		>
			<div className="bg-white border border-slate-200 grid grid-cols-6 gap-2 rounded-xl p-2 text-sm">
				<h1 className="text-center text-royalBlue text-xl font-bold col-span-6">
					{notification.category}
				</h1>
				{notification.category === "Invite" && (
					<>
						<h2 className="col-span-6 break-all w-full pre-wrap">
							상대방의 선택지: {notification.opposite}
						</h2>
						<textarea
							placeholder={
								isAccepted
									? "본인의 선택지를 16글자 안으로 적어주세요."
									: "거절 사유를 적어주세요."
							}
							maxLength={isAccepted ? 16 : undefined}
							value={inputValue}
							onChange={(e) => setInputValue(e.target.value)}
							className="bg-slate-100 text-slate-600 h-28 placeholder:text-slate-600 placeholder:opacity-50 border border-slate-200 col-span-6 resize-none outline-none rounded-lg p-2 duration-300 focus:border-slate-600"
						/>
						<button
							type="button"
							className={`fill-slate-600 col-span-1 flex justify-center items-center rounded-lg p-2 duration-300 bg-slate-100 ${
								isAccepted
									? "border border-royalBlue"
									: "border border-slate-200"
							} hover:border-slate-600 focus:fill-blue-200 focus:bg-blue-400`}
							onClick={handleParticipateClick}
						>
							<svg
								xmlns="http://www.w3.org/2000/svg"
								height="20px"
								viewBox="0 0 512 512"
							>
								<path d="M464 256A208 208 0 1 0 48 256a208 208 0 1 0 416 0zM0 256a256 256 0 1 1 512 0A256 256 0 1 1 0 256zm177.6 62.1C192.8 334.5 218.8 352 256 352s63.2-17.5 78.4-33.9c9-9.7 24.2-10.4 33.9-1.4s10.4 24.2 1.4 33.9c-22 23.8-60 49.4-113.6 49.4s-91.7-25.5-113.6-49.4c-9-9.7-8.4-24.9 1.4-33.9s24.9-8.4 33.9 1.4zM144.4 208a32 32 0 1 1 64 0 32 32 0 1 1 -64 0zm192-32a32 32 0 1 1 0 64 32 32 0 1 1 0-64z" />
							</svg>
						</button>
						<button
							type="button"
							className={`fill-slate-600 col-span-1 flex justify-center items-center rounded-lg p-2 duration-300 bg-slate-100 ${
								!isAccepted
									? "border border-royalBlue"
									: "border border-slate-200"
							} hover:border-slate-600 focus:fill-blue-200 focus:bg-blue-400`}
							onClick={handleRejectClick}
						>
							<svg
								xmlns="http://www.w3.org/2000/svg"
								height="20px"
								viewBox="0 0 512 512"
							>
								<path d="M464 256A208 208 0 1 0 48 256a208 208 0 1 0 416 0zM0 256a256 256 0 1 1 512 0A256 256 0 1 1 0 256zm174.6 384.1c-4.5 12.5-18.2 18.9-30.7 14.4s-18.9-18.2-14.4-30.7C146.9 319.4 198.9 288 256 288s109.1 31.4 126.6 79.9c4.5 12.5-2 26.2-14.4 30.7s-26.2-2-30.7-14.4C328.2 358.5 297.2 336 256 336s-72.2 22.5-81.4 48.1zM144.4 208a32 32 0 1 1 64 0 32 32 0 1 1 -64 0zm192-32a32 32 0 1 1 0 64 32 32 0 1 1 0-64z" />
							</svg>
						</button>
					</>
				)}
				{notification.category === "Punishment" && (
					<div className="bg-slate-100 text-slate-600 h-28 placeholder:text-slate-600 placeholder:opacity-50 border border-slate-200 col-span-6 resize-none outline-none rounded-lg p-2 duration-300 focus:border-slate-600">
						{notification.message}
					</div>
				)}
				{notification.category === "Live" && (
					<div className="bg-slate-100 text-slate-600 h-28 placeholder:text-slate-600 placeholder:opacity-50 border border-slate-200 col-span-6 resize-none outline-none rounded-lg p-2 duration-300 focus:border-slate-600">
						{/* 'nickname'님의 라이브\n 'title'이 방송 5분전입니다. */}
						{notification.message}
						<p>
							바로가기:{" "}
							<a href={notification.url} className="text-blue">
								{notification.url}
							</a>
						</p>
					</div>
				)}
				<span className="col-span-2" />
				{notification.category === "Invite" && (
					<button
						type="button"
						className="bg-slate-100 stroke-slate-600 border border-slate-200 col-span-2 flex justify-center rounded-lg p-2 duration-300 hover:border-slate-600 hover:text-white focus:stroke-blue-200 focus:bg-blue-400"
						onClick={handleSendClick}
					>
						<svg
							fill="none"
							viewBox="0 0 24 24"
							height="30px"
							width="30px"
							xmlns="http://www.w3.org/2000/svg"
						>
							<path
								strokeLinejoin="round"
								strokeLinecap="round"
								strokeWidth="1.5"
								d="M7.39999 6.32003L15.89 3.49003C19.7 2.22003 21.77 4.30003 20.51 8.11003L17.68 16.6C15.78 22.31 12.66 22.31 10.76 16.6L9.91999 14.08L7.39999 13.24C1.68999 11.34 1.68999 8.23003 7.39999 6.32003Z"
							/>
							<path
								strokeLinejoin="round"
								strokeLinecap="round"
								strokeWidth="1.5"
								d="M10.11 13.6501L13.69 10.0601"
							/>
						</svg>
					</button>
				)}
			</div>
		</div>
	);
}

export default NotificationModal;
