import React, { useState } from "react";
import { RiCornerDownLeftLine } from "react-icons/ri";
import { ChatMessage } from "@/types/Chat";
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
				placeholder="채팅을 입력해주세요."
				className="flex-1 px-2 py-1 outline-none"
			/>
			<RiCornerDownLeftLine className="h-full w-6" />
		</div>
	);
}

interface ChatBoxProps {
	messages: ChatMessage[];
	sendMessage: (userId: number, message: string) => void;
	userId: number;
}
function ChatBox({ messages, sendMessage, userId }: ChatBoxProps) {
	return (
		<>
			<div className="flex-1 overflow-y-auto p-2 my-2 border-solid border-4 border-black rounded-lg flex flex-col-reverse scrollbar-hide">
				<div>
					{messages.map((msg) => (
						<Chat
							key={msg.idx}
							idx={msg.idx}
							user={msg.user}
							message={msg.message}
							userVote={msg.userVote}
						/>
					))}
				</div>
			</div>
			<ChatInput sendMessage={(message) => sendMessage(userId, message)} />
		</>
	);
}

export default ChatBox;
