import { useEffect, useState } from "react";
import { Client, IMessage } from "@stomp/stompjs";
import { ChatMessage } from "@/types/Chat";

interface UseChatStompReturn {
	messages: ChatMessage[];
	sendMessage: (userId: number, message: string) => void;
}

const useChatSocket = (battleBoardId: number): UseChatStompReturn => {
	const [messages, setMessages] = useState<ChatMessage[]>([]);
	const [stompClient, setStompClient] = useState<Client | null>(null);

	const addMessage = (message: ChatMessage) => {
		setMessages((prevMessages) => {
			const updatedMessages = [...prevMessages, message];
			return updatedMessages.slice(-40); // 마지막 40개 메시지만 유지
		});
	};

	useEffect(() => {
		const socket = new WebSocket(import.meta.env.VITE_APP_WEBSOCKET_URL);
		const client = new Client({
			webSocketFactory: () => socket,
			debug: (str) => console.log(str),
			reconnectDelay: 5000,
			heartbeatIncoming: 4000,
			heartbeatOutgoing: 4000,
			onConnect: () => {
				console.log("Connected to chat server");

				// 구독 설정
				const chatSubscription = client.subscribe(
					`/topic/chat/${battleBoardId}`,
					(message: IMessage) => {
						const parsedMessage: ChatMessage = JSON.parse(message.body);
						addMessage(parsedMessage);
					},
				);

				// 구독 해제 및 리소스 해제
				return () => {
					chatSubscription.unsubscribe();
					client.deactivate();
				};
			},
			onStompError: (frame) => {
				console.error("STOMP error", frame);
			},
		});

		client.activate();
		setStompClient(client);

		// 상태 초기화
		return () => {
			setMessages([]);
			client.deactivate();
		};
	}, [battleBoardId]);

	const sendMessage = (userId: number, message: string) => {
		if (stompClient && stompClient.connected) {
			const chatMessage = {
				userId,
				message,
			};
			stompClient.publish({
				destination: `/app/chat/${battleBoardId}`,
				body: JSON.stringify(chatMessage),
			});
		} else {
			console.error("Unable to send message: STOMP client is not connected.");
		}
	};

	return {
		messages,
		sendMessage,
	};
};

export default useChatSocket;
