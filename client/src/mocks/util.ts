import { BattleResponse, PageableResponse } from "@/types/api";
import { LoremIpsum } from "lorem-ipsum";
import { Battle } from "@/types/battle";
import { Opinion } from "@/types/vote";
import { BasicUserInfo } from "@/types/user";

const lorem = new LoremIpsum();

export const generateBasicUser = (): BasicUserInfo => {
	return {
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
		nickname: lorem.generateWords(2),
		imgUrl: lorem.generateWords(1),
		rating: 100,
	},
	oppositeUser: {
		nickname: lorem.generateWords(2),
		imgUrl: lorem.generateWords(1),
		rating: 100,
	},
	voteInfo: {
		id: Math.floor(Math.random() * 10000),
		title: lorem.generateWords(5),
		startDate: new Date().toISOString(),
		endDate: new Date(Date.now() + 86400000).toISOString(),
		category: category || (Math.floor(Math.random() * 10) % 7) + 1,
	},
	minPeopleCount: 2,
	maxPeopleCount: 10,
	detail: lorem.generateSentences(2),
	battleRule: lorem.generateSentences(1),
	registDate: new Date().toISOString(),
	currentState: Math.floor(Math.random() * 3),
	rejectionReason: Math.random() > 0.5 ? lorem.generateSentences(1) : undefined,
	imageUrl: Math.random() > 0.5 ? lorem.generateWords(1) : undefined,
});

const generateOpinion = (index: number): Opinion => ({
	index,
	opinion: lorem.generateSentences(1),
	userId: Math.random() > 0.5 ? index : undefined,
	userNickname: Math.random() > 0.5 ? lorem.generateWords(2) : undefined,
	preCount: Math.floor(Math.random() * 100),
	finalCount: Math.floor(Math.random() * 100),
	isWinner: Math.random() > 0.5,
});

export const generateBattleResponse = (
	id: number,
	category?: number,
): BattleResponse => ({
	battleBoard: generateBattle(id, category),
	opinionList: Array.from({ length: 2 }, (_, index) => generateOpinion(index)),
});
