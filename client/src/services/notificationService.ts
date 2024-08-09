import axiosInstance from "./axiosInstance";

export const notificationService = {
	// 사용자 알림 전체 조회
	getNotificationList: async (userId: string) => {
		try {
			const response = await axiosInstance.get<Notification[]>(
				`/notify/${userId}`,
			);
			return response.data;
		} catch (error) {
			console.error("Failed to fetch notifications:", error);
			throw error;
		}
	},

	// 알림 별 상세 내역 조회
	getNotificationDetail: async (notifyId: string) => {
		try {
			const response = await axiosInstance.get<Notification[]>(
				`/notify/detail/${notifyId}`,
			);
			return response.data;
		} catch (error) {
			console.error("Failed to fetch notifications:", error);
			throw error;
		}
	},

	// 알림 조회 처리
	putNotification: async (notifyId: string) => {
		try {
			const response = await axiosInstance.put<Notification[]>(
				`/notify/${notifyId}`,
			);
			return response.status === 204;
		} catch (error) {
			console.error("Failed to fetch notifications:", error);
			throw error;
		}
	},
	// 알림 삭제
	deleteNotification: async (notifyId: string) => {
		try {
			const response = await axiosInstance.delete<Notification[]>(
				`/notify/${notifyId}`,
			);
			return response.status === 204;
		} catch (error) {
			console.error("Failed to fetch notifications:", error);
			throw error;
		}
	},
};
