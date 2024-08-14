import axiosInstance from "@/services/axiosInstance";
import { ApiResponse } from "@/types/api";

export const getUserVoteOpinion = async (
	battleId: string,
): Promise<ApiResponse<number>> => {
	try {
		const response = await axiosInstance.get<ApiResponse<number>>(
			`/vote/live-user-vote-opinion/${battleId}`,
		);
		return response.data; // 예상되는 반환값이 0, 1, -1 중 하나일 경우
	} catch (error) {
		console.error("Failed to fetch user vote opinion:", error);
		throw error; // 필요한 경우 에러를 상위로 전달
	}
};

export const getIsUserRequest = async (
	battleId: string,
): Promise<ApiResponse<number>> => {
	try {
		const response = await axiosInstance.get<ApiResponse<number>>(
			`/openvidu/is-user-speak-request/${battleId}`,
		);
		return response.data; // 예상되는 반환값이 0, 1, -1 중 하나일 경우
	} catch (error) {
		console.error("Failed to fetch user vote opinion:", error);
		throw error; // 필요한 경우 에러를 상위로 전달
	}
};
