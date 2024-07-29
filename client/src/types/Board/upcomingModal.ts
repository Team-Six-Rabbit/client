export interface User {
	id: number;
	nickname: string;
	imgUrl: string;
	rating: number;
	opinion: string;
}

export interface UpcomingModal {
	id: number;
	roomId: string;
	title: string;
	registerUser: User;
	oppositeUser: User;
	startDate: string;
	endDate: string;
	currentPeopleCount: number;
	category: string;
	imageUri: string;
	battleRule: string;
	summary: string;
}
