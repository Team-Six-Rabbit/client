import axiosInstance from "@/services/axiosInstance";
import { ApiResponse } from "@/types/api";

export const createSession = async (
	battleId: string,
): Promise<ApiResponse<string>> => {
	try {
		const response = await axiosInstance.post<ApiResponse<string>>(
			`/openvidu/create-session?battleId=${battleId}`,
		);
		return response.data; // roomId를 string으로 받음
	} catch (error) {
		console.error("Failed to create session:", error);
		throw error;
	}
};

export const getTokenAndIndex = async (
	battleId: string,
): Promise<ApiResponse<{ token: string; index: number }>> => {
	try {
		const response = await axiosInstance.post<
			ApiResponse<{ token: string; index: number }>
		>(`/openvidu/get-token?battleId=${battleId}`);
		return response.data; // 서버에서 토큰과 역할 index(0=개최자, 1=대적자, -1=관객) 받아옴
	} catch (error) {
		console.error("Failed to get token:", error);
		throw error;
	}
};
