import { useEffect, useState, useCallback, useRef } from "react";
import { useParams } from "react-router-dom";
import ItemBox from "@/components/Live/ItemBox";
import VideoScreen from "@/components/Live/VideoScreen";
import Header from "@/components/header";
import Timer from "@/components/Live/Timer";
import LiveVote from "@/components/Live/LiveVote";
import ChatBox from "@/components/Live/ChatBox";
import EndedLive from "@/components/Live/EndLive";
import useWebRTC from "@/hooks/useWebRTC";

function LivePage() {
	const [winner, setWinner] = useState("");
	const [isTimeOver, setIsTimeOver] = useState(false);
	const [isMicMuted, setIsMicMuted] = useState(true);
	const videoElement = useRef<HTMLVideoElement>(null);
	const canvasElement = useRef<HTMLCanvasElement>(null);
	const [isVideoDisabled, setIsVideoDisabled] = useState(true);
	const { joinSession, subscribers } = useWebRTC(
		isMicMuted,
		isVideoDisabled,
		videoElement,
		canvasElement,
	);
	const { battleId } = useParams();

	const handleMicClick = () => setIsMicMuted((prev) => !prev);
	const handleVideoClick = () => setIsVideoDisabled((prev) => !prev);

	useEffect(() => {
		joinSession(battleId!);
	}, [battleId, joinSession]);

	const onVoteEnd = useCallback((winner: string) => {
		setWinner(winner);
	}, []);

	return (
		<>
			<Header />
			<video ref={videoElement} autoPlay muted className="w-[0px] h-[0px]" />
			<canvas ref={canvasElement} className="w-[0px] h-[0px]" />
			<div className="flex flex-col h-screen">
				<div className="flex-1 flex mt-24 px-8">
					<Timer duration={5220} onTimeOver={() => setIsTimeOver(true)} />
					<div className="flex-col w-full h-144 justify-center items-center">
						<LiveVote
							title="오늘 저녁 메뉴 추천"
							optionA="치킨을 먹자"
							optionB="마라탕을 먹자"
							onVoteEnd={onVoteEnd}
						/>
						<VideoScreen subscribers={subscribers} />
						<ItemBox
							isMicMuted={isMicMuted}
							isVideoDisabled={isVideoDisabled}
							onMicClick={handleMicClick}
							onVideoClick={handleVideoClick}
						/>
					</div>
					<ChatBox battleBoardId="7" role={role} />
				</div>
				{isTimeOver && <EndedLive winner={winner} />}
			</div>
		</>
	);
}

export default LivePage;
