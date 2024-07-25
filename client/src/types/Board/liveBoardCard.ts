export interface CardType {
	id: number;
	title: string;
	regist_user_id: string;
	opposite_user_id: string;
	start_date: string;
	end_date: string;
	max_people_count: number;
	live_apply_user_count: number;
	category: number;
	image_uri: string;
	live_uri: string;
	status: "live" | "upcoming" | "ended";
	index: number;
}
