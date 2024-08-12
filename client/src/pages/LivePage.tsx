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

import useChatSocket from "@/hooks/useChatSocket";
import { useAuthStore } from "@/stores/userAuthStore";

function LivePage() {
	const videoElement = useRef<HTMLVideoElement>(null);
	const canvasElement = useRef<HTMLCanvasElement>(null);

	const [winner, setWinner] = useState("");
	const [isTimeOver, setIsTimeOver] = useState(false);
	const [isMicMuted, setIsMicMuted] = useState(true);
	const [isVideoDisabled, setIsVideoDisabled] = useState(true);

	const { joinSession, subscribers, index } = useWebRTC(
		isMicMuted,
		isVideoDisabled,
		videoElement,
		canvasElement,
	);

	const { battleId } = useParams();
	const { messages, sendMessage } = useChatSocket(battleId!);
	const userId = useAuthStore().user?.id;

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
				<div className="flex-1 flex mt-16 px-8 pt-8">
					{/* 추후에 start와 end시간을 계산해서 duration에 넣기 */}
					<Timer duration={520} onTimeOver={() => setIsTimeOver(true)} />
					<div className="flex-col w-full justify-center items-center h-144">
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
					<ChatBox
						messages={messages}
						sendMessage={sendMessage}
						role={index}
						userId={userId!}
					/>
				</div>
				{isTimeOver && <EndedLive winner={winner} />}
			</div>
		</>
	);
}

export default LivePage;
