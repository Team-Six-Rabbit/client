export interface Vote {
	id?: number;
	title: string;
	detail?: string;
	startDate: string;
	endDate: string;
	category: number;
}

export interface Opinion {
	index: number;
	opinion: string;
	count: number;
	percentage: number;
}
