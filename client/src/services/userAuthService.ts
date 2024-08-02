import axiosInstance from "@/services/axiosInstance";
import { JoinRequest, LoginRequest, ApiResponse } from "@/types/api";
import { DetailUserInfo } from "@/types/user";
import { useAuthStore } from "@/stores/userAuthStore";

export const authService = {
	// 로그인 함수
	login: async (
		loginRequest: LoginRequest,
	): Promise<ApiResponse<DetailUserInfo>> => {
		try {
			const response = await axiosInstance.post<ApiResponse<DetailUserInfo>>(
				"/auth/login",
				loginRequest,
			);
			if (response.data.code === "success" && response.data.data) {
				useAuthStore.getState().login(response.data.data);
			}
			return response.data;
		} catch (error) {
			console.error("Login Error: ", error);
			throw error;
		}
	},

	// 회원가입 함수
	join: async (joinRequest: JoinRequest): Promise<ApiResponse<string>> => {
		try {
			const response = await axiosInstance.post<ApiResponse<string>>(
				"/user/join",
				joinRequest,
			);
			return response.data;
		} catch (error) {
			console.error("Join Error: ", error);
			throw error;
		}
	},

	// 사용자 정보 가져오기 함수
	getUserInfo: async (
		userId?: number,
	): Promise<ApiResponse<DetailUserInfo>> => {
		try {
			const url = userId ? `/user/profile/${userId}` : "/user/profile";
			const response =
				await axiosInstance.get<ApiResponse<DetailUserInfo>>(url);
			if (!userId && response.data.code === "success" && response.data.data) {
				useAuthStore.getState().setUser(response.data.data);
			}
			return response.data;
		} catch (error) {
			console.error("Get User Info Error: ", error);
			throw error;
		}
	},

	// 로그아웃 함수
	logout: async (): Promise<void> => {
		try {
			await axiosInstance.post("/auth/logout");
			useAuthStore.getState().logout();
		} catch (error) {
			console.error("Logout Error: ", error);
			throw error;
		}
	},
};
