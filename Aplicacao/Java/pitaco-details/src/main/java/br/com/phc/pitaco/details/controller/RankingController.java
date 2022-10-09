package br.com.phc.pitaco.details.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.phc.pitaco.details.service.RankingService;
import br.com.phc.pitaco.utilities.dto.ErrorDetailsDTO;
import br.com.phc.pitaco.utilities.dto.RankingDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(value = "ranking")
@RestController
@RequestMapping("/ranking")
public class RankingController {

	@Autowired
	private RankingService rankingService;
	
	@ApiOperation(value = "Recupera o ranking global paginado", 
			nickname = "listarRankingGlobalPaginado",
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
	@GetMapping("/listarRankingGlobalPaginado/{inicio}")
	public ResponseEntity<List<RankingDTO>> listarRankingGlobalPaginado(@PathVariable("inicio") Integer inicio) {
		log.info("Listando ranking global paginado");
		List<RankingDTO> listaRankingGlobal = rankingService.listarRankingGlobalPaginado(inicio);
		log.info("Lista de ranking global gerada com sucesso");
		return ResponseEntity.ok().body(listaRankingGlobal);
	}
	
	@ApiOperation(value = "Recupera o ranking de uma liga", 
			nickname = "listarRankingPorLigaPaginado",
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
	@GetMapping("/listarRankingPorLigaPaginado/{idLiga}/{inicio}")
	public ResponseEntity<List<RankingDTO>> listarRankingPorLigaPaginado(@PathVariable("idLiga") Long idLiga, @PathVariable("inicio") Integer inicio) {
		log.info("Listando ranking paginado por liga");
		List<RankingDTO> listaRanking = rankingService.listarRankingPorLigaPaginado(idLiga, inicio);
		log.info("Lista de ranking paginado por liga gerado com sucesso");
		return ResponseEntity.ok().body(listaRanking);
	}
	
	@ApiOperation(value = "Recupera o ranking de um grupo para uma liga", 
			nickname = "listarRankingPorGrupoLigaPaginado",
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
	@GetMapping("/listarRankingPorGrupoLigaPaginado/{idLiga}/{idGrupo}/{inicio}")
	public ResponseEntity<List<RankingDTO>> listarRankingPorGrupoLigaPaginado(@PathVariable("idLiga") Long idLiga, 
			@PathVariable("idGrupo") Long idGrupo, @PathVariable("inicio") Integer inicio) {
		log.info("Listando ranking por liga e grupo paginado");
		List<RankingDTO> listaRanking = rankingService.listarRankingPorGrupoLigaPaginado(idLiga, idGrupo, inicio);
		log.info("Lista de ranking por liga e grupo gerado com sucesso");
		return ResponseEntity.ok().body(listaRanking);
	}
	
	@ApiOperation(value = "Recupera o ranking global de um usuário", 
			nickname = "buscarRankingGlobalUsuarioId",
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
	@GetMapping("/buscarRankingGlobalUsuarioId/{idUsuario}")
	public ResponseEntity<RankingDTO> buscarRankingGlobalUsuarioId(@PathVariable("idUsuario") Long idUsuario) {
		log.info("Buscando ranking global do usuario");
		RankingDTO ranking = rankingService.buscarRankingGlobalUsuarioId(idUsuario);
		log.info("Ranking global do usuario gerado com sucesso");
		return ResponseEntity.ok().body(ranking);
	}
	
	@ApiOperation(value = "Recupera o ranking do usuario em uma liga", 
			nickname = "buscarRankingLigaUsuarioId",
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
	@GetMapping("/buscarRankingLigaUsuarioId/{idUsuario}/{idLiga}")
	public ResponseEntity<RankingDTO> buscarRankingLigaUsuarioId(@PathVariable("idUsuario") Long idUsuario, @PathVariable("idLiga") Long idLiga) {
		log.info("Buscando ranking da liga por usuario");
		RankingDTO ranking = rankingService.buscarRankingLigaUsuarioId(idUsuario, idLiga);
		log.info("Ranking da liga por usuario gerado com sucesso");
		return ResponseEntity.ok().body(ranking);
	}
	
	@ApiOperation(value = "Recupera o ranking de usuários por palpite", 
			nickname = "listarRankingUsuarioPorPalpite",
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
	@GetMapping("/listarRankingUsuarioPorPalpite/{idPartida}/{placarPalpite}/{inicio}")
	public ResponseEntity<List<RankingDTO>> listarRankingUsuarioPorPalpite(@PathVariable("idPartida") Long idPartida,
			@PathVariable("placarPalpite") String placarPalpite, @PathVariable("inicio") Integer inicio) {
		log.info("Listando ranking dos usuarios por palpite");
		List<RankingDTO> listaUsuarios = rankingService.listarRankingUsuarioPorPalpite(idPartida, placarPalpite, inicio);
		log.info("Lista de ranking de usuarios por palpite gerada com sucesso");	
		return ResponseEntity.ok().body(listaUsuarios);
	}
}
