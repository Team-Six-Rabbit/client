import { createBrowserRouter } from "react-router-dom";
import App from "@/App";
import LoginPage from "@/pages/LoginPage";
import SignUpPage from "@/pages/SignUpPage";
import LiveBoardPage from "@/pages/LiveBoardPage";
import NotificationPage from "@/pages/NotificationPage";
import PreVotingBoardPage from "@/pages/PreVotingBoardPage";
import LivePage from "@/pages/LivePage";
import BalanceGameBoardPage from "@/pages/BalanceGameBoardPage";
import BattleRegistPage from "@/pages/BattleRegistPage";
import MyPage from "@/pages/MyPage";
import WinRate from "@/components/tabs/WinRate";
import CreatedLives from "@/components/tabs/CreatedLives";
import ParticipatedVotes from "@/components/tabs/ParticipatedVotes";
import Interests from "@/components/tabs/Interests";

const router = createBrowserRouter([
	{
		path: "/",
		element: <App />,
	},
	{
		path: "/login",
		element: <LoginPage />,
	},
	{
		path: "/join",
		element: <SignUpPage />,
	},
	{
		path: "/firework",
		element: <LiveBoardPage />,
	},
	{
		path: "/notification",
		element: <NotificationPage />,
	},
	{
		path: "/fanning",
		element: <PreVotingBoardPage />,
	},
	{
		path: "/live/:battleId",
		element: <LivePage />,
	},
	{
		path: "/bonfire",
		element: <BalanceGameBoardPage />,
	},
	{
		path: "/ignition",
		element: <BattleRegistPage />,
	},
	{
		path: "/my-page",
		element: <MyPage />,
		children: [
			{
				path: "win-rate",
				element: <WinRate />,
			},
			{
				path: "created-lives",
				element: <CreatedLives />,
			},
			{
				path: "participated-votes",
				element: <ParticipatedVotes />,
			},
			{
				path: "interests",
				element: <Interests />,
			},
		],
	},
]);

export default router;
