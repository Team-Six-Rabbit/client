export interface ApiResponse<T> {
	code: "success" | "fail";
	data: T;
}

export interface JoinRequest {
	email: string;
	password: string;
	nickname: string;
}

export interface LoginRequest {
	email: string;
	password: string;
}
