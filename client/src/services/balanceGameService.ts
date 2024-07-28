import {
	ApiResponse,
	BalanceGameResponse,
	CreateBalanceGameRequest,
	PageableResponse,
	CreateCommentRequest,
} from "@/types/api";
import axiosInstance from "./axiosInstance";

export const BalanceGameService = {
	async getBalanceGames(
		page: number,
		size: number,
		category: number,
		status: number,
	): Promise<ApiResponse<PageableResponse<BalanceGameResponse[]>>> {
		try {
			const response = await axiosInstance.get<
				ApiResponse<PageableResponse<BalanceGameResponse[]>>
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

	async getComments(
		page: number,
		size: number,
		id: number,
	): Promise<ApiResponse<PageableResponse<Comment[]>>> {
		try {
			const response = await axiosInstance.get<
				ApiResponse<PageableResponse<Comment[]>>
			>("/balance-game/comment", {
				params: { page, size, id },
			});
			return response.data;
		} catch (error) {
			console.error("Failed to fetch comments:", error);
			throw error;
		}
	},

	async createComment(
		data: CreateCommentRequest,
	): Promise<ApiResponse<string>> {
		try {
			const response = await axiosInstance.post<ApiResponse<string>>(
				"/balance-game/comment",
				data,
			);
			return response.data;
		} catch (error) {
			console.error("Failed to create comment:", error);
			throw error;
		}
	},
};
