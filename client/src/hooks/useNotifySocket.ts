import { useEffect, useRef } from "react";
import { Client } from "@stomp/stompjs";
import notificationStore from "@/stores/notifyStore";

export const useNotifySocket = (userId: number | undefined | null) => {
	const clientRef = useRef<Client | null>(null); // client를 ref로 선언
	const isSubscribedRef = useRef(false); // 구독 상태를 ref로 유지
	const setNewNotification = notificationStore(
		(state) => state.setNewNotification,
	);

	useEffect(() => {
		if (!userId) {
			// userId가 없으면 소켓 연결을 건너뜁니다.
			return;
		}

		if (isSubscribedRef.current) {
			return;
		}

		const socket = new WebSocket(import.meta.env.VITE_APP_WEBSOCKET_URL);
		const client = new Client({
			webSocketFactory: () => socket,
			debug: (str: unknown) => console.log(str),
			reconnectDelay: 5000,
			heartbeatIncoming: 4000,
			heartbeatOutgoing: 4000,
			onConnect: () => {
				console.log("Connected to notify server");

				// 구독 설정
				const notifySubscription = client.subscribe(`/topic/${userId}`, () => {
					console.log("알림");
					setNewNotification(true);
				});

				isSubscribedRef.current = true; // 구독 상태 업데이트
				clientRef.current = client; // client 인스턴스 저장

				// 구독 해제 및 리소스 해제
				return () => {
					notifySubscription.unsubscribe();
				};
			},
			onStompError: (frame: unknown) => {
				console.error("STOMP error", frame);
			},
		});

		client.activate();

		// 컴포넌트 언마운트 시 클린업
		// eslint-disable-next-line consistent-return
		return () => {
			if (clientRef.current) {
				clientRef.current.deactivate();
			}
		};
	}, [setNewNotification, userId]);

	const deactivateClient = () => {
		if (clientRef.current) {
			clientRef.current.deactivate();
			clientRef.current = null;
		}
	};

	return {
		deactivateClient,
	};
};
