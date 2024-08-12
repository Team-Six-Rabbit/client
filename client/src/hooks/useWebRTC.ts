import { useEffect } from "react";
import useOpenVidu from "./useOpenVidu";

const publishFrameRate = 30;

const useWebRTC = (
	isMicMuted: boolean,
	isVideoDisabled: boolean,
	// video: RefObject<HTMLVideoElement>,
	// canvas: RefObject<HTMLCanvasElement>,
) => {
	const {
		joinSession,
		publishMedia,
		unpublishMedia,
		index,
		connectionId,
		subscribers,
		// isPublisher,
		shouldPublish,
	} = useOpenVidu();
	// const { drawMask, shouldRenderVideo, shouldRenderMask } = useFaceApi(
	// 	isPublisher,
	// 	video,
	// 	canvas,
	// );

	useEffect(() => {
		if (!shouldPublish) {
			unpublishMedia();
			return;
		}
		// if (!isReady) return;

		// const mediaStreamTrack = canvas
		// 	.current!.captureStream(publishFrameRate)
		// 	.getVideoTracks()
		// 	.at(0)!;
		// if (!mediaStreamTrack) throw new Error("NOMEDIA");
		// mediaStreamTrack.contentHint = "motion";
		// shouldRenderVideo.current = true;

		publishMedia(
			undefined,
			undefined,
			!isMicMuted,
			!isVideoDisabled,
			publishFrameRate,
		);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [shouldPublish]);

	return {
		joinSession,
		// drawMask,
		index,
		subscribers,
		connectionId,
		// shouldRenderVideo,
		// shouldRenderMask,
	};
};

export default useWebRTC;
