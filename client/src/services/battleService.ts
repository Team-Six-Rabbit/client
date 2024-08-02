import {
	ApiResponse,
	BattleResponse,
	BattleInviteRequest,
	BattleInviteRespondRequest,
	ApplyBattleRequest,
	PageableResponse,
} from "@/types/api";
import axiosInstance from "./axiosInstance";

export const battleService = {
	// 배틀 목록 가져오기
	getBattles: async (
		page: number,
		size: number,
	): Promise<ApiResponse<PageableResponse<BattleResponse[]>>> => {
		try {
			const response = await axiosInstance.get<
				ApiResponse<PageableResponse<BattleResponse[]>>
			>("/battle", {
				params: { page, size },
			});
			return response.data;
		} catch (error) {
			console.error("Failed to fetch battles:", error);
			throw error;
		}
	},

	// 배틀 초대하기
	inviteBattle: async (
		data: BattleInviteRequest,
	): Promise<ApiResponse<string>> => {
		try {
			const response = await axiosInstance.post<ApiResponse<string>>(
				"/battle/invite",
				data,
			);
			return response.data;
		} catch (error) {
			console.error("Failed to invite to battle:", error);
			throw error;
		}
	},

	// 배틀 초대 응답하기
	respondToBattleInvite: async (
		data: BattleInviteRespondRequest,
	): Promise<ApiResponse<string>> => {
		try {
			const response = await axiosInstance.post<ApiResponse<string>>(
				`/battle/${data.respond}`,
				data,
			);
			return response.data;
		} catch (error) {
			console.error(`Failed to ${data.respond} battle invite:`, error);
			throw error;
		}
	},

	// 배틀 신청 목록 가져오기
	getApplyList: async (
		page: number,
		size: number,
		category: number,
	): Promise<ApiResponse<PageableResponse<BattleResponse[]>>> => {
		try {
			const response = await axiosInstance.get<
				ApiResponse<PageableResponse<BattleResponse[]>>
			>("/battle/apply-list", {
				params: { page, size, category },
			});
			return response.data;
		} catch (error) {
			console.error("Failed to fetch apply list:", error);
			throw error;
		}
	},

	// 배틀 신청하기
	applyToBattle: async (
		data: ApplyBattleRequest,
	): Promise<ApiResponse<string>> => {
		try {
			const response = await axiosInstance.post<ApiResponse<string>>(
				"/battle/apply",
				data,
			);
			return response.data;
		} catch (error) {
			console.error("Failed to apply to battle:", error);
			throw error;
		}
	},
};
