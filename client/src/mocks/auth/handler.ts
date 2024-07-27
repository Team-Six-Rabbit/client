import { LoginRequest, ApiResponse } from "@/types/api";
import { User } from "@/types/user";
import { http, HttpResponse, PathParams } from "msw";

export const handlers = [
	http.post<PathParams, LoginRequest, undefined>(
		"/auth/login",
		async ({ request }) => {
			const { email } = await request.json();
			if (email === "test@email.com") {
				const body: ApiResponse<User> = {
					code: "success",
					data: {
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
			return HttpResponse.json<ApiResponse<string>>(
				{
					code: "fail",
					data: "올바르지 않은 아이디/비밀번호",
				},
				{ status: 400 },
			);
		},
	),
	http.post("/auth/logout", () => {
		return HttpResponse.json({
			code: "success",
			data: "로그아웃 성공",
		});
	}),
];
