import { useRef, useEffect } from "react";
import { StreamManager } from "openvidu-browser";
import "@/assets/styles/liveLoding.css";

interface VideoStreamProps {
	className: string;
	streamManager?: StreamManager;
}

function VideoStream({ className, streamManager }: VideoStreamProps) {
	const videoRef = useRef<HTMLVideoElement>(null);

	useEffect(() => {
		if (streamManager && videoRef.current) {
			videoRef.current.srcObject = streamManager.stream.getMediaStream();
			videoRef.current.muted = true;
			videoRef.current.play();
			if (!streamManager.stream.isLocal()) videoRef.current.muted = false;
		}
	}, [streamManager]);

	return (
		<div className={className}>
			{streamManager ? (
				<video ref={videoRef} autoPlay playsInline preload="metadata">
					<track kind="captions" srcLang="en" src="captions_en.vtt" default />
				</video>
			) : (
				<div className="loading">
					<span />
					<span />
					<span />
					<span />
					<span />
				</div>
			)}
		</div>
	);
}

export default VideoStream;
