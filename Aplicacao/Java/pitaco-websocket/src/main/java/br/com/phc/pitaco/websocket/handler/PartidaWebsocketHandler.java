package br.com.phc.pitaco.websocket.handler;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.SubProtocolCapable;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.phc.pitaco.utilities.dto.PartidaDTO;
import br.com.phc.pitaco.utilities.dto.WsBuscaPartida;
import br.com.phc.pitaco.utilities.indicador.NotificaWebSocket;
import br.com.phc.pitaco.websocket.service.PartidaService;

@Component
public class PartidaWebsocketHandler extends TextWebSocketHandler implements SubProtocolCapable {

	private static final Logger logger = LoggerFactory.getLogger(PartidaWebsocketHandler.class);

	private Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private PartidaService partidaService;

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info("Conexao estabelecida: [{}]", session.getId());
		sessions.put(session.getId(), session);

		TextMessage message = new TextMessage("{\"message\":\"Websocket conectado\"}");
		logger.info("Envio confirmacao de conexao");
		session.sendMessage(message);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		logger.info("Conexao encerrada: [{}] - {}", session.getId(), status);
		sessions.remove(session.getId());
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String request = message.getPayload();
		logger.info("Cliente [{}] Mensagem recebida: {}", session.getId(), request);

		WsBuscaPartida msgRecebida = objectMapper.readValue(request, WsBuscaPartida.class);
		
		List<PartidaDTO> listaPartidasHoje = partidaService.listarPartidasHojeLigasUsuarioPaginado(msgRecebida.getIdUsuario(), msgRecebida.getInicio());
		
		String msgEnviada = objectMapper.writeValueAsString(listaPartidasHoje);
		
		if (NotificaWebSocket.INDIVIDUAL.equals(msgRecebida.getNotifica())) {
			logger.info("Envia resposta para o cliente [{}]", session.getId());
			session.sendMessage(new TextMessage(msgEnviada));
		} else {
			for (WebSocketSession ws : sessions.values()) {
				logger.info("Envia resposta para o cliente [{}]", ws.getId());
				ws.sendMessage(new TextMessage(msgEnviada));
			}
		}
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) {
		logger.info("Erro no  transporte do websocket: {}", exception.getMessage());
	}

	@Override
	public List<String> getSubProtocols() {
		return Collections.singletonList("subprotocol.demo.websocket");
	}
}
