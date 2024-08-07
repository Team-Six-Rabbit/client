import { useEffect, useState, useCallback } from "react";
import { useParams } from "react-router-dom";
import ItemBox from "@/components/Live/ItemBox";
import useOpenVidu from "@/hooks/useOpenVidu";
import VideoScreen from "@/components/Live/VideoScreen";
import Header from "@/components/header";
import Timer from "@/components/Live/Timer";
import LiveVote from "@/components/Live/LiveVote";
import ChatBox from "@/components/Live/ChatBox";
import EndedLive from "@/components/Live/EndLive";

function LivePage() {
	const [winner, setWinner] = useState("");
	const [isTimeOver, setIsTimeOver] = useState(false);
	const [isMicMuted, setIsMicMuted] = useState(true);
	const [isVideoDisabled, setIsVideoDisabled] = useState(true);
	const { joinSession, publisher, subscribers } = useOpenVidu();
	const { battleId } = useParams();

	const handleMicClick = useCallback(() => {
		setIsMicMuted((prev) => !prev);
	}, []);

	const handleVideoClick = useCallback(() => {
		setIsVideoDisabled((prev) => !prev);
	}, []);

	useEffect(() => {
		if (battleId) joinSession(battleId);
	}, [battleId, joinSession]);

	useEffect(() => {
		if (publisher) {
			publisher.publishAudio(!isMicMuted);
			publisher.publishVideo(!isVideoDisabled);
		}
	}, [isMicMuted, isVideoDisabled, publisher]);

	const onVoteEnd = useCallback((winner: string) => {
		setWinner(winner);
	}, []);

	return (
		<>
			<Header />
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
					<ChatBox />
				</div>
				{isTimeOver && <EndedLive winner={winner} />}
			</div>
		</>
	);
}

export default LivePage;
