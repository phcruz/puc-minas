package br.com.phc.pitaco.utilities.config;

import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import br.com.phc.pitaco.utilities.Constantes;
import br.com.phc.pitaco.utilities.dto.UsuarioDTO;
import br.com.phc.pitaco.utilities.exception.CustomException;
import br.com.phc.pitaco.utilities.indicador.Role;
import br.com.phc.pitaco.utilities.model.LogToken;
import br.com.phc.pitaco.utilities.model.Usuario;
import br.com.phc.pitaco.utilities.repository.LogTokenRepository;
import br.com.phc.pitaco.utilities.repository.UsuarioRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ConditionalOnProperty(name = Constantes.ENABLE_SECURITY, havingValue = "true", matchIfMissing = false)
@Component
public class JwtTokenProvider {

	private String secretKey;

	@Autowired
	private MyUserDetails myUserDetails;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private LogTokenRepository logTokenRepository;

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(Constantes.FRASE_SEGREDO.getBytes());
	}

	public String gerarToken(String email, List<Role> roles) {
		final Usuario user = usuarioRepository.findByEmail(email);

		Claims claims = Jwts.claims().setSubject(email);
		claims.put("name", user.getNome());
		claims.put("user", user.getIdUsuario());
		claims.put("auth", roles.stream().map(s -> new SimpleGrantedAuthority(s.getAuthority()))
				.filter(Objects::nonNull).collect(Collectors.toList()));

		Date now = new Date();
		Calendar expira = Calendar.getInstance();
		expira.add(Calendar.DAY_OF_MONTH, +Constantes.QUANTIDADE_DIAS_EXPIRACAO_TOKEN);
		Date validity = new Date(expira.getTimeInMillis());

		String token = Jwts.builder()//
				.setClaims(claims)//
				.setIssuedAt(now)//
				.setExpiration(validity)//
				.signWith(SignatureAlgorithm.HS256, secretKey)//
				.compact();

		this.insereLogToken(token, user, now, expira);
				
		return token;
	}

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = myUserDetails.loadUserByUsername(getUsername(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public String getUsername(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}

	public UsuarioDTO getInfosUserJWT(String token) {
		UsuarioDTO dto = new UsuarioDTO();
		try {
			dto.setEmail((String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("sub"));
			dto.setNome((String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("name"));
			dto.setTransaction(UUID.randomUUID().toString() + "@"
					+ (Integer) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("user"));
		} catch (Exception e) {
		}
		return dto;
	}

	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			throw new CustomException("Token expirado.", HttpStatus.PRECONDITION_FAILED);
		}
	}

	public void insereLogToken(String token, Usuario user, Date agora, Calendar expira) {
		log.info("Acessando insereLogToken(String token, String email, Date agora, Calendar expira)");
		LogToken logTok = new LogToken();
		logTok.setToken(token);
		logTok.setUsuario(user);
		logTok.setEmail(user.getEmail());
		logTok.setDataCriacao(agora);
		logTok.setDataExpiracao(new Date(expira.getTimeInMillis()));

		LogToken logToken = logTokenRepository.findByEmail(user.getEmail());

		if (logToken == null) {
			// cria uma nova linha
			logTokenRepository.save(logTok);
		} else {
			// altera linha existente
			logTok.setIdLogToken(logToken.getIdLogToken());
			logTokenRepository.save(logTok);
		}
	}
}
