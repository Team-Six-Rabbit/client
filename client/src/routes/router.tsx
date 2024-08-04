import { createBrowserRouter } from "react-router-dom";

import App from "@/App";
import LoginPage from "@/pages/LoginPage";
import SignUpPage from "@/pages/SignUpPage";
import LiveBoardPage from "@/pages/LiveBoardPage";
import NotificationPage from "@/pages/NotificationPage";
import PreVotingBoardPage from "@/pages/PreVotingBoardPage";
import LivePage from "@/pages/LivePage";
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
import BalanceGameBoardPage from "@/pages/BalanceGameBoardPage";
=======
>>>>>>> 6b657da (Feat: 마이페이지 구현)
=======
import MyPage from "@/pages/MyPage";
import CreatedLives from "@/components/tabs/CreatedLives";
import Interests from "@/components/tabs/Interests";
import ParticipatedVotes from "@/components/tabs/ParticipatedVotes";
import WinRate from "@/components/tabs/WinRate";
<<<<<<< HEAD
>>>>>>> 1ccc20e (Feat: 마이페이지 구현)
=======
>>>>>>> 7ec1267 (Chore: 로그인 회원가입 push)
=======
>>>>>>> a9a2a58 (Feat: 마이페이지 구현)
>>>>>>> 6b657da (Feat: 마이페이지 구현)

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
<<<<<<< HEAD
<<<<<<< HEAD
	{
<<<<<<< HEAD
		path: "/bonfire",
		element: <BalanceGameBoardPage />,
=======
=======
=======
	{
>>>>>>> 6b657da (Feat: 마이페이지 구현)
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
<<<<<<< HEAD
>>>>>>> 1ccc20e (Feat: 마이페이지 구현)
	},
=======
>>>>>>> 7ec1267 (Chore: 로그인 회원가입 push)
=======
	},
>>>>>>> a9a2a58 (Feat: 마이페이지 구현)
>>>>>>> 6b657da (Feat: 마이페이지 구현)
]);

export default router;
