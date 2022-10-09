package br.com.phc.pitaco.crawler.websocket;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class ClientWebSocket extends TextWebSocketHandler {

	private static final Logger logger = LoggerFactory.getLogger(ClientWebSocket.class);

	public void sendMessageWebSocket(String message) throws Exception {
		WebSocketClient webSocketClient = new StandardWebSocketClient();
		WebSocketSession clientSession = webSocketClient.doHandshake(this, new WebSocketHttpHeaders(), URI.create("ws://localhost:8086/partida")).get();

		if (clientSession.isOpen()) {
			clientSession.sendMessage(new TextMessage(message));
			clientSession.close();
		}
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) {
		logger.info("Mensagem enviada pelo crawler para todos os clientes");
	}
}
