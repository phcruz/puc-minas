package br.com.phc.pitaco.details.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.phc.pitaco.details.service.AcessoService;
import br.com.phc.pitaco.utilities.dto.AcessoDTO;
import br.com.phc.pitaco.utilities.dto.ErrorDetailsDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(value = "acesso")
@RestController
@RequestMapping("/acesso")
public class AcessoController {

	@Autowired
	private AcessoService acessoService;

	@ApiOperation(value = "Contador de acessos de usuario", nickname = "appAcesso", code = 201, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad Request", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 404, message = "Not Found", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 415, message = "Unsupported Media Type", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 503, message = "Service Unavailable", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 504, message = "Gateway Timeout", response = ErrorDetailsDTO.class) })
	@PostMapping("/plataforma")
	public ResponseEntity<String> contadorAcessoUsuario(@RequestBody AcessoDTO acessoDTO, HttpServletRequest request) throws JsonProcessingException {
		log.info("Acessando contadorAcessoUsuario()");
		String mensagem = acessoService.contagemAcessoPlataforma(acessoDTO, request);
		return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(mensagem));
	}
}
