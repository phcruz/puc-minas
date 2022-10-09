package br.com.phc.pitaco.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class GatewayPostFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
         
        return chain.filter(exchange).then(Mono.fromRunnable(()-> {
            
            log.info("Global Post-filter executed...");
            
        }));
    }

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

}
