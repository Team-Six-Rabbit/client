import { LoginRequest, ApiResponse } from "@/types/api";
import { DetailUserInfo } from "@/types/user";
import { http, HttpResponse, PathParams } from "msw";

export const handlers = [
	http.post<PathParams, LoginRequest, ApiResponse<DetailUserInfo | string>>(
		"/battle-people/auth/login",
		async ({ request }) => {
			const { email } = await request.json();
			if (email === "test@email.com") {
				const body: ApiResponse<DetailUserInfo> = {
					code: "success",
					data: {
						id: 1,
						email: "test@email.com",
						nickname: "testUser",
						rating: 99999,
						imgUrl: "test/url",
					},
				};
				return HttpResponse.json(body, {
					headers: [
						["Set-Cookie", "accessToken=1234"],
						["Set-Cookie", "refreshToken=5678"],
					],
				});
			}
			return HttpResponse.json(
				{
					code: "fail",
					data: "올바르지 않은 아이디/비밀번호",
				},
				{ status: 400 },
			);
		},
	),
	http.post("/battle-people/auth/logout", () => {
		return HttpResponse.json({
			code: "success",
			data: "로그아웃 성공",
		});
	}),
];
