import { useEffect, useRef } from "react";
import { Client, Message } from "@stomp/stompjs";
import useSpeakSocket from "@/hooks/useSpeakSocket";
import useVoteSocket from "@/hooks/useVoteSocket";
import useItemSocket from "@/hooks/useItemSocket";
import {
	SpeakResponse,
	VoteResponse,
	ItemResponse,
} from "@/types/liveMessageType";

const useLiveSocket = (battleId: string) => {
	const stompClientRef = useRef<Client | null>(null);

	// livePage에서 가져가면 코드가 너무 길어질 것 같아
	// 단순히 import해서 내려주는 역할
	const { speakRequests, handleSpeak, sendSpeak } = useSpeakSocket(
		stompClientRef.current!,
		battleId,
	);
	const { voteA, voteB, handleVote, sendVote } = useVoteSocket(
		stompClientRef.current!,
		battleId,
	);
	const { handleItem, sendItem } = useItemSocket(
		stompClientRef.current!,
		battleId,
	);

	useEffect(() => {
		const socket = new WebSocket(import.meta.env.VITE_APP_WEBSOCKET_URL);
		const client = new Client({
			webSocketFactory: () => socket,
			debug: (str) => console.log(str),
			reconnectDelay: 5000,
			heartbeatIncoming: 4000,
			heartbeatOutgoing: 4000,
			onConnect: () => {
				console.log("서버에 연결되었습니다.");
				client.subscribe(`/topic/live/${battleId}`, (message: Message) => {
					const response = JSON.parse(message.body);

					console.log(response);

					switch (response.type) {
						case "speak":
							handleSpeak(response.responseDto as SpeakResponse);
							break;
						case "vote":
							handleVote(
								(response.responseDto as VoteResponse[]).map(
									({ index, opinion, count, percentage }: VoteResponse) => ({
										index,
										opinion,
										count,
										percentage,
									}),
								),
							);
							break;
						case "item":
							handleItem(response.responseDto as ItemResponse);
							break;
						default:
							console.warn("알 수 없는 메시지 타입:", response.type);
					}
				});
			},
			onStompError: (frame) => {
				console.error("STOMP 오류", frame);
			},
		});

		client.activate();
		stompClientRef.current = client;

		return () => {
			if (stompClientRef.current) {
				stompClientRef.current.deactivate();
				stompClientRef.current = null;
			}
		};
	}, [battleId, handleSpeak, handleVote, handleItem]);

	return {
		speakRequests,
		sendSpeak,
		voteA,
		voteB,
		sendVote,
		sendItem,
	};
};

export default useLiveSocket;
