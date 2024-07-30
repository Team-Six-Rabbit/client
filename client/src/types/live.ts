import { BasicUserInfo } from "./user";

interface LiveBattle {
	id: number;
	title: string;
	registerUser: UserWithOpinion;
	oppositeUser: UserWithOpinion;
	category: string;
	imageUri?: string;
	summary?: string;
}

interface LiveVoteResult {
	percentageRegisterOpinion: number;
	percentageOppositeOpinion: number;
}

export interface UserWithOpinion extends BasicUserInfo {
	opinion: string;
}

export interface LiveBattleList extends LiveBattle {
	roomId: string;
	startDate: string;
	endDate: string;
	currentPeopleCount?: number;
	battleRule?: string;
}

export interface LiveEndDetail extends LiveBattle {
	preResult: LiveVoteResult;
	finalResult: LiveVoteResult;
}
