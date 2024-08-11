import { create } from "zustand";

interface NotificationState {
	hasNewNotification: boolean;
	setNewNotification: (hasNew: boolean) => void;
}

const notificationStore = create<NotificationState>((set) => ({
	hasNewNotification: false,
	setNewNotification: (hasNew: boolean) => set({ hasNewNotification: hasNew }),
}));

export default notificationStore;
