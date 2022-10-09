package br.com.phc.pitaco.utilities.util;

import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.phc.pitaco.utilities.Constantes;
import br.com.phc.pitaco.utilities.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ConditionalOnProperty(name = Constantes.ENABLE_UTIL_JWT, havingValue = "true", matchIfMissing = false)
@Component
public class JwtUtil {

	private String secretKey;

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(Constantes.FRASE_SEGREDO.getBytes());
	}

	public boolean validateToken(String token) {
		try {
			log.info("validateToken");
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			log.error("validateToken: {}", e.getMessage());
			throw new CustomException("Token expirado.", HttpStatus.PRECONDITION_FAILED);
		}
	}

	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
}
