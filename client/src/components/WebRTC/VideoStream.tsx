import { useRef, useEffect } from "react";
import { StreamManager } from "openvidu-browser";
import "@/assets/styles/liveLoding.css";

interface VideoStreamProps {
	className: string;
	streamManager: StreamManager | null;
}

function VideoStream({ className, streamManager }: VideoStreamProps) {
	const videoRef = useRef<HTMLVideoElement>(null);

	useEffect(() => {
		if (streamManager && videoRef.current) {
			videoRef.current.innerHTML = "";
			streamManager.addVideoElement(videoRef.current);
		}
	}, [streamManager]);

	return (
		<div className={className}>
			{streamManager ? (
				<video ref={videoRef} autoPlay playsInline>
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
