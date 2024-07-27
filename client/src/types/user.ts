export interface BasicUserInfo {
	nickname: string;
	imgUrl: string;
	rating: number;
}

export interface DetailUserInfo extends BasicUserInfo {
	id: number;
	email: string;
	regDate?: Date;
}
