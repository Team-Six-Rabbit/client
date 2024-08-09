/* eslint-disable no-useless-catch */
import axiosInstance from "@/services/axiosInstance";
import { JoinRequest, LoginRequest, ApiResponse } from "@/types/api";
import { BasicUserInfo, DetailUserInfo } from "@/types/user";
import { useAuthStore } from "@/stores/userAuthStore";
// import { generateBasicUser } from "@/mocks/util";

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
			await axiosInstance.delete("/auth/logout");
			useAuthStore.getState().logout();
		} catch (error) {
			console.error("Logout Error: ", error);
			throw error;
		}
	},

	// 닉네임 중복 체크 함수
	checkNicknameAvailability: async (nickname: string): Promise<boolean> => {
		try {
			const response = await axiosInstance.get<ApiResponse<boolean>>(
				`/user/check/nickname?nickname=${nickname}`,
			);
			return response.data.data ?? false;
		} catch (error) {
			console.error("Check Nickname Error: ", error);
			throw error;
		}
	},

	// 이메일 중복 체크 함수
	checkEmailAvailability: async (email: string): Promise<boolean> => {
		try {
			const response = await axiosInstance.get<ApiResponse<boolean>>(
				`/user/check/email?email=${email}`,
			);
			return response.data.data ?? false;
		} catch (error) {
			console.error("Check Email Error: ", error);
			throw error;
		}
	},

	// 닉네임으로 사용자 검색 함수
	searchUserByNickname: async (
		nickname: string,
	): Promise<ApiResponse<BasicUserInfo[]>> => {
		try {
			const response = await axiosInstance.get<ApiResponse<BasicUserInfo[]>>(
				`/user?nickname=${encodeURIComponent(nickname)}`,
			);
			return response.data;
		} catch (error) {
			console.error("Search User By Nickname Error: ", error);
			throw error;
		}
	},

	// 수정된 유저 정보 저장
	updateUserProfile: async (
		userInfo: DetailUserInfo,
	): Promise<ApiResponse<DetailUserInfo>> => {
		try {
			const response = await axiosInstance.post<ApiResponse<DetailUserInfo>>(
				"/user/profile",
				userInfo,
			);
			if (response.data.code === "success" && response.data.data) {
				useAuthStore.getState().setUser(response.data.data);
			}
			return response.data;
		} catch (error) {
			throw error;
		}
	},

	// 프로필 이미지 업로드 함수
	uploadProfileImage: async (
		formData: FormData,
	): Promise<ApiResponse<string>> => {
		try {
			const response = await axiosInstance.post<ApiResponse<string>>(
				"/user/upload",
				formData,
				{
					headers: {
						"Content-Type": "multipart/form-data",
					},
				},
			);
			console.log(response);
			return response.data;
		} catch (error) {
			console.log(error);
			throw error;
		}
	},

	// 프로필 이미지 가져오기 함수
	getProfileImage: async (filename: string): Promise<string | null> => {
		try {
			const response = await axiosInstance.get(`/user/images/${filename}`, {
				responseType: "blob",
			});
			const url = URL.createObjectURL(new Blob([response.data]));
			return url;
		} catch (error) {
			console.error("Error fetching image:", error);
			return null;
		}
	},

	getLoginUserWinHistory: async (): Promise<UserWinHistory> => {
		try {
			const response = await axiosInstance.get(`/user/profile/win_rate`);
			console.log(response.data);
			return response.data.data;
		} catch (error) {
			console.error("Failed to fetch user win history:", error);
			throw error;
		}
	},
};

export interface UserWinHistory {
	debateCnt: number;
	winCnt: number;
	loseCnt: number;
	winRate: number;
}
