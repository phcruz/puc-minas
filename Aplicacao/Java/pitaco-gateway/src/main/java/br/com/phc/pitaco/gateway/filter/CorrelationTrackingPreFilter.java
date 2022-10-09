package br.com.phc.pitaco.gateway.filter;

import static java.lang.String.format;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import br.com.phc.pitaco.gateway.indicador.FilterOrderType;
import br.com.phc.pitaco.utilities.Constantes;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CorrelationTrackingPreFilter implements GlobalFilter, Ordered {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		log.info("Tracking filter invoked...");

		ServerHttpRequest request = exchange.getRequest();
		HttpHeaders header = request.getHeaders();

		if (hasCorrelationId(header)) {
			log.info(format("Tracked request with correlation id %s", header.get(Constantes.CORRELATION_ID)));
		} else {
			request = exchange.getRequest().mutate().header(Constantes.CORRELATION_ID, generateCorrelationId()).build();
			return chain.filter(exchange.mutate().request(request).build());
		}

		return chain.filter(exchange);
	}

	private boolean hasCorrelationId(HttpHeaders header) {
		return header.containsKey(Constantes.CORRELATION_ID);
	}

	private String generateCorrelationId() {
		return java.util.UUID.randomUUID().toString();
	}

	@Override
	public int getOrder() {
		return FilterOrderType.PRE.getOrder();
	}
}
