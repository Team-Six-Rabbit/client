import React, { useState } from "react";
import { RiCornerDownLeftLine } from "react-icons/ri";
import useChatSocket from "@/hooks/useChatSocket";
import "@/assets/styles/scrollbar.css";

interface ChatMessageProps {
	message: string;
}

export function ChatMessage({ message }: ChatMessageProps) {
	return (
		<div className="mb-2 p-2 border-solid border-2 border-blue rounded">
			{message}
		</div>
	);
}

interface ChatInputProps {
	onSendMessage: (message: string) => void;
}

export function ChatInput({ onSendMessage }: ChatInputProps) {
	const [input, setInput] = useState<string>("");

	const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
		if (event.key === "Enter" && input.trim() !== "") {
			onSendMessage(input);
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

interface ChatBoxProps {
	battleBoardId: string;
}

function ChatBox({ battleBoardId }: ChatBoxProps) {
	const { messages, sendMessage } = useChatSocket(battleBoardId);

	const handleSendMessage = (message: string) => {
		sendMessage(message);
	};

	return (
		<div className="flex flex-col h-150 w-1/4 ms-6 mt-2">
			<div className="flex-1 overflow-y-auto p-2 my-2 border-solid border-4 border-black rounded-lg flex flex-col-reverse scrollbar-hide">
				<div>
					{messages.map((msg) => (
						<ChatMessage key={msg} message={msg} />
					))}
				</div>
			</div>
			<ChatInput onSendMessage={handleSendMessage} />
		</div>
	);
}

export default ChatBox;
