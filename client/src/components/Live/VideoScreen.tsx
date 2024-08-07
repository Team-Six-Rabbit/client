import VideoStream from "@/components/WebRTC/VideoStream";
import vsImage from "@/assets/images/LiveVS.png";
import { createLiveStateBorder } from "@/utils/textBorder";
import { StreamManager } from "openvidu-browser";

const borderStyles = createLiveStateBorder("black", 4);

interface VideoPlayerProps {
	userRank: string;
	userName: string;
	streamManager?: StreamManager;
}

function VideoPlayerLeft({
	userRank,
	userName,
	streamManager,
}: VideoPlayerProps) {
	return (
		<div className="w-full h-full flex flex-col items-end">
			<VideoStream
				className="w-4/5 h-full mt-5 clip-path-left bg-white flex justify-center items-center"
				streamManager={streamManager}
			/>
			<div
				className="w-full flex flex-col text-white p-2 ps-14 mt-2"
				style={borderStyles}
			>
				<div className="text-3xl mb-2">{userRank}</div>
				<div className="font-bold text-5xl">{userName}</div>
			</div>
		</div>
	);
}

function VideoPlayerRight({
	userRank,
	userName,
	streamManager,
}: VideoPlayerProps) {
	return (
		<div className="w-full h-full flex flex-col items-start">
			<div
				className="w-full flex flex-col items-end text-white pe-12 mb-4"
				style={borderStyles}
			>
				<div className="text-3xl mb-2">{userRank}</div>
				<div className="font-bold text-5xl">{userName}</div>
			</div>
			<VideoStream
				className="w-5/6 h-full mb-9 clip-path-right bg-white flex justify-center items-center"
				streamManager={streamManager}
			/>
		</div>
	);
}

interface VideoScreenProps {
	subscribers: StreamManager[];
}

function VideoScreen({ subscribers }: VideoScreenProps) {
	return (
		<div className="relative h-70% w-full">
			<div className="h-full w-full bg-[url('@/assets/images/LivePlayers.png')] bg-contain bg-center bg-no-repeat flex">
				<VideoPlayerLeft
					userRank="육두품"
					userName="반반무마니"
					streamManager={subscribers.find(
						(streamManager) =>
							streamManager.stream.connection.serverData?.index === 0,
					)}
				/>
				<VideoPlayerRight
					userRank="사두품"
					userName="마라탕탕후루후루"
					streamManager={subscribers.find(
						(streamManager) =>
							streamManager.stream.connection.serverData?.index === 1,
					)}
				/>
			</div>
			<div className="absolute z-10 w-1/3 top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2">
				<img src={vsImage} alt="VS" />
			</div>
		</div>
	);
}

export default VideoScreen;
