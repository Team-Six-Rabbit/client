import React, { useState } from "react";
import "@/assets/styles/scrollbar.css";
import { RiCornerDownLeftLine } from "react-icons/ri";

interface ChatMessageProps {
	message: string;
}

interface ChatInputProps {
	onSendMessage: (message: string) => void;
}

export function ChatMessage({ message }: ChatMessageProps) {
	return (
		<div className="mb-2 p-2 border-solid border-2 border-blue rounded">
			{message}
		</div>
	);
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

export function SpeechRequestList() {
	const [isOpen, setIsOpen] = useState(false);

	const toggleOpen = () => {
		setIsOpen(!isOpen);
	};

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
					<div className="p-2 border-b-2 border-gray-300">싸움꾼[천민]</div>
					<div className="p-2 border-b-2 border-gray-300">시비전문가[육두품]</div>
					<div className="p-2">방구석판사[진골]</div>
					<div className="p-2">방구석판사[진골]</div>
					<div className="p-2">방구석판사[진골]</div>
					<div className="p-2">방구석판사[진골]</div>
					<div className="p-2">방구석판사[진골]</div>
				</div>
			)}
		</div>
	);
}

function ChatBox() {
	const [messages, setMessages] = useState<string[]>([
		"싸움꾼[천민] : 내가 해도 저거보단 잘 하겠다ㅋㅋㅋ",
		"시비전문가[육두품] : 답답해 죽겠는데 그냥 나 발언권 주면 안됨?",
		"방구석판사[진골] : 누가봐도 A가 잘못한 거 아니냐? 이걸 A편을 드네;;",
	]);
	const handleSendMessage = (message: string) => {
		setMessages([...messages, message]);
	};

	return (
		<div className="flex flex-col h-15/16 w-1/4 ms-6 mt-4">
			<SpeechRequestList />
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
