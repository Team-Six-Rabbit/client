package com.woowahanrabbits.battle_people.handler;

import java.util.Map;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LiveChatHandler implements WebSocketHandler {

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		Map<String, String> uriTemplateVars = (Map<String, String>)session.getAttributes().get("uriTemplateVars");
		String battleId = uriTemplateVars.get("battle_id");
		System.out.println("Connected to battle room: " + battleId);
		// handle the new connection in the room identified by battleId
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		String tutorial = message.getPayload().toString();
		// log.info("Received tutorial: " + tutorial);
		session.sendMessage(new TextMessage(tutorial));

	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		// log.info("Transport error: " + exception.getMessage());
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		// log.info("Connection closed: " + session.getRemoteAddress());
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}
}
