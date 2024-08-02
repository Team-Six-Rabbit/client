import { BasicUserInfo } from "./user";
import { Vote } from "./vote";

export interface Battle {
	id: number;
	registUser: BasicUserInfo;
	oppositeUser: BasicUserInfo;
	voteInfo: Vote;

	minPeopleCount: number;
	maxPeopleCount: number;
	registDate: string;
	currentState: number;
	imageUrl?: string;
}
