export interface Notification {
	code: string;
	message: string;
	category: "Live" | "Invite" | "Punishment";
	opposite?: string;
	url?: string;
}
