package br.com.phc.pitaco.websocket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import br.com.phc.pitaco.websocket.handler.PartidaWebsocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    private static final String PARTIDA_ENDPOINT = "/partida";

    @Autowired
    private PartidaWebsocketHandler partidaWebsocketHandler;
    
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(partidaWebsocketHandler, PARTIDA_ENDPOINT)
                .setAllowedOrigins("*");
    }

}
