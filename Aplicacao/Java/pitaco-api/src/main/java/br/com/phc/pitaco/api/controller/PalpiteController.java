package br.com.phc.pitaco.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.phc.pitaco.api.service.PalpiteService;
import br.com.phc.pitaco.utilities.dto.ErrorDetailsDTO;
import br.com.phc.pitaco.utilities.dto.PalpiteDTO;
import br.com.phc.pitaco.utilities.model.Palpite;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(value = "palpite")
@RestController
@RequestMapping("/palpite")
public class PalpiteController {

	@Autowired
	private PalpiteService palpiteService;
	
	@ApiOperation(value = "Cadastra palpite usuario", 
			nickname = "palpite",
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
	@PostMapping("/cadastro")
	public ResponseEntity<Palpite> cadastrarPalpitePartidaUsuario(@RequestBody PalpiteDTO palpiteDTO) {
		log.info("Cadastrando palpite na partida");
		palpiteService.verificaPeriodoPalpite(palpiteDTO);
		palpiteService.atualizaPalpitePartida(palpiteDTO);
		Palpite palpite = palpiteService.buscarPalpiteUsuarioPartida(palpiteDTO);

		log.info("Sucesso ao cadastrar palpite");
		return ResponseEntity.ok().body(palpite);
	}
}
