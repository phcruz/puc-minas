package br.com.phc.pitaco.notification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.phc.pitaco.notification.service.EmailService;
import br.com.phc.pitaco.notification.service.NotificacaoService;
import br.com.phc.pitaco.utilities.dto.ErrorDetailsDTO;
import br.com.phc.pitaco.utilities.dto.NotificacaoDTO;
import br.com.phc.pitaco.utilities.dto.NotificacaoUsuarioDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(value = "notificacao")
@RestController
@RequestMapping("/notificacao")
public class NotificacaoController {

	@Autowired
	private NotificacaoService notificacaoService;

	@Autowired
	private EmailService enviaEmailService;

	@ApiOperation(value = "Recupera mensagem de notificacao do usuario", 
			nickname = "buscaNotificacao",
			code = 200,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = NotificacaoDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 404, message = "Not Found", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 415, message = "Unsupported Media Type", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 503, message = "Service Unavailable", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 504, message = "Gateway Timeout", response = ErrorDetailsDTO.class) })
	@GetMapping("/mensagem/{idUsuario}")
	public ResponseEntity<NotificacaoDTO> buscarNotificacao(@PathVariable("idUsuario") long idUsuario) {
		log.info("Buscando notificacao");
		NotificacaoDTO notificacao = notificacaoService.verificaNotificacaoUsuario(idUsuario);
		log.info("Notificacao recuperada com sucesso");
		return ResponseEntity.ok().body(notificacao);
	}
	
	@ApiOperation(value = "Atualiza dados de notificacao do usuario", 
			nickname = "atualizaNotificacaoUsuario",
			code = 201,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad Request", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 404, message = "Not Found", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 415, message = "Unsupported Media Type", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 503, message = "Service Unavailable", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 504, message = "Gateway Timeout", response = ErrorDetailsDTO.class) })
	@PostMapping("/notificacaoUsuario")
	public ResponseEntity<String> atualizaNotificacaoUsuario(@RequestBody NotificacaoUsuarioDTO notificacaoUsuarioDTO) throws JsonProcessingException {
		log.info("Atualizando notificacao do usuario");
		notificacaoService.atualizaNotificacaoUsuario(notificacaoUsuarioDTO);
		log.info("Notificcao do usuario atualizada com sucesso");	
		return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString("NotificacaoUsuario cadastrada com sucesso."));
	}

	@ApiOperation(value = "Envia convite para amigo", 
			nickname = "enviaConviteAmigo",
			code = 200,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad Request", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 404, message = "Not Found", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 415, message = "Unsupported Media Type", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 503, message = "Service Unavailable", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 504, message = "Gateway Timeout", response = ErrorDetailsDTO.class) })
	@GetMapping("/enviaConviteAmigo/{email}/{nomeUsuario}")
	public ResponseEntity<String> enviaConviteAmigo(@PathVariable("email") String email, @PathVariable("nomeUsuario") String nomeUsuario) throws JsonProcessingException {
		log.info("Enviando convite para amigo");
		enviaEmailService.enviaConviteEmail(nomeUsuario, email);
		log.info("Convite enviado com sucesso");
		return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString("Convite enviado com sucesso."));
	}
}
