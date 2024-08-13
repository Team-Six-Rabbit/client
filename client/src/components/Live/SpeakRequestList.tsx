import { useState } from "react";
import { SpeakResponse } from "@/types/liveMessageType";

interface SpeechRequestListProps {
	userId: number;
	connectionId: string | undefined;
	speakRequests: SpeakResponse[];
	sendSpeak: (userId: number, connectionId: string) => void;
	sendRequestAccept: (connectionId: string) => void;
	buttonDisabled: boolean;
	onButtonClick: () => void;
	role: number;
}

function SpeakRequestList({
	userId,
	connectionId,
	speakRequests,
	sendSpeak,
	sendRequestAccept,
	buttonDisabled,
	onButtonClick,
	role,
}: SpeechRequestListProps) {
	const [isOpen, setIsOpen] = useState(false);

	const toggleOpen = () => {
		setIsOpen(!isOpen);
	};

	const handleRequestSpeech = () => {
		sendSpeak(userId, connectionId!); // (userId: number, connectionId: string)
		onButtonClick();
	};

	if (role === -1) {
		return (
			<div className="relative">
				<button
					type="button"
					onClick={handleRequestSpeech}
					className={`border-solid border-4 border-black p-2 w-full text-left rounded-lg ${buttonDisabled ? "bg-black text-white" : ""}`}
					disabled={buttonDisabled}
				>
					발언권 신청
				</button>
			</div>
		);
	}

	const filteredRequests = speakRequests.filter(
		(req) => req.userVote === role, // 투표한 값이 자신의 역할값과 같은 경우만
	);

	return (
		<div className="relative">
			<button
				type="button"
				onClick={toggleOpen}
				className="border-solid border-4 border-black text-black p-2 w-full text-left rounded-lg"
			>
				발언권 신청 목록
			</button>
			{isOpen && (
				<div className="absolute top-full left-0 w-full bg-white border-solid border-2 border-black rounded-lg shadow-lg z-10 h-40 overflow-y-auto custom-scrollbar">
					{filteredRequests.map((request) => (
						<div key={request.idx}>
							<div className="p-2 border-b-2 border-gray-300">
								{request.nickname}[{request.rating}]
							</div>
							<button
								type="button"
								onClick={() => sendRequestAccept(request.connectionId)}
							>
								V
							</button>
						</div>
					))}
				</div>
			)}
		</div>
	);
}

export default SpeakRequestList;
