import { useState } from "react";
import {
	FaVideo,
	FaVideoSlash,
	FaMicrophone,
	FaMicrophoneSlash,
	FaBomb,
	FaTicketAlt,
} from "react-icons/fa";

interface VideoAudioIconsProps {
	isMicMuted: boolean;
	isVideoDisabled: boolean;
	onMicClick: () => void;
	onVideoClick: () => void;
}

function VideoAudioIcons({
	isMicMuted,
	isVideoDisabled,
	onMicClick,
	onVideoClick,
}: VideoAudioIconsProps) {
	return (
		<div className="flex items-center space-x-4 ms-6">
			<div
				className="flex items-center cursor-pointer me-2"
				aria-label="Toggle Video"
				role="button"
			>
				{isVideoDisabled ? (
					<FaVideoSlash
						size={36}
						className="text-white cursor-pointer transform hover:scale-110 hidden"
						onClick={onVideoClick}
					/>
				) : (
					<FaVideo
						size={36}
						className="text-white cursor-pointer transform hover:scale-110 hidden"
						onClick={onVideoClick}
					/>
				)}
			</div>
			<div
				className="flex items-center cursor-pointer"
				aria-label="Toggle Microphone"
				role="button"
			>
				{isMicMuted ? (
					<FaMicrophoneSlash
						size={36}
						className="text-white cursor-pointer transform hover:scale-110 hidden"
						onClick={onMicClick}
					/>
				) : (
					<FaMicrophone
						size={34}
						className="text-white cursor-pointer transform hover:scale-110 hidden"
						onClick={onMicClick}
					/>
				)}
			</div>
		</div>
	);
}

// interface BombTicketIconsProps {
// 	sendItem: (userId: number, targetIndex: number, itemCode: number) => void;
// }
// { sendItem }: BombTicketIconsProps

function BombTicketIcons() {
	const [bombCount, setBombCount] = useState(2);
	const [ticketCount, setTicketCount] = useState(1);

	const handleBombClick = () => {
		setBombCount((prevCount) => Math.max(prevCount - 1, 0));
	};

	const handleTicketClick = () => {
		setTicketCount((prevCount) => Math.max(prevCount - 1, 0));
	};

	return (
		<div className="flex items-center space-x-4 me-4">
			<div className="flex items-center">
				<FaBomb
					size={36}
					className="text-white cursor-pointer transform hover:scale-110 hover:text-orange"
					onClick={handleBombClick}
					aria-label="Use Bomb"
					role="button"
				/>
				<span className="text-red-500 text-xl ml-2 me-2">{bombCount}</span>
			</div>
			<div className="flex items-center">
				<FaTicketAlt
					size={36}
					className="text-white cursor-pointer transform hover:scale-110 hover:text-blue"
					onClick={handleTicketClick}
					aria-label="Use Ticket"
					role="button"
				/>
				<span className="text-red-500 text-xl ml-2">{ticketCount}</span>
			</div>
		</div>
	);
}

interface ItemBoxProps {
	isMicMuted: boolean;
	isVideoDisabled: boolean;
	onMicClick: () => void;
	onVideoClick: () => void;
	sendItem: (userId: number, targetIndex: number, itemCode: number) => void;
}

function ItemBox({
	isMicMuted,
	isVideoDisabled,
	onMicClick,
	onVideoClick,
	sendItem,
}: ItemBoxProps) {
	console.debug(
		isMicMuted,
		isVideoDisabled,
		onMicClick,
		onVideoClick,
		BombTicketIcons,
		VideoAudioIcons,
	);

	console.log(sendItem);

	return (
		<div className="flex justify-between w-full p-2 mt-6 bg-black rounded-lg">
			<VideoAudioIcons
				isMicMuted={isMicMuted}
				isVideoDisabled={isVideoDisabled}
				onMicClick={onMicClick}
				onVideoClick={onVideoClick}
			/>
			<BombTicketIcons />
		</div>
	);
}

export default ItemBox;
