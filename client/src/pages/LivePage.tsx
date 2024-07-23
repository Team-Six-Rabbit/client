// import VideoPlayer from "@/components/Live/VideoPlayer";
import ChatBox from "@/components/Live/ChatBox";
import VideoPlayer from "@/components/Live/VideoPlayer";

function LivePage() {
	return (
		<div className="flex flex-col h-screen">
			<div className="flex-1 flex justify-between mt-16 p-8">
				<VideoPlayer />
				<ChatBox />
			</div>
		</div>
	);
}

export default LivePage;
