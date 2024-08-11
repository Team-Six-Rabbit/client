import "./assets/fonts/font.css";
import "./App.css";
import Header from "@/components/header";
import MainPage from "@/pages/MainPage";
import { useAuthStore } from "./stores/userAuthStore";
import { useNotifySocket } from "./hooks/useNotifySocket";

function App() {
	useNotifySocket(useAuthStore().user?.id);
	return (
		<>
			<Header />
			<MainPage />
		</>
	);
}

export default App;
