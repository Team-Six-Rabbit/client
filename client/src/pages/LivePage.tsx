// import VideoPlayer from "@/components/Live/VideoPlayer";
import ChatBox from "@/components/Live/ChatBox";
import VideoScreen from "@/components/Live/VideoScreen";
import Timer from "@/components/Live/Timer";

function LivePage() {
	return (
		<div className="flex flex-col h-screen">
			<div className="flex-1 flex justify-between items-center mt-16 p-8">
				<Timer duration={320} />
				<VideoScreen />
				<ChatBox />
			</div>
		</div>
	);
}

export default LivePage;
