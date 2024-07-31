import {
	ApiResponse,
	BalanceGameResponse,
	CreateBalanceGameRequest,
	CreateCommentRequest,
} from "@/types/api";
import { http, HttpResponse } from "msw";
import {
	generateBalanceGameResponse,
	generateComment,
	generatePageableResponse,
} from "../util";

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
	http.get("/balance-game/comment", async ({ request }) => {
		const qs = new URLSearchParams(request.url);
		const page = Number(qs.get("page") || 1);
		const size = Number(qs.get("size") || 10);
		const id = Number(qs.get("id")?.toString());
		const totalElements = 50; // For example, total number of elements
		const comments = Array.from({ length: size }, () => generateComment(id));
		return HttpResponse.json({
			code: "success",
			data: generatePageableResponse(comments, page, size, totalElements),
		});
	}),
	http.post<never, CreateCommentRequest, ApiResponse<string>>(
		"/balance-game/comment",
		() => {
			return HttpResponse.json({
				code: "success",
				data: "",
			});
		},
	),
];
