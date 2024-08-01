import {
	BalanceGameResponse,
	BattleResponse,
	PageableResponse,
} from "@/types/api";
import { LoremIpsum } from "lorem-ipsum";
import { Battle } from "@/types/battle";
import { Opinion } from "@/types/vote";
import { BasicUserInfo } from "@/types/user";

const lorem = new LoremIpsum();

export const generateBasicUser = (): BasicUserInfo => {
	return {
		id: Math.floor(Math.random() * 3000),
		imgUrl: "img/default",
		nickname: lorem.generateWords(2),
		rating: Math.floor(Math.random() * 1000),
	};
};

export const generatePageableResponse = <T>(
	content: T[],
	page: number,
	size: number,
	totalElements: number,
): PageableResponse<T[]> => {
	const totalPages = Math.ceil(totalElements / size);
	return {
		content,
		pageable: "INSTANCE",
		last: page >= totalPages,
		totalPages,
		totalElements,
		first: page === 1,
		numberOfElements: content.length,
		size,
		number: page,
		sort: {
			empty: false,
			sorted: true,
			unsorted: false,
		},
		empty: content.length === 0,
	};
};

const generateBattle = (id: number, category?: number): Battle => ({
	id,
	registUser: {
		id: Math.floor(Math.random() * 3000),
		nickname: lorem.generateWords(2),
		imgUrl: lorem.generateWords(1),
		rating: 100,
	},
	oppositeUser: {
		id: Math.floor(Math.random() * 3000),
		nickname: lorem.generateWords(2),
		imgUrl: lorem.generateWords(1),
		rating: 100,
	},
	voteInfo: {
		id: Math.floor(Math.random() * 10000),
		title: lorem.generateWords(5),
		startDate: new Date().toString(),
		endDate: new Date(Date.now() + 86400000).toString(),
		category: category || (Math.floor(Math.random() * 10) % 7) + 1,
		detail: lorem.generateSentences(2),
	},
	minPeopleCount: 2,
	maxPeopleCount: 10,
	registDate: new Date().getMilliseconds(),
	currentState: Math.floor(Math.random() * 3),
	imageUrl: Math.random() > 0.5 ? lorem.generateWords(1) : undefined,
});

const generateOpinion = (
	index: number,
	totalCount: number,
	count: number,
): Opinion => ({
	index,
	opinion: lorem.generateSentences(1).substring(0, 16),
	count,
	percentage: (count / totalCount) * 100,
});

export const generateBattleResponse = (
	id: number,
	category?: number,
): BattleResponse => {
	const count1 = Math.floor(Math.random() * 100);
	const count2 = Math.floor(Math.random() * 100);
	const totalCount = count1 + count2;

	return {
		battle: generateBattle(id, category),
		opinions: [
			generateOpinion(0, totalCount, count1),
			generateOpinion(1, totalCount, count2),
		],
	};
};

export const generateBalanceGameResponse = (
	category: number,
	status: number,
	id: number = Math.floor(Math.random() * 1000),
): BalanceGameResponse => {
	const count1 = Math.floor(Math.random() * 100);
	const count2 = 100 - count1; // Ensure that the sum of percentages is 100
	const totalCount = count1 + count2;

	const generateTitle = () => {
		const title = lorem.generateSentences(1);
		return title.length > 16 ? title.substring(0, 16) : title;
	};

	return {
		id,
		opinions: [
			generateOpinion(0, totalCount, count1),
			generateOpinion(1, totalCount, count2),
		],
		detail: lorem.generateSentences(2),
		currentState: status,
		userVote: Math.random() > 0.5 ? 0 : undefined,
		title: generateTitle(),
		startDate: new Date().toString(),
		endDate: new Date(Date.now() + 86400000).toString(),
		category,
	};
};
