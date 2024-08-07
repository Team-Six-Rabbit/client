import { useEffect, useState } from "react";
import { Client, IMessage } from "@stomp/stompjs";

interface ChatMessage {
	userName: string;
	message: string;
	rating: number;
	userVote: number | null;
}

interface UseChatStompReturn {
	messages: string[];
	sendMessage: (message: string) => void;
}

const useChatSocket = (topic: string): UseChatStompReturn => {
	const [messages, setMessages] = useState<string[]>([]);
	const [stompClient, setStompClient] = useState<Client | null>(null);

	useEffect(() => {
		const socket = new WebSocket("ws://70.12.247.158:8080/battle-people/ws");
		const client = new Client({
			webSocketFactory: () => socket,
			debug: (str) => {
				console.log(str);
			},
			reconnectDelay: 5000,
			heartbeatIncoming: 4000,
			heartbeatOutgoing: 4000,
			onConnect: () => {
				console.log("Connected to chat server");
				client.subscribe(`/topic/chat/${topic}`, (message: IMessage) => {
					const parsedMessage: ChatMessage = JSON.parse(message.body);
					const formattedMessage = `${parsedMessage.userName}[${parsedMessage.rating}]: ${parsedMessage.message}`;
					setMessages((prevMessages) => [...prevMessages, formattedMessage]);
				});
			},
			onStompError: (frame) => {
				console.error("STOMP error", frame);
			},
		});

		client.activate();
		setStompClient(client);

		return () => {
			client.deactivate();
		};
	}, [topic]);

	const sendMessage = (message: string) => {
		if (stompClient && stompClient.connected) {
			const chatMessage: ChatMessage = {
				message,
			};
			stompClient.publish({
				destination: `/app/chat/${topic}`,
				body: JSON.stringify(chatMessage),
			});
		}
	};

	return {
		messages,
		sendMessage,
	};
};

export default useChatSocket;
