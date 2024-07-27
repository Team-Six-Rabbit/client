import { Battle } from "./battle";
import { Opinion, Vote } from "./vote";

export interface ApiResponse<T> {
	code: "success" | "fail";
	data: T;
}

export interface PageableResponse<T> {
	content: T;
	pageable: "INSTANCE";
	last: boolean;
	totalPages: number;
	totalElements: number;
	first: boolean;
	numberOfElements: number;
	size: number;
	number: number;
	sort: {
		empty: boolean;
		sorted: boolean;
		unsorted: boolean;
	};
	empty: boolean;
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

export interface PageableParm {
	page?: number;
	size?: number;
	sort?: string;
}

export interface BattleInviteRequest {
	voteInfo: Vote;
	opinions: Opinion[];
	minPeopleCount: number;
	maxPeopleCount: number;
	detail: string;
	battleRule: string;
}

export interface BattleHistoryRequestParam extends PageableParm {
	type: "received" | "sent";
}

export interface BattleResponse {
	battleBoard: Battle;
	opinionList: Opinion[];
}

export interface BattleInviteRespondRequest {
	battleId: number;
	respond: "accept" | "decline";
	content: string;
}

export interface SearchRecruitingBattleRequestParam extends PageableParm {
	category: number;
}

export interface ApplyBattleRequest {
	id: number;
	selectedOpinion: number;
}
