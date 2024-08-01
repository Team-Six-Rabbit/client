import {
	ApiResponse,
	BalanceGameResponse,
	CreateBalanceGameRequest,
} from "@/types/api";
import { http, HttpResponse } from "msw";
import { generateBalanceGameResponse } from "../util";

export const handlers = [
	http.get<never, never, ApiResponse<BalanceGameResponse[]>>(
		"/balance-game",
		async ({ request }) => {
			const qs = new URLSearchParams(request.url);
			const size = Number(qs.get("size") || 10);
			const category = Number(qs.get("category")?.toString());
			const status = Number(qs.get("status")?.toString());
			const balanceGames = Array.from({ length: size }, () =>
				generateBalanceGameResponse(category, status),
			);
			return HttpResponse.json({
				code: "success",
				data: balanceGames,
			});
		},
	),
	http.post<never, CreateBalanceGameRequest, ApiResponse<string>>(
		"/balance-game",
		() => {
			return HttpResponse.json({
				code: "success",
				msg: "",
				data: undefined,
			});
		},
	),
	http.delete<never, never, ApiResponse<string>>("/balance-game", () => {
		return HttpResponse.json({
			code: "success",
			data: "",
		});
	}),
	http.get<{ id: string }, never, ApiResponse<BalanceGameResponse>>(
		"/balance-game/:id",
		({ params }) => {
			return HttpResponse.json({
				code: "success",
				msg: undefined,
				data: generateBalanceGameResponse(0, 5, Number(params.id)),
			});
		},
	),
];
