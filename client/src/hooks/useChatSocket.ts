import { useEffect, useState } from "react";
import { Client, IMessage } from "@stomp/stompjs";
import { ChatMessage, SpeechRequestMessage } from "@/types/Chat";
import useChatStorage from "@/hooks/useChatStorage";
import Cookies from "js-cookie";

interface UseChatStompReturn {
	messages: ChatMessage[];
	speechRequests: SpeechRequestMessage[];
	sendMessage: (message: string) => void;
	sendSpeechRequest: () => void;
}

const useChatSocket = (battleBoardId: string): UseChatStompReturn => {
	const { messages, speechRequests, addMessage, addSpeechRequest } =
		useChatStorage();
	const [stompClient, setStompClient] = useState<Client | null>(null);

	useEffect(() => {
		const socket = new WebSocket("ws://70.12.247.158:8080/battle-people/ws");
		const client = new Client({
			webSocketFactory: () => socket,
			connectHeaders: {
				Authorization: `Bearer ${Cookies.get("accessToken")} ${Cookies.get("refreshToken")}`,
			},
			debug: (str) => {
				console.log(str);
			},
			reconnectDelay: 5000,
			heartbeatIncoming: 4000,
			heartbeatOutgoing: 4000,
			onConnect: () => {
				console.log("Connected to chat server");

				// 나를 제외한 다른 사람들의 채팅 수신
				client.subscribe(
					`/topic/chat/${battleBoardId}`,
					(message: IMessage) => {
						const parsedMessage: ChatMessage = JSON.parse(message.body);
						addMessage(parsedMessage);
					},
				);

				// 발언자들 각 개인의 발언권 요청자 리스트 추가
				client.subscribe(
					`/topic/requests/${battleBoardId}`,
					(message: IMessage) => {
						const parsedRequest: SpeechRequestMessage = JSON.parse(
							message.body,
						);
						addSpeechRequest(parsedRequest);
					},
				);
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
	}, [battleBoardId, addMessage, addSpeechRequest]);

	// 라이브 방 모두의 채팅 전송
	const sendMessage = (message: string) => {
		if (stompClient && stompClient.connected) {
			const chatMessage = {
				message,
			};
			stompClient.publish({
				destination: `/app/chat/${battleBoardId}`,
				body: JSON.stringify(chatMessage),
			});
		}
	};

	// 발언자를 제외한 관객들의 발언권 신청
	const sendSpeechRequest = () => {
		if (stompClient && stompClient.connected) {
			stompClient.publish({
				destination: `/app/request/${battleBoardId}`,
				body: JSON.stringify({}),
			});
		}
	};

	return {
		messages,
		speechRequests,
		sendMessage,
		sendSpeechRequest,
	};
};

export default useChatSocket;
