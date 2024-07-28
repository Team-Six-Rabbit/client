import { BasicUserInfo } from "./user";

export interface Comment {
	id: number;
	battleId: number;
	user: BasicUserInfo;
	content: string;
	registDate: string;
}
