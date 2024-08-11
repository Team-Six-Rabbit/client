// userAuthStore.ts

import { create } from "zustand";
import { persist, createJSONStorage } from "zustand/middleware";
import { DetailUserInfo } from "@/types/user";

interface AuthState {
	isLogin: boolean;
	user: DetailUserInfo | null;
}

interface AuthAction {
	login: (user: DetailUserInfo) => void;
	logout: () => void;
	setUser: (user: DetailUserInfo | null) => void;
}

export const useAuthStore = create<AuthState & AuthAction>()(
	persist(
		(set) => ({
			isLogin: false,
			user: null,
			login: (user: DetailUserInfo) => {
				set({ isLogin: true, user });
				sessionStorage.setItem("user", JSON.stringify({ user }));
			},
			logout: () => {
				set({ isLogin: false, user: null });
				sessionStorage.removeItem("user");
			},
			setUser: (user: DetailUserInfo | null) => set({ user }),
		}),
		{
			name: "auth",
			storage: createJSONStorage(() => sessionStorage),
		},
	),
);

// 페이지가 새로고침될 때 유저 정보를 세션 스토리지에서 가져와서 상태를 갱신
const storedUser = sessionStorage.getItem("user");
if (storedUser) {
	const { user } = JSON.parse(storedUser);
	if (new Date() < new Date(user.accessExpiration)) {
		useAuthStore.getState().login(user);
	} else {
		sessionStorage.removeItem("user");
	}
}
