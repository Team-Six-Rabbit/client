import { createBrowserRouter } from "react-router-dom";

import App from "@/App";
import LoginPage from "@/pages/LoginPage";
import SignUpPage from "@/pages/SignUpPage";
import LiveBoardPage from "@/pages/LiveBoardPage";
import PreVotingBoardPage from "@/pages/PreVotingBoardPage";

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
		path: "/fanning",
		element: <PreVotingBoardPage />,
	},
]);

export default router;
