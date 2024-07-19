// userAuthStore.ts

import { User } from "@/types/user";
import { create } from "zustand";
import { persist, createJSONStorage } from "zustand/middleware";

interface AuthState {
	isLogin: boolean;
	user: User | null;
}

interface AuthAction {
	login: (user: User) => void;
	logout: () => void;
	setUser: (user: User | null) => void;
}

export const useAuthStore = create<AuthState & AuthAction>()(
	persist(
		(set) => ({
			isLogin: false,
			user: null,
			login: (user: User, expireAt: Date = new Date(Date.now() + 3600000)) => {
				set({ isLogin: true, user });
				sessionStorage.setItem(
					"user",
					JSON.stringify({ user, expireAt: expireAt.toISOString() }),
				);
			},
			logout: () => {
				set({ isLogin: false, user: null });
				sessionStorage.removeItem("user");
			},
			setUser: (user: User | null) => set({ user }),
		}),
		{
			name: "auth",
			storage: createJSONStorage(() => localStorage),
		},
	),
);

// 페이지가 새로고침될 때 유저 정보를 세션 스토리지에서 가져와서 상태를 갱신
const storedUser = sessionStorage.getItem("user");
if (storedUser) {
	const { user, expireAt } = JSON.parse(storedUser);
	if (new Date() < new Date(expireAt)) {
		useAuthStore.getState().login(user);
	} else {
		sessionStorage.removeItem("user");
	}
}
