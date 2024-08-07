import React, { useState } from "react";
import { RiCornerDownLeftLine } from "react-icons/ri";
import useChatSocket from "@/hooks/useChatSocket";
import { ChatMessage, SpeechRequestMessage } from "@/types/Chat";
import "@/assets/styles/scrollbar.css";

function Chat({ user, message, userVote }: ChatMessage) {
	let borderColorClass = "";

	if (userVote === 0) {
		borderColorClass = "border-orange";
	} else if (userVote === 1) {
		borderColorClass = "border-blue";
	}

	return (
		<div
			className={`mb-2 p-2 border-solid border-2 rounded-lg ${borderColorClass}`}
		>
			<div>
				<strong>
					{user.nickname}[{user.rating}]
				</strong>
				: {message}
			</div>
		</div>
	);
}

interface ChatInputProps {
	sendMessage: (message: string) => void;
}

function ChatInput({ sendMessage }: ChatInputProps) {
	const [input, setInput] = useState<string>("");

	const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
		if (event.key === "Enter" && input.trim() !== "") {
			sendMessage(input);
			setInput("");
		}
	};

	return (
		<div className="flex p-2 border-solid border-4 border-black rounded-lg">
			<input
				type="text"
				value={input}
				onChange={(e) => setInput(e.target.value)}
				onKeyDown={handleKeyDown}
				placeholder="Type your message..."
				className="flex-1 px-2 py-1 outline-none"
			/>
			<RiCornerDownLeftLine className="h-full w-6" />
		</div>
	);
}

interface SpeechRequestListProps {
	role: number;
	speechRequests: SpeechRequestMessage[];
	sendRequestSpeech: () => void;
}

function SpeechRequestList({
	role,
	speechRequests,
	sendRequestSpeech,
}: SpeechRequestListProps) {
	const [isOpen, setIsOpen] = useState(false);
	const [buttonDisabled, setButtonDisabled] = useState(false);

	const toggleOpen = () => {
		setIsOpen(!isOpen);
	};

	const handleRequestSpeech = () => {
		sendRequestSpeech();
		setButtonDisabled(true);
	};

	if (role !== 0 && role !== 1) {
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

	const filteredRequests = speechRequests.filter(
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
						<div key={request.idx} className="p-2 border-b-2 border-gray-300">
							{request.user.nickname}[{request.user.rating}]
						</div>
					))}
				</div>
			)}
		</div>
	);
}

interface ChatBoxProps {
	battleBoardId: string;
	role: number;
}

function ChatBox({ battleBoardId, role }: ChatBoxProps) {
	const { messages, sendMessage, speechRequests, sendRequestSpeech } =
		useChatSocket(battleBoardId);

	return (
		<div className="flex flex-col h-150 w-1/4 ms-6 mt-2">
			<SpeechRequestList
				role={role}
				speechRequests={speechRequests}
				sendRequestSpeech={sendRequestSpeech}
			/>
			<div className="flex-1 overflow-y-auto p-2 my-2 border-solid border-4 border-black rounded-lg flex flex-col-reverse scrollbar-hide">
				<div>
					{messages.map((msg) => (
						<Chat
							idx={msg.idx}
							user={msg.user}
							message={msg.message}
							userVote={msg.userVote}
						/>
					))}
				</div>
			</div>
			<ChatInput sendMessage={(message) => sendMessage(message)} />
		</div>
	);
}

export default ChatBox;
