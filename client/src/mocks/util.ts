import {
	BalanceGameResponse,
	BattleResponse,
	PageableResponse,
} from "@/types/api";
import { LoremIpsum } from "lorem-ipsum";
import { Battle } from "@/types/battle";
import { Opinion } from "@/types/vote";
import { BasicUserInfo } from "@/types/user";
import { Comment } from "@/types/balancegame";

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
		startDate: new Date().getMilliseconds(),
		endDate: new Date(Date.now() + 86400000).getMilliseconds(),
		category: category || (Math.floor(Math.random() * 10) % 7) + 1,
		detail: lorem.generateSentences(2),
	},
	minPeopleCount: 2,
	maxPeopleCount: 10,
	battleRule: lorem.generateSentences(1),
	registDate: new Date().getMilliseconds(),
	currentState: Math.floor(Math.random() * 3),
	rejectionReason: Math.random() > 0.5 ? lorem.generateSentences(1) : undefined,
	imageUrl: Math.random() > 0.5 ? lorem.generateWords(1) : undefined,
});

const generateOpinion = (index: number): Opinion => ({
	index,
	opinion: lorem.generateSentences(1),
	user: generateBasicUser(),
	preCount: Math.floor(Math.random() * 100),
	finalCount: Math.floor(Math.random() * 100),
	isWinner: Math.random() > 0.5,
});

export const generateBattleResponse = (
	id: number,
	category?: number,
): BattleResponse => ({
	battle: generateBattle(id, category),
	opinions: Array.from({ length: 2 }, (_, index) => generateOpinion(index)),
});

export const generateBalanceGameResponse = (
	category: number,
	status: number,
): BalanceGameResponse => ({
	opinions: Array.from({ length: 2 }, (_, index) => generateOpinion(index)),
	detail: lorem.generateSentences(2),
	currentState: status,
	userVote: Math.random() > 0.5 ? 0 : undefined,
	title: "",
	startDate: new Date().getMilliseconds(),
	endDate: new Date(Date.now() + 86400000).getMilliseconds(),
	category,
});

export const generateComment = (battleBoardId: number): Comment => {
	return {
		battleId: battleBoardId,
		id: Math.floor(Math.random() * 1000),
		content: lorem.generateSentences(3),
		user: generateBasicUser(),
		registDate: new Date(
			Date.now() - Math.floor(Math.random() * 10000),
		).toISOString(),
	};
};
