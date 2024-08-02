import { useCallback } from "react";
import { OpenVidu, Session, StreamManager } from "openvidu-browser";
import useWebRTCStore from "@/stores/WebRTCStore";
import { createSession, getToken } from "@/services/openviduService";

const useWebRTC = (battleId: string, role: string, userId: string) => {
	const { setSession, addStream, removeStream } = useWebRTCStore();

	const initWebRTC = useCallback(async () => {
		const OV = new OpenVidu();
		const session: Session = OV.initSession();

		session.on("streamCreated", (event) => {
			const subscriber: StreamManager = session.subscribe(
				event.stream,
				undefined,
			);
			addStream(subscriber);
		});

		session.on("streamDestroyed", (event) => {
			removeStream(event.stream.streamManager);
		});

		setSession(session);

		try {
			const createResponse = await createSession(battleId);
			if (createResponse.code === "success") {
				const tokenResponse = await getToken(
					createResponse.data!,
					role,
					userId,
				);
				if (tokenResponse.code === "success") {
					await session.connect(tokenResponse.data!, { clientData: "User" });

					const publisher: StreamManager = OV.initPublisher(undefined, {
						audioSource: undefined,
						videoSource: undefined,
						publishAudio: true,
						publishVideo: true,
						resolution: "640x480",
						frameRate: 30,
						insertMode: "APPEND",
					});

					session.publish(publisher);
				} else {
					throw new Error("Failed to get token");
				}
			} else {
				console.log(createResponse.code);
				throw new Error("Failed to create session");
			}
		} catch (error) {
			console.error("There was an error connecting to the session:", error);
		}
	}, [battleId, role, userId, setSession, addStream, removeStream]);

	return initWebRTC;
};

export default useWebRTC;
