import {
	BalanceGameResponse,
	BattleResponse,
	PageableResponse,
} from "@/types/api";
import { LoremIpsum } from "lorem-ipsum";
import { Battle } from "@/types/battle";
import { Opinion } from "@/types/vote";
import { BasicUserInfo } from "@/types/user";
import { FinishedLiveBattleDetail, LiveBattleCardInfo } from "@/types/live";

const lorem = new LoremIpsum();

const generateSentences = (sentenceNum: number = 1, length: number = 16) => {
	const title = lorem.generateSentences(sentenceNum);
	return title.substring(0, length);
};

const generateInteger = (max: number = 1000) => {
	return Math.floor(Math.random() * max);
};

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
	registUser: generateBasicUser(),
	oppositeUser: generateBasicUser(),
	voteInfo: {
		id: Math.floor(Math.random() * 10000),
		title: generateSentences(),
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
	percentage: Math.floor((count / totalCount) * 100),
});

function generateOpinions(...counts: number[]): Opinion[] {
	const totalCount = counts.reduce((sum, count) => sum + count, 0);

	const opinions = counts.map((count, index) =>
		generateOpinion(index, count, totalCount),
	);

	const totalPercentage = opinions.reduce(
		(acc, curr) => acc + curr.percentage,
		0,
	);

	if (totalPercentage !== 100) {
		const maxIndex = opinions.reduce(
			(maxIdx, curr, idx, arr) =>
				curr.percentage > arr[maxIdx].percentage ? idx : maxIdx,
			0,
		);
		opinions[maxIndex].percentage += 100 - totalPercentage;
	}

	return opinions;
}

export const generateBattleResponse = (
	id: number,
	category?: number,
): BattleResponse => {
	const count1 = Math.floor(Math.random() * 100);
	const count2 = Math.floor(Math.random() * 100);

	return {
		battle: generateBattle(id, category),
		opinions: generateOpinions(count1, count2),
	};
};

export const generateBalanceGameResponse = (
	category: number,
	status: number,
	id: number = Math.floor(Math.random() * 1000),
): BalanceGameResponse => {
	const count1 = Math.floor(Math.random() * 100);
	const count2 = 100 - count1; // Ensure that the sum of percentages is 100

	return {
		id,
		opinions: generateOpinions(count1, count2),
		detail: lorem.generateSentences(2),
		currentState: status,
		userVote: Math.random() > 0.5 ? 0 : undefined,
		title: generateSentences(),
		startDate: new Date().toString(),
		endDate: new Date(Date.now() + 86400000).toString(),
		category,
	};
};

export const generateLiveBattleCard = (
	category: number,
	battleId: number = generateInteger(7),
): LiveBattleCardInfo => {
	return {
		id: battleId,
		category,
		startDate: new Date().toString(),
		endDate: new Date(Date.now() + 86400000).toString(),
		registerUser: { ...generateBasicUser(), opinion: generateSentences(1, 16) },
		oppositeUser: { ...generateBasicUser(), opinion: generateSentences(1, 16) },
		roomId: generateInteger().toString(),
		title: generateSentences(1, 16),
		currentPeopleCount: Math.floor(Math.random() * 1000),
		imageUri: `/img/${lorem.generateWords(1)}`,
	};
};

export const generateFinishedLiveBattleResponse = (
	battleId: number,
	category: number,
): FinishedLiveBattleDetail => {
	const finalPercentageA = generateInteger(100);
	const prePercentageA = generateInteger(100);
	return {
		id: battleId,
		title: generateSentences(1, 16),
		category,
		registerUser: { ...generateBasicUser(), opinion: generateSentences(1, 16) },
		oppositeUser: { ...generateBasicUser(), opinion: generateSentences(1, 16) },
		preResult: {
			percentageRegisterOpinion: prePercentageA,
			percentageOppositeOpinion: 100 - finalPercentageA,
		},
		finalResult: {
			percentageRegisterOpinion: finalPercentageA,
			percentageOppositeOpinion: 100 - finalPercentageA,
		},
		imageUri: `/img/${lorem.generateWords(1)}`,
		summary: generateSentences(3, 1000),
	};
};
