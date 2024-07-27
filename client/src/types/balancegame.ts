import { BasicUserInfo } from "./user";

export interface Comment {
	id: number;
	battleBoardId: number;
	user: BasicUserInfo;
	content: string;
	registDate: string;
}
