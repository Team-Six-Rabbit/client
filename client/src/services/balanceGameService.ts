import {
	ApiResponse,
	BalanceGameResponse,
	CreateBalanceGameRequest,
} from "@/types/api";
import axiosInstance from "./axiosInstance";

export async function getBalanceGames(
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
}

export async function createBalanceGame(
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
}

export async function deleteBalanceGame(): Promise<ApiResponse<string>> {
	try {
		const response =
			await axiosInstance.delete<ApiResponse<string>>("/balance-game");
		return response.data;
	} catch (error) {
		console.error("Failed to delete balance game:", error);
		throw error;
	}
}
