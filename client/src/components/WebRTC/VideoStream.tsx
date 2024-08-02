// src/components/WebRTC/VideoStream.tsx
import { useEffect, useRef } from "react";
import useWebRTCStore from "@/stores/WebRTCStore";

interface VideoStreamProps {
	className: string;
}

function VideoStream({ className }: VideoStreamProps) {
	const videoRef = useRef<HTMLVideoElement>(null);
	const { streams } = useWebRTCStore();

	useEffect(() => {
		if (streams.length > 0 && videoRef.current) {
			streams[0].addVideoElement(videoRef.current);
		}
	}, [streams]);

	return (
		<div className={className}>
			<video ref={videoRef} autoPlay playsInline>
				<track kind="captions" srcLang="en" src="captions_en.vtt" default />
			</video>
		</div>
	);
}

export default VideoStream;
