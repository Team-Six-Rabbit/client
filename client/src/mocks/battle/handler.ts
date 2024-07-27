import { http, HttpResponse } from "msw";

import {
	ApiResponse,
	ApplyBattleRequest,
	BattleInviteRequest,
	BattleInviteRespondRequest,
	BattleResponse,
	PageableResponse,
} from "@/types/api";
import { generateBattleResponse, generatePageableResponse } from "@/mocks/util";

export const handlers = [
	http.get<never, never, ApiResponse<PageableResponse<BattleResponse[]>>>(
		"/battle",
		async ({ request }) => {
			const qs = new URLSearchParams(request.url);
			const page = Number(qs.get("page") || 1);
			const size = Number(qs.get("size") || 10);
			const totalElements = 50; // For example, total number of elements
			const battles = Array.from({ length: size }, (_, index) =>
				generateBattleResponse((page - 1) * size + index + 1),
			);
			return HttpResponse.json({
				code: "success",
				data: generatePageableResponse(battles, page, size, totalElements),
			});
		},
	),
	http.post<never, BattleInviteRequest, ApiResponse<string>>(
		"/battle/invite",
		() => {
			return HttpResponse.json({
				code: "success",
				data: "",
			});
		},
	),
	http.post<never, BattleInviteRespondRequest, ApiResponse<string>>(
		"/battle/accept",
		() => {
			return HttpResponse.json({
				code: "success",
				data: "",
			});
		},
	),
	http.post<never, BattleInviteRespondRequest, ApiResponse<string>>(
		"/battle/decline",
		() => {
			return HttpResponse.json({
				code: "success",
				data: "",
			});
		},
	),
	http.get<never, never, ApiResponse<PageableResponse<BattleResponse[]>>>(
		"/battle/apply-list",
		async ({ request }) => {
			const qs = new URLSearchParams(request.url);
			const page = Number(qs.get("page") || 1);
			const size = Number(qs.get("size") || 10);
			const category = Number(qs.get("category")?.toString());
			const totalElements = 50; // For example, total number of elements
			const battles = Array.from({ length: size }, (_, index) =>
				generateBattleResponse((page - 1) * size + index + 1, category),
			);
			return HttpResponse.json({
				code: "success",
				data: generatePageableResponse(battles, page, size, totalElements),
			});
		},
	),
	// http.get<
	// 	// eslint-disable-next-line @typescript-eslint/ban-ts-comment
	// 	// @ts-ignore
	// 	PageableParm & { battleBoardId: number },
	// 	undefined,
	// 	ApiResponse<PageableResponse<BasicUserInfo[]>>
	// >("/apply-user-list/:battleBoardId", ({ params }) => {

	// }),
	http.post<never, ApplyBattleRequest, ApiResponse<string>>(
		"/battle/apply",
		() => {
			return HttpResponse.json({
				code: "success",
				data: "",
			});
		},
	),
];
