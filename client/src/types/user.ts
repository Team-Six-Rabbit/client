export interface BasicUserInfo {
	id: number;
	nickname: string;
	imgUrl: string;
	rating: number;
}

export interface DetailUserInfo extends BasicUserInfo {
	email: string;
	regDate?: Date;
}

export interface LiveApplyUser extends BasicUserInfo {
	rating: number;
	inTime?: string;
	outTime?: null;
}
