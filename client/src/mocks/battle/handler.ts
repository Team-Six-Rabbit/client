import { http, HttpResponse } from "msw";

import {
	ApiResponse,
	ApplyBattleRequest,
	BattleInviteRequest,
	BattleInviteRespondRequest,
	BattleResponse,
} from "@/types/api";
import {
	generateBattleResponse,
	generateBattleUnconfirmed,
} from "@/mocks/util";
import { BattleUnconfirmed } from "@/types/battle";

export const handlers = [
	http.get<never, never, ApiResponse<BattleResponse[]>>(
		"/battle",
		async ({ request }) => {
			const qs = new URLSearchParams(request.url);
			const page = Number(qs.get("page") || 1);
			const size = Number(qs.get("size") || 10);
			const battles = Array.from({ length: size }, (_, index) =>
				generateBattleResponse((page - 1) * size + index + 1),
			);
			return HttpResponse.json({
				code: "success",
				data: battles,
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
	http.get<never, never, ApiResponse<BattleUnconfirmed[]>>(
		"/battle/apply-list",
		async ({ request }) => {
			const qs = new URLSearchParams(request.url);
			const size = Number(qs.get("size") || 10);
			const battles = Array.from({ length: size }, () =>
				generateBattleUnconfirmed(),
			);
			return HttpResponse.json({
				code: "success",
				data: battles,
			});
		},
	),
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
