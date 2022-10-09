package br.com.phc.pitaco.gateway.filter;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import br.com.phc.pitaco.utilities.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class GatewayPreFilter implements GlobalFilter, Ordered {

	@Autowired
	private JwtUtil jwtUtil;
	
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) { 
        String requestPath = exchange.getRequest().getPath().toString();
        log.info("Request path = " + requestPath);
        
        HttpHeaders headers = exchange.getRequest().getHeaders();
        Set<String> headerNames = headers.keySet();
 
        headerNames.forEach((header) -> {
        	log.info(header + " " + headers.get(header));
        });
        
        /*
        ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();

		final List<String> apiEndpoints = List.of("/register", "/login");
		
		Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream().noneMatch(uri -> r.getURI().getPath().contains(uri));

		if (isApiSecured.test(request)) {
			if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				ServerHttpResponse response = exchange.getResponse();
				response.setStatusCode(HttpStatus.UNAUTHORIZED);

				return response.setComplete();
			}

			final String token = request.getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION).get(0);

			try {
				jwtUtil.validateToken(token);
			} catch (Exception e) {
				// e.printStackTrace();

				onError(ServerWebExchange exchange, String err, HttpStatus httpStatus)
			}

			//Claims claims = jwtUtil.getClaims(token);
			//exchange.getRequest().mutate().header("id", String.valueOf(claims.get("id"))).build();
		}
		*/
		return chain.filter(exchange);
    }
    
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus)  {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }
    
    @Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}
}
