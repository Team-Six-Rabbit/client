import React, { useState } from "react";
import { CardType } from "@/types/Board/liveBoardCard";
import UpcomingLivePreviewModal from "@/components/Modal/UpcomingLivePreviewModal";
import EndedLivePreviewModal from "@/components/Modal/EndedLivePreviewModal";
import { createLiveStateBorder } from "@/utils/textBorder";

const getStatusColor = (
	status: "live" | "upcoming" | "ended",
	index: number,
) => {
	if (status === "live") return "bg-transparent";
	if (status === "ended") return "bg-gray-500";
	const colors = ["bg-orange", "bg-blue", "bg-yellow", "bg-olive"];
	return colors[index % colors.length];
};

const getStatusText = (status: "live" | "upcoming" | "ended") => {
	switch (status) {
		case "live":
			return "라이브";
		case "upcoming":
			return "예정된 라이브";
		case "ended":
			return "종료된 라이브";
		default:
			return "";
	}
};

const formatDate = (dateString: string) => {
	const options: Intl.DateTimeFormatOptions = {
		year: "numeric",
		month: "2-digit",
		day: "2-digit",
		hour: "2-digit",
		minute: "2-digit",
		hour12: false,
	};
	const date = new Date(dateString);
	return date
		.toLocaleString("ko-KR", options)
		.replace(/\. /g, "/")
		.replace(". ", " ");
};

function LiveCard({
	image_uri,
	title,
	regist_user_id,
	opposite_user_id,
	status,
	live_apply_user_count,
	start_date,
	index,
}: CardType) {
	const [isModalOpen, setIsModalOpen] = useState(false);

	const handleCardClick = () => {
		if (status !== "live") {
			setIsModalOpen(true);
		}
	};

	const handleCloseModal = () => {
		setIsModalOpen(false);
	};

	const handleKeyDown = (event: React.KeyboardEvent<HTMLDivElement>) => {
		if (event.key === "Enter" || event.key === " ") {
			handleCardClick();
		}
	};

	const renderStatusOverlay = () => {
		if (status === "live") {
			return (
				<div className="absolute top-2 left-2 bg-red-600 text-white px-2 py-1 rounded z-10">
					{getStatusText(status)}
				</div>
			);
		}

		const borderStyles = createLiveStateBorder("black", 3);

		return (
			<>
				<div
					className={`absolute inset-0 ${getStatusColor(status, index)} opacity-75 flex items-center justify-center`}
				/>
				<div
					className="absolute inset-0 flex items-center justify-center text-white text-3xl"
					style={borderStyles}
				>
					{getStatusText(status)}
				</div>
			</>
		);
	};

	return (
		<>
			<div
				className="relative flex flex-col border-solid border-black border-4 shadow-md rounded-xl overflow-hidden hover:shadow-lg hover:-translate-y-1 transition-all duration-300 max-w-sm"
				onClick={handleCardClick}
				onKeyDown={handleKeyDown}
				tabIndex={0}
				role="button"
				aria-pressed="false"
			>
				<div className="h-44 relative overflow-hidden">
					<img
						src={image_uri}
						alt={title}
						className="w-full h-full object-cover"
					/>
					{renderStatusOverlay()}
				</div>
				<div className="bg-white py-4 px-3 border-solid border-t-4">
					<h3 className="text-xl mb-2 font-medium">{title}</h3>
					<div className="flex justify-between items-center">
						<p className="text-base text-black">{regist_user_id}</p>
						{status === "upcoming" && start_date && (
							<div className="text-sm text-black">{formatDate(start_date)}</div>
						)}
						{status === "live" && (
							<div className="text-sm text-black">
								{live_apply_user_count}명 시청중
							</div>
						)}
					</div>
				</div>
			</div>
			{isModalOpen && status === "upcoming" && (
				<UpcomingLivePreviewModal
					title={title}
					regist_user_id={regist_user_id}
					opposite_user_id={opposite_user_id}
					onClose={handleCloseModal}
				/>
			)}
			{isModalOpen && status === "ended" && (
				<EndedLivePreviewModal
					title={title}
					regist_user_id={regist_user_id}
					opposite_user_id={opposite_user_id}
					onClose={handleCloseModal}
				/>
			)}
		</>
	);
}

export default LiveCard;
