package br.com.phc.pitaco.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.phc.pitaco.api.service.PartidaService;
import br.com.phc.pitaco.utilities.dto.ErrorDetailsDTO;
import br.com.phc.pitaco.utilities.dto.PartidaDTO;
import br.com.phc.pitaco.utilities.model.Partida;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(value = "partida")
@RestController
@RequestMapping("/partida")
public class PartidaController {

	@Autowired
	private PartidaService partidaService;
	
	@ApiOperation(value = "Recupera partidas de hoje", 
			nickname = "listarPartidasHojeLigasUsuarioPaginado",
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
	@GetMapping("/listarPartidasHojeLigasUsuarioPaginado/{idUsuario}/{inicio}")
	public ResponseEntity<List<PartidaDTO>> listarPartidasHojeLigasUsuarioPaginado(@PathVariable("idUsuario") Long idUsuario, @PathVariable("inicio") Integer inicio) {
		log.info("Listando partidas de hoje nas ligas do usuario");
		List<PartidaDTO> listaPartidasHoje = partidaService.listarPartidasHojeLigasUsuarioPaginado(idUsuario, inicio);
		log.info("Lista de partidas de hoje nas ligas do usuario gerada com sucesso");	
		return ResponseEntity.ok().body(listaPartidasHoje);
	}
	
	@ApiOperation(value = "Recupera partidas de hoje", 
			nickname = "listarPartidasHojeLigasUsuarioFimPaginado",
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
	@GetMapping("/listarPartidasHojeLigasUsuarioFimPaginado/{idUsuario}/{inicio}/{fim}")
	public ResponseEntity<List<PartidaDTO>> listarPartidasHojeLigasUsuarioFimPaginado(@PathVariable("idUsuario") Long idUsuario, @PathVariable("inicio") Integer inicio, @PathVariable("fim") Integer fim) {
		log.info("Listando partidas de hoje nas ligas do usuario");
		List<PartidaDTO> listaPartidasHoje = partidaService.listarPartidasHojeLigasUsuarioFimPaginado(idUsuario, inicio, fim);
		log.info("Lista partidas de hoje nas ligas do usuario");	
		return ResponseEntity.ok().body(listaPartidasHoje);
	}
	
	@ApiOperation(value = "Recupera partidas da liga do usuário", 
			nickname = "listarPartidasLigaUsuarioPaginado",
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
	@GetMapping("/listarPartidasLigaUsuarioPaginado/{idUsuario}/{idLiga}/{inicio}")
	public ResponseEntity<List<PartidaDTO>> listarPartidasLigaUsuarioPaginado(@PathVariable("idUsuario") Long idUsuario, @PathVariable("idLiga") Long idLiga, @PathVariable("inicio") Integer inicio) {
		log.info("Listando partidas da liga do usuario");
		List<PartidaDTO> listaPartidasLiga = partidaService.listarPartidasLigaUsuarioPaginado(idLiga, idUsuario, inicio);
		log.info("Lista de partidas da liga do usuario gerada com sucesso");	
		return ResponseEntity.ok().body(listaPartidasLiga);
	}
	
	@ApiOperation(value = "Recupera partidas da liga pos palpite", 
			nickname = "listarPartidasLigaUsuarioPosPalpitePaginado",
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
	@GetMapping("/listarPartidasLigaUsuarioPosPalpitePaginado/{idUsuario}/{idLiga}/{inicio}/{fim}")
	public ResponseEntity<List<PartidaDTO>> listarPartidasLigaUsuarioPosPalpitePaginado(@PathVariable("idUsuario") Long idUsuario, @PathVariable("idLiga") Long idLiga, @PathVariable("inicio") Integer inicio, @PathVariable("fim") Integer fim) {
		log.info("Listando partidas da liga do usuario pos palpite");
		List<PartidaDTO> listaPartidasLiga = partidaService.listarPartidasLigaUsuarioPosPalpitePaginado(idLiga, idUsuario, inicio, fim);
		log.info("Lista de partidas da liga do usuario por palpite gerada com sucesso");
		return ResponseEntity.ok().body(listaPartidasLiga);		
	}
	
	@ApiOperation(value = "Recupera partidas detalhe usuário", 
			nickname = "listarPalpitePartidaUsuarioDetalhePaginado",
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
	@GetMapping("/listarPalpitePartidaUsuarioDetalhePaginado/{idUsuario}/{idLiga}/{inicio}")
	public ResponseEntity<List<PartidaDTO>> listarPalpitePartidaUsuarioDetalhePaginado(@PathVariable("idUsuario") Long idUsuario, @PathVariable("idLiga") Long idLiga, @PathVariable("inicio") Integer inicio) {
		log.info("Listando palpites da partida do usuario detalhe");
		List<PartidaDTO> listaPartidasLiga =  partidaService.listarPalpitePartidaUsuarioDetalhePaginado(idLiga, idUsuario, inicio);
		log.info("Lista de palpites da partida do usuario detalhe gerado com sucesso");
		return ResponseEntity.ok().body(listaPartidasLiga);
	}
	
	@ApiOperation(value = "Recupera partidas perfil usuario", 
			nickname = "listarPalpitePartidaUsuarioPerfilPaginado",
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
	@GetMapping("/listarPalpitePartidaUsuarioPerfilPaginado/{idUsuario}/{idLiga}/{inicio}")
	public ResponseEntity<List<PartidaDTO>> listarPalpitePartidaUsuarioPerfilPaginado(@PathVariable("idUsuario") Long idUsuario, @PathVariable("idLiga") Long idLiga, @PathVariable("inicio") Integer inicio) {
		log.info("Listando palpites da partida no perfil do usuario");
		List<PartidaDTO> listaPartidasLiga = partidaService.listarPalpitePartidaUsuarioPerfilPaginado(idLiga, idUsuario, inicio);
		log.info("Lista de palpites da partida no perfil do usuario gerado com sucesso");
		return ResponseEntity.ok().body(listaPartidasLiga);
	}
	
	@ApiOperation(value = "Recupera o historico de partidas da equipe", 
			nickname = "listarHistoricoPartidasEquipe",
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
	@GetMapping("/listarHistoricoPartidasEquipe/{idLiga}/{idEquipe}/{inicio}")
	public ResponseEntity<List<PartidaDTO>> listarHistoricoPartidasEquipe(@PathVariable("idLiga") Long idLiga, @PathVariable("idEquipe") Long idEquipe, @PathVariable("inicio") Integer inicio) {
		log.info("Listando historico de partidas da equipe");
		List<PartidaDTO> listaPartidasLiga = partidaService.listarHistoricoPartidasEquipePaginado(idLiga, idEquipe, inicio);
		log.info("Historico de partidas da equipe gerada com sucesso");
		return ResponseEntity.ok().body(listaPartidasLiga);
	}
	
	@ApiOperation(value = "Recupera total de partidas do dia anterior", 
			nickname = "totalPartidasDiaAnterior",
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
	@GetMapping("/totalPartidasDiaAnterior")
	public ResponseEntity<Integer> buscarTotalPartidasDiaAnterior() {
		log.info("Buscando o total de partidas do dia anterior");
		Integer totalPartidas = partidaService.buscarTotalPartidasDiaAnterior();
		log.info("Total de partidas recuperado com sucesso");
		return ResponseEntity.ok().body(totalPartidas);
	}
	
	@ApiOperation(value = "Recupera uma partida pelo id", 
			nickname = "buscarPartidaId",
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
	@GetMapping("/buscarPartidaId/{idPartida}")
	public ResponseEntity<Partida> buscarPartidaId(@PathVariable("idPartida") Long idPartida) {
		log.info("Buscando partida por id");
		Partida partida = partidaService.buscarPartidaPorId(idPartida);
		log.info("Partida por id recuperada com sucesso");
		return ResponseEntity.ok().body(partida);
	}
}
