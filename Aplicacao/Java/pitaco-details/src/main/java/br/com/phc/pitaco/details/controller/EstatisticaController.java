package br.com.phc.pitaco.details.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.phc.pitaco.details.service.EstatisticaService;
import br.com.phc.pitaco.utilities.dto.ErrorDetailsDTO;
import br.com.phc.pitaco.utilities.dto.EstatisticaPartidaDTO;
import br.com.phc.pitaco.utilities.dto.EstatisticaPlacarDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(value = "estatistica")
@RestController
@RequestMapping("/estatistica")
public class EstatisticaController {

	@Autowired
	private EstatisticaService estatisticaService;
	
	@ApiOperation(value = "Recupera a lista de estatistica da partida", 
			nickname = "estatisticaPartida",
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
	@GetMapping("/estatisticaPartida/{idPartida}")
	public ResponseEntity<EstatisticaPartidaDTO> buscarEstatisticaPartidaId(@PathVariable("idPartida") Long idPartida) {
		log.info("Listando estatisticas da partida");
		EstatisticaPartidaDTO estatisticaPartida = estatisticaService.buscarEstatisticaPartidaId(idPartida);
		log.info("Lista estatisticas da partida gerada com sucesso");
		return ResponseEntity.ok().body(estatisticaPartida);
	}
	
	@ApiOperation(value = "Recupera a lista de placar com estatistica", 
			nickname = "estatisticaPlacar",
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
	@GetMapping("/estatisticaPlacar/{idPartida}/{inicio}")
	public ResponseEntity<List<EstatisticaPlacarDTO>> listarEstatisticaPlacarPartidaId(@PathVariable("idPartida") Long idPartida, @PathVariable("inicio") Integer inicio) {
		log.info("Listando estatisticas do placar");
		List<EstatisticaPlacarDTO> estatisticaPlacar = estatisticaService.listarEstatisticaPlacarPartidaIdPaginado(idPartida, inicio);
		log.info("Lista estatisticas do placar gerada com sucesso");
		return ResponseEntity.ok().body(estatisticaPlacar);
	}
}
