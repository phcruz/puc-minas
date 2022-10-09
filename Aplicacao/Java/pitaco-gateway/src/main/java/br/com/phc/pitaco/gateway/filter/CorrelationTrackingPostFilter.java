package br.com.phc.pitaco.gateway.filter;

import java.util.Objects;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import br.com.phc.pitaco.gateway.indicador.FilterOrderType;
import br.com.phc.pitaco.utilities.Constantes;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CorrelationTrackingPostFilter implements GlobalFilter, Ordered {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		log.info("Injecting correlation into response...");

		HttpHeaders header = exchange.getRequest().getHeaders();

		exchange.getResponse().getHeaders().add(Constantes.CORRELATION_ID, getCorrelationId(header));

		return chain.filter(exchange);
	}

	private String getCorrelationId(HttpHeaders header) {
		return Objects.requireNonNull(header.get(Constantes.CORRELATION_ID)).iterator().next();
	}

	@Override
	public int getOrder() {
		return FilterOrderType.POST.getOrder();
	}
}
