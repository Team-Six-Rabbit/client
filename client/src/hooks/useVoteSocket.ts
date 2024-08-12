import { useCallback, useState } from "react";
import { Client } from "@stomp/stompjs";
import { VoteRequest, VoteResponse } from "@/types/liveMessageType";

const useVoteSocket = (stompClient: Client, battleId: string) => {
	const [voteA, setVoteA] = useState<number>(50);
	const [voteB, setVoteB] = useState<number>(50);

	// 투표 응답 처리
	const handleVote = useCallback((data: VoteResponse[]) => {
		setVoteA(data[0].percentage);
		setVoteB(data[1].percentage);
	}, []);

	// 투표 요청 전송
	const sendVote = useCallback(
		(userId: number, voteInfoIndex: number) => {
			if (stompClient && stompClient.connected) {
				const request: VoteRequest = {
					type: "vote",
					data: { userId, voteInfoIndex },
				};
				stompClient.publish({
					destination: `/app/live/${battleId}`,
					body: JSON.stringify(request),
				});
				console.log("Vote 요청 전송됨:", request);
			} else {
				console.error(
					"요청을 보낼 수 없습니다: STOMP 클라이언트가 연결되지 않았습니다.",
				);
			}
		},
		[stompClient, battleId],
	);

	return { voteA, voteB, handleVote, sendVote };
};

export default useVoteSocket;
