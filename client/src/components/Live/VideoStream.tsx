import React, { useRef, useEffect } from "react";
import useWebRTCStore from "@/stores/WebRTCStore";

interface VideoStreamProps {
	isLocal: boolean;
}

function VideoStream({ isLocal }: VideoStreamProps) {
	const videoRef = useRef<HTMLVideoElement>(null);
	const { localStream, remoteStream } = useWebRTCStore();

	useEffect(() => {
		if (videoRef.current) {
			videoRef.current.srcObject = isLocal ? localStream : remoteStream;
		}
	}, [isLocal, localStream, remoteStream]);

	return <video ref={videoRef} autoPlay playsInline muted={isLocal} />;
}

export default VideoStream;
