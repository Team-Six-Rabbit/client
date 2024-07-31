export interface Opinion {
	index: number;
	opinion: string;
	finalCount: number | null;
}

export interface TicketType {
	id: number;
	title: string;
	opinionDtos: Opinion[];
	startDate: string;
	endDate: string;
	maxPeopleCount: number;
	currentPeopleCount: number;
	isVoted: boolean;
}

export interface ExampleProps {
	tickets: TicketType[];
}
