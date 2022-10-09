package br.com.phc.pitaco.details.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.phc.pitaco.details.service.PontuadorRodadaService;
import br.com.phc.pitaco.utilities.dto.ErrorDetailsDTO;
import br.com.phc.pitaco.utilities.dto.PontuadorRodadaDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(value = "pontuadorRodada")
@RestController
@RequestMapping("/pontuadorRodada")
public class PontuadorRodadaController {
	
	@Autowired
	private PontuadorRodadaService pontuadorRodadaService;
	
	@ApiOperation(value = "Recupera dados dos pontuadores da rodada", 
			nickname = "listarPontuadorRodada",
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
	@GetMapping("/listarPontuadorRodada/{inicio}")
	public ResponseEntity<List<PontuadorRodadaDTO>> listarPontuadorRodadaPaginado(@PathVariable("inicio") Integer inicio) {
		log.info("Listando pontuador da rodada");
		List<PontuadorRodadaDTO> listaUsuarios = pontuadorRodadaService.listarPontuadorRodadaPaginado(inicio);
		log.info("Lista de pontuadores da rodada gerada com sucesso");
		return ResponseEntity.ok().body(listaUsuarios);
	}
	
	@ApiOperation(value = "Recupera dados dos pontuadores da rodada de um grupo", 
			nickname = "listarPontuadorRodadaGrupo",
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
	@GetMapping("/listarPontuadorRodadaGrupo/{idGrupo}/{inicio}")
	public ResponseEntity<List<PontuadorRodadaDTO>> listarPontuadorRodadaGrupoPaginado(@PathVariable("idGrupo") Long idGrupo, @PathVariable("inicio") Integer inicio) {
		log.info("Listando pontuador da rodada no grupo");
		List<PontuadorRodadaDTO> listaUsuarios = pontuadorRodadaService.listarPontuadorRodadaGrupoPaginado(idGrupo, inicio);
		log.info("Lista de pontuador no grupo gerada com sucesso");
		return ResponseEntity.ok().body(listaUsuarios);
	}
}
