package br.com.phc.pitaco.authentication.security;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.phc.pitaco.utilities.Constantes;
import br.com.phc.pitaco.utilities.dto.ErrorDetailsDTO;
import br.com.phc.pitaco.utilities.util.UtilData;
import br.com.phc.pitaco.utilities.util.UtilMetodos;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RegisterPublicFilter implements Filter {

	private static final String MENSAGEM_ERRO  = "Sua solicitação não pode ser completada devido há um erro em sua requisição.";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        String xRegisterPublic = null;
        String path = req.getRequestURI().substring(req.getContextPath().length());
        boolean isAtivarUsuario = false;
        if (path.equals("/usuario/ativarUsuario")) {
        	xRegisterPublic = request.getParameter("transaction");
        	isAtivarUsuario = true;
        } else {
        	xRegisterPublic = req.getHeader(Constantes.HEADER_REGISTER_PUBLIC);
        }
        
        if (xRegisterPublic == null || xRegisterPublic.trim().equals("") || validaTempoXPublicRegister(xRegisterPublic, isAtivarUsuario)) {
        	try {
        		String errorDetails = this.createErrorDetails(path, xRegisterPublic);

            	res.setStatus(HttpStatus.BAD_REQUEST.value());
				res.setContentType("application/json");
				res.setCharacterEncoding("UTF-8");
				res.getWriter().write(errorDetails);
				
                return;	
        	} catch (Exception e) {
        		res.sendError(HttpStatus.BAD_REQUEST.value(), MENSAGEM_ERRO);
        		return;
			}
        }
        
        chain.doFilter(request, response);
	}

	private String createErrorDetails(String path, String xRegisterPublic) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		
		ErrorDetailsDTO details = new ErrorDetailsDTO();
		details.setUniqueId(UUID.randomUUID().toString());
		details.setMessage(MENSAGEM_ERRO);
		details.setInformationCode(HttpStatus.BAD_REQUEST.name());
		details.setStatusCode(HttpStatus.BAD_REQUEST.value());
		details.setTime(Calendar.getInstance().getTimeInMillis());

		Map<String, String> args = new HashMap<>();
		args.put(Constantes.HEADER_REGISTER_PUBLIC, createMessageXRegisterpublic(xRegisterPublic));
		args.put("path", path);
		details.setArgs(args);

		String jsonError = objectMapper.writeValueAsString(details);
		log.info(String.format("Erro: %s", jsonError));
		return jsonError;
	}
	
	private boolean validaTempoXPublicRegister(String xRegisterPublic, boolean isAtivarUsuario) {
		if (isAtivarUsuario) {
			return false;
		}
		String decodeRegister = UtilMetodos.decodeXRegisterpublic(xRegisterPublic);
		String[] parametros = decodeRegister.split(":");
		
		String dateRegister = parametros[2] + ":" + parametros[3];
		
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime dataRegister = UtilData.convertStringInlocalDateTime(dateRegister).plusMinutes(10);
		
		return today.isAfter(dataRegister);
	}
	
	private String createMessageXRegisterpublic(String xRegisterPublic) {
		if (Objects.isNull(xRegisterPublic)) {
			return "É necessário fornecer esse header para essa requisição.";
		}
		return validaTempoXPublicRegister(xRegisterPublic, false) ? "Header expirado. Por favor verifique e tente novamente." : "É necessário fornecer esse header para essa requisição.";
	}
}
