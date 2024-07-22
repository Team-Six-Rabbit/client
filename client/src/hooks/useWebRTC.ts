import { useState, useEffect, useCallback } from "react";
import Peer from "simple-peer";
import io, { Socket } from "socket.io-client";
import useWebRTCStore from "@/stores/WebRTCStore";

const useWebRTC = () => {
	const [peer, setPeer] = useState<Peer.Instance | null>(null);
	const [socket, setSocket] = useState<Socket | null>(null);
	const { setLocalStream, setRemoteStream } = useWebRTCStore();

	const initializeWebRTC = useCallback(async () => {
		try {
			const stream = await navigator.mediaDevices.getUserMedia({
				video: true,
				audio: true,
			});
			setLocalStream(stream);

			const newSocket = io("시그널링 서버 url");
			setSocket(newSocket);

			const newPeer = new Peer({ initiator: true, stream });
			setPeer(newPeer);

			newPeer.on("signal", (data) => {
				newSocket.emit("signal", data);
			});

			newPeer.on("stream", (remoteStream) => {
				setRemoteStream(remoteStream);
			});

			newSocket.on("signal", (data) => {
				newPeer.signal(data);
			});
		} catch (error) {
			console.error("WebRTC 초기화 에러:", error);
		}
	}, [setLocalStream, setRemoteStream]);

	useEffect(() => {
		return () => {
			if (peer) {
				peer.destroy();
			}
			if (socket) {
				socket.disconnect();
			}
		};
	}, [peer, socket]);

	return { initializeWebRTC };
};

export default { useWebRTC };
