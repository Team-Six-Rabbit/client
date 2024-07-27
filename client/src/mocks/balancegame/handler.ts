import {
	ApiResponse,
	BalanceGameResponse,
	CreateBalanceGameRequest,
	CreateCommentRequest,
	PageableResponse,
} from "@/types/api";
import { http, HttpResponse } from "msw";
import {
	generateBalanceGameResponse,
	generateComment,
	generatePageableResponse,
} from "../util";

export const handlers = [
	http.get<never, never, ApiResponse<PageableResponse<BalanceGameResponse[]>>>(
		"/balance-game",
		async ({ request }) => {
			const qs = new URLSearchParams(request.url);
			const page = Number(qs.get("page") || 1);
			const size = Number(qs.get("size") || 10);
			const category = Number(qs.get("category")?.toString());
			const status = Number(qs.get("status")?.toString());
			const totalElements = 50; // For example, total number of elements
			const balanceGames = Array.from({ length: size }, () =>
				generateBalanceGameResponse(category, status),
			);
			return HttpResponse.json({
				code: "success",
				data: generatePageableResponse(balanceGames, page, size, totalElements),
			});
		},
	),
	http.post<never, CreateBalanceGameRequest, ApiResponse<string>>(
		"/balance-game",
		() => {
			return HttpResponse.json({
				code: "success",
				data: "",
			});
		},
	),
	http.delete<never, never, ApiResponse<string>>("/balance-game", () => {
		return HttpResponse.json({
			code: "success",
			data: "",
		});
	}),
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
