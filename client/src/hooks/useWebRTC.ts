import { RefObject, useEffect } from "react";
import useFaceApi from "./useFaceApi";
import useOpenVidu from "./useOpenVidu";

const publishFrameRate = 30;

const useWebRTC = (
	isMicMuted: boolean,
	isVideoDisabled: boolean,
	video: RefObject<HTMLVideoElement>,
	canvas: RefObject<HTMLCanvasElement>,
) => {
	const {
		joinSession,
		publishMedia,
		unpublishMedia,
		index,
		connectionId,
		publisher,
		subscribers,
		isPublisher,
		shouldPublish,
	} = useOpenVidu();
	const { isReady, shouldRenderVideo } = useFaceApi(isPublisher, video, canvas);

	useEffect(() => {
		if (!shouldPublish) {
			unpublishMedia();
			return;
		}
		if (!isReady) return;

		const mediaStreamTrack = canvas
			.current!.captureStream(publishFrameRate)
			.getVideoTracks()
			.at(0)!;
		if (!mediaStreamTrack) throw new Error("NOMEDIA");
		mediaStreamTrack.contentHint = "motion";
		shouldRenderVideo.current = true;

		publishMedia(
			mediaStreamTrack,
			undefined,
			!isMicMuted,
			!isVideoDisabled,
			publishFrameRate,
		);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [isReady, shouldPublish]);

	useEffect(() => {
		if (publisher && isReady) {
			publisher.publishAudio(!isMicMuted);
			publisher.publishVideo(!isVideoDisabled);
		}
	}, [isMicMuted, isReady, isVideoDisabled, publisher]);

	return { joinSession, index, subscribers, connectionId };
};

export default useWebRTC;
