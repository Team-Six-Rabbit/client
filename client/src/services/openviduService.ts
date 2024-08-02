import axiosInstance from "@/services/axiosInstance";
import { ApiResponse } from "@/types/api";

export const createSession = async (
	battleId: string,
): Promise<ApiResponse<string>> => {
	try {
		const response = await axiosInstance.post<ApiResponse<string>>(
			`/openvidu/create-session?battleId=${battleId}`,
			{ withCredentials: true },
		);
		return response.data; // roomId를 string으로 받음
	} catch (error) {
		console.error("Failed to create session:", error);
		throw error;
	}
};

export const getToken = async (
	roomId: string,
	role: string,
	userId: string,
): Promise<ApiResponse<string>> => {
	try {
		const response = await axiosInstance.post<ApiResponse<string>>(
			`/openvidu/get-token?roomId=${roomId}&role=${role}&userId=${userId}`,
			{ withCredentials: true },
		);
		return response.data; // 서버에서 토큰을 받아옴
	} catch (error) {
		console.error("Failed to get token:", error);
		throw error;
	}
};
