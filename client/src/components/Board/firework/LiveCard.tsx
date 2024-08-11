import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { CardType } from "@/types/Board/liveBoardCard";
import { LiveStatus } from "@/types/Board/liveStatus";
import UpcomingLivePreviewModal from "@/components/Modal/UpcomingLivePreviewModal";
import EndedLivePreviewModal from "@/components/Modal/EndedLivePreviewModal";
import { createLiveStateBorder } from "@/utils/textBorder";
import { convertToTimeZone } from "@/utils/dateUtils";
import noImage from "@/assets/images/noImage.png"; // Make sure this path is correct

const getLiveStatusBackgroundColor = (status: LiveStatus, index: number) => {
	if (status === "live") return "bg-transparent";
	if (status === "ended") return "bg-gray-500";
	const colors = ["bg-orange", "bg-blue", "bg-yellow", "bg-olive"];
	return colors[index % colors.length];
};

const getLiveStatusBackgroundText = (status: LiveStatus) => {
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

function LiveCard({
	id,
	image_uri,
	title,
	regist_user_id,
	status,
	start_date,
	currentPeopleCount,
}: CardType) {
	const [isModalOpen, setIsModalOpen] = useState(false);
	const navigate = useNavigate();

	const handleCardClick = () => {
		if (status !== "live") {
			setIsModalOpen(true);
		}
		if (status === "live") {
			navigate("/live");
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
					{getLiveStatusBackgroundText(status)}
				</div>
			);
		}

		const borderStyles = createLiveStateBorder("black", 3);

		return (
			<>
				<div
					className={`absolute inset-0 ${getLiveStatusBackgroundColor(status, id)} opacity-75 flex items-center justify-center`}
				/>
				<div
					className="absolute inset-0 flex items-center justify-center text-white text-3xl"
					style={borderStyles}
				>
					{getLiveStatusBackgroundText(status)}
				</div>
			</>
		);
	};

	return (
		<>
			<div
				className="relative flex flex-col border-solid border-black border-4 shadow-md rounded-xl overflow-hidden focus:shadow-lg focus:-translate-y-1 hover:shadow-lg hover:-translate-y-1 transition-all duration-300 max-w-sm"
				onClick={handleCardClick}
				onKeyDown={handleKeyDown}
				tabIndex={0}
				role="button"
				aria-pressed="false"
			>
				<div className="live-card-image h-44 relative overflow-hidden">
					<img
						src={image_uri || noImage} // Use noImage if image_uri is null or undefined
						alt={title}
						className="w-full h-full object-cover"
					/>
					{renderStatusOverlay()}
				</div>
				<div className="live-card-info bg-white py-4 px-3 border-t-4 border-solid">
					<h3 className="text-xl mb-2 font-medium">{title}</h3>
					<div className="flex justify-between items-center">
						<p className="text-base text-black">{regist_user_id}</p>
						{status === "upcoming" && start_date && (
							<div className="text-sm text-black">
								{convertToTimeZone(start_date, "Asia/Seoul")}
							</div>
						)}
						{status === "live" && (
							<div className="text-sm text-black">
								{currentPeopleCount}명 시청중
							</div>
						)}
					</div>
				</div>
			</div>
			{isModalOpen && status === "upcoming" && (
				<UpcomingLivePreviewModal battleId={id} onClose={handleCloseModal} />
			)}
			{isModalOpen && status === "ended" && (
				<EndedLivePreviewModal battleId={id} onClose={handleCloseModal} />
			)}
		</>
	);
}

export default LiveCard;
