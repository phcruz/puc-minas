package br.com.phc.pitaco.authentication.handler;

import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.phc.pitaco.utilities.dto.ErrorDetailsDTO;

public class CustomAuthenticationFailureHandler implements AuthenticationEntryPoint {

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2) throws IOException, ServletException {

		response.setStatus(HttpStatus.UNAUTHORIZED.value());

		ErrorDetailsDTO details = new ErrorDetailsDTO();
		details.setUniqueId(UUID.randomUUID().toString());
		details.setMessage("Falha na autenticação. Por favor realize o login novamente.");
		details.setInformationCode(HttpStatus.UNAUTHORIZED.name());
		details.setStatusCode(HttpStatus.UNAUTHORIZED.value());
		details.setTime(Calendar.getInstance().getTimeInMillis());

		response.getOutputStream().println(objectMapper.writeValueAsString(details));
	}

}
