// import VideoPlayer from "@/components/Live/VideoPlayer";
import { useState } from "react";
import ChatBox from "@/components/Live/ChatBox";
import VideoScreen from "@/components/Live/VideoScreen";
import Timer from "@/components/Live/Timer";
import LiveVote from "@/components/Live/LiveVote";
import ItemBox from "@/components/Live/ItemBox";
import EndedLive from "@/components/Live/EndLive";
import Header from "@/components/header";

function LivePage() {
	const [winner, setWinner] = useState("");
	const [isTimeOver, setIsTimeOver] = useState(false);

	const onVoteEnd = (winner: string) => {
		setWinner(winner);
	};

	return (
		<>
			<Header />
			<div className="flex flex-col h-screen">
				<div className="flex-1 flex mt-16 p-8">
					<Timer duration={1} onTimeOver={() => setIsTimeOver(true)} />
					<div className="flex-col justify-center items-center h-144">
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
