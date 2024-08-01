import {
	ApiResponse,
	BalanceGameResponse,
	CreateBalanceGameRequest,
} from "@/types/api";
import axiosInstance from "./axiosInstance";

export const BalanceGameService = {
	async getBalanceGames(
		page: number,
		size: number,
		category: number,
		status: number,
	): Promise<ApiResponse<BalanceGameResponse[]>> {
		try {
			const response = await axiosInstance.get<
				ApiResponse<BalanceGameResponse[]>
			>("/balance-game", {
				params: { page, size, category, status },
			});
			return response.data;
		} catch (error) {
			console.error("Failed to fetch balance games:", error);
			throw error;
		}
	},

	async createBalanceGame(
		data: CreateBalanceGameRequest,
	): Promise<ApiResponse<string>> {
		try {
			const response = await axiosInstance.post<ApiResponse<string>>(
				"/balance-game",
				data,
			);
			return response.data;
		} catch (error) {
			console.error("Failed to create balance game:", error);
			throw error;
		}
	},

	async deleteBalanceGame(): Promise<ApiResponse<string>> {
		try {
			const response =
				await axiosInstance.delete<ApiResponse<string>>("/balance-game");
			return response.data;
		} catch (error) {
			console.error("Failed to delete balance game:", error);
			throw error;
		}
	},
};
