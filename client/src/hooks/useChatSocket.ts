import { useEffect, useState } from "react";
import { Client, IMessage } from "@stomp/stompjs";
import { ChatMessage, SpeechRequestMessage } from "@/types/Chat";

interface UseChatStompReturn {
	messages: ChatMessage[];
	speechRequests: SpeechRequestMessage[];
	sendMessage: (message: string) => void;
	sendRequestSpeech: () => void;
}

const useChatSocket = (topic: string): UseChatStompReturn => {
	const [messages, setMessages] = useState<ChatMessage[]>([]);
	const [speechRequests, setSpeechRequests] = useState<SpeechRequestMessage[]>(
		[],
	);
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

				// 채팅 메시지 받는 곳
				client.subscribe(`/topic/chat/${topic}`, (message: IMessage) => {
					const parsedMessage: ChatMessage = JSON.parse(message.body);
					setMessages((prevMessages) => [...prevMessages, parsedMessage]);
				});

				// 발언권 신청자 받는 곳
				client.subscribe(`/topic/request/${topic}`, (message: IMessage) => {
					const parsedRequest: SpeechRequestMessage = JSON.parse(message.body);
					setSpeechRequests((prevRequests) => [...prevRequests, parsedRequest]);
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
			const chatMessage = {
				message,
			};
			stompClient.publish({
				destination: `/app/chat/${topic}`,
				body: JSON.stringify(chatMessage),
			});
		}
	};

	const sendRequestSpeech = () => {
		if (stompClient && stompClient.connected) {
			stompClient.publish({
				destination: `/app/request/${topic}`,
				body: JSON.stringify({}),
			});
		}
	};

	return {
		messages,
		speechRequests,
		sendMessage,
		sendRequestSpeech,
	};
};

export default useChatSocket;
