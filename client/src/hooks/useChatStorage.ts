import { useCallback, useEffect, useState } from "react";
import { ChatMessage, SpeechRequestMessage } from "@/types/Chat";
import {
	saveMessagesToLocalStorage,
	loadMessagesFromLocalStorage,
	saveSpeechRequestsToLocalStorage,
	loadSpeechRequestsFromLocalStorage,
} from "@/utils/localStorage";

const useChatStorage = () => {
	const [messages, setMessages] = useState<ChatMessage[]>(() =>
		loadMessagesFromLocalStorage(),
	);

	const [speechRequests, setSpeechRequests] = useState<SpeechRequestMessage[]>(
		() => loadSpeechRequestsFromLocalStorage(),
	);

	useEffect(() => {
		saveMessagesToLocalStorage(messages);
	}, [messages]);

	useEffect(() => {
		saveSpeechRequestsToLocalStorage(speechRequests);
	}, [speechRequests]);

	const addMessage = useCallback((message: ChatMessage) => {
		setMessages((prevMessages) => {
			const updatedMessages = [...prevMessages, message];
			return updatedMessages.slice(0, 40);
		});
	}, []);

	const addSpeechRequest = useCallback((request: SpeechRequestMessage) => {
		setSpeechRequests((prevRequests) => [...prevRequests, request]);
	}, []);

	return { messages, speechRequests, addMessage, addSpeechRequest };
};

export default useChatStorage;
