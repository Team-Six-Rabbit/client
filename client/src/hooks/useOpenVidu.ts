import { useRef, useCallback, useState, useEffect } from "react";
import {
	OpenVidu,
	Publisher,
	Session,
	StreamEvent,
	StreamManager,
} from "openvidu-browser";
import { getTokenAndIndex } from "@/services/openviduService";
import { ServerData } from "@/types/openvidu";

const useOpenVidu = () => {
	const OV = useRef<OpenVidu>();
	const session = useRef<Session>();
	const [publisher, setPublisher] = useState<Publisher>();
	const [isPublisher, setIsPublisher] = useState<boolean>(false);
	const [subscribers, setSubscribers] = useState<StreamManager[]>([]);
	const [index, setIndex] = useState<number>(-1);

	const leaveSession = useCallback(() => {
		if (session) {
			session.current?.disconnect();
			session.current = undefined;
		}
		setPublisher(undefined);
		setIndex(-1);
		setSubscribers([]);
		OV.current = undefined;
	}, [session]);

	useEffect(() => {
		return () => leaveSession();
	}, [leaveSession]);

	const initializeSession = async (battleId: string) => {
		const tokenResponse = await getTokenAndIndex(battleId);

		if (!tokenResponse.data) {
			throw new Error("Invalid response data");
		}

		return tokenResponse.data;
	};

	const parseServerData = (event: StreamEvent) => {
		const serverData: ServerData = JSON.parse(
			event.stream.connection.data.split("%/%").at(-1)!,
		);
		// eslint-disable-next-line no-param-reassign
		event.stream.connection.serverData = serverData;
		return serverData;
	};

	const onStreamCreated = useCallback(
		(event: StreamEvent, publisher?: Publisher) => {
			parseServerData(event);

			const streamManager =
				publisher ?? session.current!.subscribe(event.stream, undefined);

			if (streamManager) {
				setSubscribers((prevSubscribers) => [
					...prevSubscribers,
					streamManager,
				]);
			}
		},
		[],
	);

	const onStreamDestroyed = (event: StreamEvent) => {
		setSubscribers((prevSubscribers) =>
			prevSubscribers.filter(
				(subscriber) => subscriber.stream !== event.stream,
			),
		);
	};

	const joinSession = useCallback(
		async (battleId: string) => {
			OV.current = new OpenVidu();

			const { token, index } = await initializeSession(battleId);
			setIndex(index);

			if (!token) return;

			session.current = OV.current.initSession();

			// 상대방의 스트림을 구독
			session.current.on("streamCreated", onStreamCreated);
			session.current.on("streamDestroyed", onStreamDestroyed);

			await session.current.connect(token);
			setIsPublisher(session.current.capabilities.publish);
		},
		[onStreamCreated],
	);

	const publishMedia = async (
		videoSource?: MediaStreamTrack,
		audioSource?: MediaStreamTrack,
		publishAudio: boolean = true,
		publishVideo: boolean = true,
		frameRate: number = 30,
	) => {
		if (session.current?.capabilities.publish) {
			const pub = OV.current!.initPublisher(undefined, {
				audioSource,
				videoSource,
				publishAudio,
				publishVideo,
				resolution: "640x480",
				frameRate,
				insertMode: "APPEND",
			});
			pub.on("streamCreated", (event) => onStreamCreated(event, pub));
			pub.on("streamDestroyed", onStreamDestroyed);
			setPublisher(pub);
			session.current.publish(pub);
		}
	};

	return {
		joinSession,
		publishMedia,
		session,
		publisher,
		subscribers,
		index,
		isPublisher,
	};
};

export default useOpenVidu;
