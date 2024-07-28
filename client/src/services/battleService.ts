import {
	ApiResponse,
	BattleResponse,
	BattleInviteRequest,
	BattleInviteRespondRequest,
	ApplyBattleRequest,
	PageableResponse,
} from "@/types/api";
import axiosInstance from "./axiosInstance";

export const BattleService = {
	async getBattles(
		page: number,
		size: number,
	): Promise<ApiResponse<PageableResponse<BattleResponse[]>>> {
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

	async inviteBattle(data: BattleInviteRequest): Promise<ApiResponse<string>> {
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

	async respondToBattleInvite(
		data: BattleInviteRespondRequest,
	): Promise<ApiResponse<string>> {
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

	async getApplyList(
		page: number,
		size: number,
		category: number,
	): Promise<ApiResponse<PageableResponse<BattleResponse[]>>> {
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

	async applyToBattle(data: ApplyBattleRequest): Promise<ApiResponse<string>> {
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
