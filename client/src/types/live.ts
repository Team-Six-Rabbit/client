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

export interface LiveBattleDetail extends LiveBattle {
	roomId: string;
	startDate: string;
	endDate: string;
	currentPeopleCount?: number;
}

export interface LiveBattleEndDetail extends LiveBattle {
	preResult: LiveVoteResult;
	finalResult: LiveVoteResult;
}

export interface UserWithOpinion extends BasicUserInfo {
	opinion: string;
}

interface LiveVoteResult {
	percentageRegisterOpinion: number;
	percentageOppositeOpinion: number;
}

export interface LiveViewer extends BasicUserInfo {
	inTime: string;
	outTime?: string;
}
