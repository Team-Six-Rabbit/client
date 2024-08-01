import { BasicUserInfo } from "./user";

interface LiveBattle {
	id: number;
	title: string;
	registerUser: UserWithOpinion;
	oppositeUser: UserWithOpinion;
	category: string;
	imageUri?: string;
}

export interface UpcomingLiveBattleDetail extends LiveBattle {
	roomId: string;
	startDate: string;
	endDate: string;
	currentPeopleCount?: number;
}

export interface FinishedLiveBattleDetail extends LiveBattle {
	preResult: LiveVoteResult;
	finalResult: LiveVoteResult;
	summary?: string;
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
