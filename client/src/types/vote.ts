export interface Vote {
	id?: number;
	title: string;
	detail?: string;
	startDate: number;
	endDate: number;
	category: number;
}

export interface Opinion {
	index: number;
	opinion: string;
	count: number;
	percentage: number;
}
