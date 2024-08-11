import {
	Notification,
	NotificationInviteDetail,
	NotificationLiveDetail,
} from "@/types/notification";
import axiosInstance from "./axiosInstance";

export const notificationService = {
	// 사용자 알림 전체 조회
	getNotificationList: async () => {
		try {
			const response = await axiosInstance.get<Notification[]>(`/notify`);
			console.log(response.data);
			return response.data;
		} catch (error) {
			console.error("Failed to fetch notifications:", error);
			throw error;
		}
	},

	// 알림 별 상세 내역 조회
	getNotificationDetail: async (id: number) => {
		try {
			const response = await axiosInstance.get<
				NotificationLiveDetail | NotificationInviteDetail
			>(`/notify/detail/${id}`);
			return response.data;
		} catch (error) {
			console.error("Failed to fetch notifications:", error);
			throw error;
		}
	},

	// 알림 삭제
	deleteNotification: async (id: number) => {
		try {
			const response = await axiosInstance.delete<Notification[]>(
				`/notify/${id}`,
			);
			return response.status === 204;
		} catch (error) {
			console.error("Failed to fetch notifications:", error);
			throw error;
		}
	},
};
