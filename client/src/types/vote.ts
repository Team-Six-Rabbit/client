import { BasicUserInfo } from "./user";

export interface Vote {
	id?: number;
	title: string;
	detail?: string;
	startDate: number;
	endDate: number;
	category: number;
}

export interface Opinion {
	index: number;
	opinion: string;
	user?: BasicUserInfo;
	preCount?: number;
	finalCount?: number;
	isWinner?: boolean;
}
