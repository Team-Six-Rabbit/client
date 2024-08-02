import { create } from "zustand";
import { Session, StreamManager } from "openvidu-browser";

interface WebRTCState {
	session: Session | null;
	streams: StreamManager[];
	setSession: (session: Session) => void;
	addStream: (stream: StreamManager) => void;
	removeStream: (stream: StreamManager) => void;
}

const useWebRTCStore = create<WebRTCState>((set) => ({
	session: null,
	streams: [],
	setSession: (session) => set({ session }),
	addStream: (stream) =>
		set((state) => ({ streams: [...state.streams, stream] })),
	removeStream: (stream) =>
		set((state) => ({
			streams: state.streams.filter((s) => s !== stream),
		})),
}));

export default useWebRTCStore;
