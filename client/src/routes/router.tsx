import { createBrowserRouter } from "react-router-dom";

import App from "@/App";
import LoginPage from "@/pages/LoginPage";
import SignUpPage from "@/pages/SignUpPage";
import LiveBoardPage from "@/pages/LiveBoardPage";
import NotificationPage from "@/pages/NotificationPage";
import PreVotingBoardPage from "@/pages/PreVotingBoardPage";
import LivePage from "@/pages/LivePage";
import MyPage from "@/pages/MyPage";
import CreatedLives from "@/components/tabs/CreatedLives";
import Interests from "@/components/tabs/Interests";
import ParticipatedVotes from "@/components/tabs/ParticipatedVotes";
import WinRate from "@/components/tabs/WinRate";

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
		path: "/live",
		element: <LivePage />,
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
