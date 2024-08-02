import { useEffect, useState } from "react";
import ChatBox from "@/components/Live/ChatBox";
import VideoScreen from "@/components/Live/VideoScreen";
import Timer from "@/components/Live/Timer";
import LiveVote from "@/components/Live/LiveVote";
import ItemBox from "@/components/Live/ItemBox";
import EndedLive from "@/components/Live/EndLive";
import Header from "@/components/header";
import useWebRTC from "@/hooks/useWebRTC";

function LivePage() {
	const [winner, setWinner] = useState("");
	const [isTimeOver, setIsTimeOver] = useState(false);

	// 예시로 사용할 아이디 값
	const battleId = "1";
	const role = "viewer";
	const userId = "1";

	const initWebRTC = useWebRTC(battleId, role, userId);

	useEffect(() => {
		initWebRTC();
	}, [initWebRTC]);

	const onVoteEnd = (winner: string) => {
		setWinner(winner);
	};

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
						<VideoScreen />
						<ItemBox />
					</div>
					<ChatBox />
				</div>
				{isTimeOver && <EndedLive winner={winner} />}
			</div>
		</>
	);
}

export default LivePage;
