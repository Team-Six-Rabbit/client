import { ChatMessage, SpeechRequestMessage } from "@/types/Chat";

export const saveMessagesToLocalStorage = (messages: ChatMessage[]) => {
	localStorage.setItem("chatMessages", JSON.stringify(messages));
};

export const loadMessagesFromLocalStorage = (): ChatMessage[] => {
	const messages = localStorage.getItem("chatMessages");
	return messages ? JSON.parse(messages) : [];
};

export const saveSpeechRequestsToLocalStorage = (
	requests: SpeechRequestMessage[],
) => {
	localStorage.setItem("speechRequests", JSON.stringify(requests));
};

export const loadSpeechRequestsFromLocalStorage =
	(): SpeechRequestMessage[] => {
		const requests = localStorage.getItem("speechRequests");
		return requests ? JSON.parse(requests) : [];
	};
