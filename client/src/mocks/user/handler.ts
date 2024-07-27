import { JoinRequest, ApiResponse } from "@/types/api";
import { User } from "@/types/user";
import { http, HttpResponse, PathParams } from "msw";

export const handlers = [
	http.post<PathParams, JoinRequest>("/user/join", async ({ request }) => {
		const { email } = await request.json();
		if (email === "test@email.com") {
			return HttpResponse.json<ApiResponse<string>>(
				{
					code: "fail",
					data: "이미 가입된 이메일",
				},
				{ status: 400 },
			);
		}

		return HttpResponse.json<ApiResponse<string>>({
			code: "success",
			data: "가입 완료",
		});
	}),
	http.get("/user/profile", () => {
		const body: ApiResponse<User> = {
			code: "success",
			data: {
				email: "test@email.com",
				nickname: "testUser",
				rating: 99999,
				imgUrl: "test/url",
			},
		};
		return HttpResponse.json(body);
	}),
	http.get<PathParams>("/user/profile/:userId", async ({ params }) => {
		const { userId } = params;
		if (userId === "1") {
			const body: ApiResponse<User> = {
				code: "success",
				data: {
					email: "test1.email.com",
					nickname: "testUser1",
					rating: 100,
					imgUrl: "test1/url",
				},
			};
			return HttpResponse.json(body);
		}
		if (userId === "2") {
			const body: ApiResponse<User> = {
				code: "success",
				data: {
					email: "test2.email.com",
					nickname: "testUser2",
					rating: 200,
					imgUrl: "test2/url",
				},
			};
			return HttpResponse.json(body);
		}
		return HttpResponse.json<ApiResponse<string>>(
			{
				code: "fail",
				data: "사용자를 찾을 수 없습니다",
			},
			{ status: 404 },
		);
	}),
];
