import { create } from "zustand";

interface WebRTCState {
	localStream: MediaStream | null;
	remoteStream: MediaStream | null;
}

interface WebRTCAction {
	setLocalStream: (stream: MediaStream) => void;
	setRemoteStream: (stream: MediaStream) => void;
}

const useWebRTCStore = create<WebRTCState & WebRTCAction>((set) => ({
	localStream: null,
	remoteStream: null,
	setLocalStream: (stream) => set({ localStream: stream }),
	setRemoteStream: (stream) => set({ remoteStream: stream }),
}));

export default useWebRTCStore;
