export interface BasicUserInfo {
	id: number;
	nickname: string;
	imgUrl: string;
	rating: number;
}

export interface DetailUserInfo extends BasicUserInfo {
	email: string;
	accessExpiration?: string;
}
