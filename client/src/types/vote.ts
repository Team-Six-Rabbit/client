import { BasicUserInfo } from "./user";

export interface Vote {
	id?: number;
	title: string;
	detail?: string;
	startDate: number;
	endDate: number;
	category: number;
}

interface Opinion {
	index: number;
	opinion: string;
}

export interface BalanceGameOpinion extends Opinion {
	count: number;
	percentage: number;
}

export interface LiveBattleOpinion extends Opinion {
	user?: BasicUserInfo;
	preCount?: number;
	finalCount?: number;
	isWinner?: boolean;
}
