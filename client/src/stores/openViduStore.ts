import { create } from "zustand";
import { devtools } from "zustand/middleware";
import { getTokenAndIndex } from "@/services/openViduService";
import { ApiResponse } from "@/types/api";
import { StreamManager } from "openvidu-browser";

export interface OpenViduState {
	sessionId: string | null;
	token: string | null;
	index: number | null;
	loading: boolean;
	error: string | null;
	subscribers: StreamManager[];
}

export interface OpenViduActions {
	initializeSession: (battleId: string) => Promise<void>;
	addSubscriber: (subscriber: StreamManager) => void;
	clearSubscribers: () => void;
}

const openViduStore = create<OpenViduState & OpenViduActions>()(
	devtools((set) => ({
		// 상태 정의
		sessionId: null,
		token: null,
		index: null,
		loading: false,
		error: null,
		subscribers: [],

		// 액션 정의
		initializeSession: async (battleId: string) => {
			set({ loading: true, error: null });
			try {
				const tokenResponse: ApiResponse<{ token: string; index: number }> =
					await getTokenAndIndex(battleId);

				if (!tokenResponse.data) {
					throw new Error("Invalid response data");
				}

				set({
					token: tokenResponse.data.token,
					index: tokenResponse.data.index,
					loading: false,
					error: null,
				});
			} catch (error) {
				set({ loading: false, error: "Failed to initialize session" });
			}
		},
		addSubscriber: (subscriber: StreamManager) =>
			set((state) => ({ subscribers: [...state.subscribers, subscriber] })),
		clearSubscribers: () => set({ subscribers: [] }),
	})),
);

export default openViduStore;
