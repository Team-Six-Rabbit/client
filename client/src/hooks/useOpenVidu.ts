import { useEffect, useRef, useCallback } from "react";
import { OpenVidu, Publisher, Session, StreamEvent } from "openvidu-browser";
import useOpenViduStore, {
	OpenViduState,
	OpenViduActions,
} from "@/stores/openViduStore";

const useOpenVidu = () => {
	const { token, index, initializeSession, addSubscriber, subscribers } =
		useOpenViduStore((state: OpenViduState & OpenViduActions) => ({
			sessionId: state.sessionId,
			token: state.token,
			index: state.index,
			subscribers: state.subscribers,
			initializeSession: state.initializeSession,
			addSubscriber: state.addSubscriber,
			clearSubscribers: state.clearSubscribers,
		}));

	const OV = useRef<OpenVidu | null>(null);
	const session = useRef<Session | null>(null);
	const publisher = useRef<Publisher | null>(null);

	useEffect(() => {
		OV.current = new OpenVidu();
		return () => {
			if (session.current) {
				session.current.disconnect();
			}
		};
	}, []);

	const joinSession = useCallback(
		async (battleId: string) => {
			await initializeSession(battleId);

			if (token && OV.current) {
				session.current = OV.current.initSession();

				// 상대방의 스트림을 구독
				session.current.on("streamCreated", (event: StreamEvent) => {
					const subscriberInstance = session.current?.subscribe(
						event.stream,
						undefined,
					);
					if (subscriberInstance) {
						addSubscriber(subscriberInstance);
					}
				});

				await session.current.connect(token, { clientData: `User${index}` });

				// 자신의 스트림을 방송
				if (index === 0 || index === 1) {
					publisher.current = OV.current.initPublisher(undefined, {
						audioSource: undefined,
						videoSource: undefined,
						publishAudio: true,
						publishVideo: true,
						resolution: "640x480",
						frameRate: 30,
						insertMode: "APPEND",
					});

					session.current.publish(publisher.current);
				}
			}
		},
		[initializeSession, token, index, addSubscriber],
	);

	return { joinSession, session, publisher, subscribers, index };
};

export default useOpenVidu;
