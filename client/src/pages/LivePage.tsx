import { useEffect, useState, useCallback } from "react";
import { useNavigate, useParams } from "react-router-dom";
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
import SpeakRequestList from "@/components/Live/SpeakRequestList";
import useLiveSocket from "@/hooks/useLiveSocket";
import useRequireAuth from "@/hooks/useRequireAuth";
import { liveBattleService } from "@/services/liveBattleService";
import { WaitingLiveBattleDetail } from "@/types/live";
import { toast } from "react-toastify";

function LivePage() {
	const { isLogin } = useRequireAuth();
	const navigate = useNavigate();

	if (isLogin) {
		navigator.mediaDevices.getUserMedia({ audio: true }).then((stream) => {
			setTimeout(() => {
				stream.getTracks().forEach((track) => track.stop());
			}, 10);
		});
	}
	// const videoElement = useRef<HTMLVideoElement>(null);
	// const canvasElement = useRef<HTMLCanvasElement>(null);

	const [winner, setWinner] = useState("");
	const [isTimeOver, setIsTimeOver] = useState(false);
	const [isMicMuted, setIsMicMuted] = useState(false);
	const [isVideoDisabled, setIsVideoDisabled] = useState(false);
	const [liveData, setLiveData] = useState<WaitingLiveBattleDetail>();

	const { joinSession, subscribers, index, connectionId, publisher } =
		useWebRTC(
			isMicMuted,
			isVideoDisabled,
			// videoElement,
			// canvasElement,
		);

	const { battleId } = useParams();
	const { messages, sendMessage } = useChatSocket(battleId!);
	const { speakRequests, sendSpeak, voteA, voteB, sendVote, sendItem } =
		useLiveSocket(battleId!);
	const userId = useAuthStore().user?.id;

	const handleMicClick = () => setIsMicMuted((prev) => !prev);
	const handleVideoClick = () => setIsVideoDisabled((prev) => !prev);

	useEffect(() => {
		joinSession(battleId!);
	}, [battleId, joinSession]);

	useEffect(() => {
		const fetchData = async () => {
			const apiData = await liveBattleService
				.getWaitDetail(battleId!)
				.catch(() => ({ code: "fail", data: undefined }));
			if (apiData.code === "fail" || !apiData.data) {
				toast.error("라이브 데이터를 가져오지 못했습니다.");
				navigate("/");
				return;
			}

			setLiveData(apiData.data);
		};

		fetchData();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [battleId]);

	const onVoteEnd = useCallback((winner: string) => {
		setWinner(winner);
	}, []);

	return (
		<>
			<Header />
			{/* <video
				ref={videoElement}
				onLoadedMetadata={drawMask}
				autoPlay
				muted
				className="invisible fixed w-[640px] h-[480px]"
			/>
			<canvas
				ref={canvasElement}
				className="invisible fixed w-[640px] h-[480px]"
			/> */}
			<div className="flex flex-col h-screen">
				<div className="flex-1 flex mt-16 px-8 pt-8">
					{/* 추후에 start와 end시간을 계산해서 duration에 넣기 */}
					<Timer duration={520} onTimeOver={() => setIsTimeOver(true)} />
					<div className="flex-col w-full justify-center items-center h-144">
						<LiveVote
							userId={userId!}
							voteA={voteA}
							voteB={voteB}
							title={liveData?.title || "오늘 저녁 메뉴 추천"}
							optionA={liveData?.registerUser.opinion || "치킨을 먹자"}
							optionB={liveData?.oppositeUser.opinion || "마라탕을 먹자"}
							sendVote={sendVote}
							onVoteEnd={onVoteEnd}
						/>
						<VideoScreen
							subscribers={subscribers}
							registerUser={liveData?.registerUser}
							oppositeUser={liveData?.oppositeUser}
						/>
						<ItemBox
							isMicMuted={isMicMuted}
							isVideoDisabled={isVideoDisabled}
							canUseItem={
								publisher?.stream.connection.serverData?.role === "SPEAKER" ||
								false
							}
							onMicClick={handleMicClick}
							onVideoClick={handleVideoClick}
							sendItem={sendItem}
						/>
					</div>
					<div className="flex flex-col h-150 w-1/4 ms-6 mt-2">
						<SpeakRequestList
							userId={userId!}
							connectionId={connectionId.current}
							speakRequests={speakRequests}
							sendSpeak={sendSpeak}
							role={index} // 지금 나의 역할(0,1,null) 추후에 OpenVidu로 받을 예정
						/>
						<ChatBox
							messages={messages}
							sendMessage={sendMessage}
							userId={userId!}
						/>
					</div>
				</div>
				{isTimeOver && <EndedLive winner={winner} />}
			</div>
		</>
	);
}

export default LivePage;
