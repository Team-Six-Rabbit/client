import { createLiveStateBorder } from "@/utils/textBorder";
import vsImage from "@/assets/images/LiveVS.png";

interface VideoPlayerProps {
	userRank: string;
	userName: string;
	videoSrc: string;
}

const borderStyles = createLiveStateBorder("black", 4);

function VideoPlayerLeft({ userRank, userName, videoSrc }: VideoPlayerProps) {
	return (
		<div className="w-full h-full flex flex-col items-end">
			<video
				className="w-5/6 h-2/3 mt-6 object-cover rounded-lg clip-path-left"
				controls
				src={videoSrc}
			>
				<track kind="captions" srcLang="en" src="captions_en.vtt" default />
			</video>
			<div
				className="w-full flex flex-col items-start text-white p-2 mt-2 ps-16"
				style={borderStyles}
			>
				<div className="text-3xl mb-2">{userRank}</div>
				<div className="font-bold text-5xl">{userName}</div>
			</div>
		</div>
	);
}

function VideoPlayerRight({ userRank, userName, videoSrc }: VideoPlayerProps) {
	return (
		<div className="w-full h-full flex flex-col items-start">
			<div
				className="w-full flex flex-col items-end text-white pe-12"
				style={borderStyles}
			>
				<div className="text-3xl mb-2">{userRank}</div>
				<div className="font-bold text-5xl">{userName}</div>
			</div>
			<video
				className="w-5/6 h-2/3 mt-4 ms-4 object-cover rounded-lg clip-path-right"
				controls
				src={videoSrc}
			>
				<track kind="captions" srcLang="en" src="captions_en.vtt" default />
			</video>
		</div>
	);
}

function VideoScreen() {
	return (
		<div className="relative h-70% w-full me-1">
			<div className="h-full w-full bg-[url('@/assets/images/LivePlayers.png')] bg-contain bg-center bg-no-repeat flex justify-center">
				<VideoPlayerLeft
					userRank="육두품"
					userName="반반무마니"
					videoSrc="path-to-user-a-video.mp4"
				/>
				<VideoPlayerRight
					userRank="사두품"
					userName="마라탕탕후루후루"
					videoSrc="path-to-user-b-video.mp4"
				/>
			</div>
			<div className="absolute z-10 w-1/3 top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2">
				<img src={`${vsImage}`} alt="VS" />
			</div>
		</div>
	);
}

export default VideoScreen;
