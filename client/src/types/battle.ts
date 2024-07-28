import { BasicUserInfo } from "./user";
import { Vote } from "./vote";

export interface Battle {
	id: number;
	registUser: BasicUserInfo;
	oppositeUser: BasicUserInfo;
	voteInfo: Vote;

	minPeopleCount: number;
	maxPeopleCount: number;
	battleRule: string;
	registDate: number;
	currentState: number;
	rejectionReason?: string;
	imageUrl?: string;
}
