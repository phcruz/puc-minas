package br.com.phc.pitaco.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.phc.pitaco.api.service.GrupoService;
import br.com.phc.pitaco.api.service.GrupoUsuarioService;
import br.com.phc.pitaco.api.service.SolicitacaoGrupoUsuarioService;
import br.com.phc.pitaco.utilities.dto.ErrorDetailsDTO;
import br.com.phc.pitaco.utilities.dto.GrupoDTO;
import br.com.phc.pitaco.utilities.dto.SolicitacaoGrupoUsuarioDTO;
import br.com.phc.pitaco.utilities.model.Grupo;
import br.com.phc.pitaco.utilities.model.GrupoUsuario;
import br.com.phc.pitaco.utilities.model.SolicitacaoGrupoUsuario;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(value = "grupo")
@RestController
@RequestMapping("/grupo")
public class GrupoController {

	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private GrupoUsuarioService grupoUsuarioService;
	
	@Autowired
	private SolicitacaoGrupoUsuarioService solicitacaoGrupoUsuarioService;
	
	@ApiOperation(value = "Lista grupos para importar", 
			nickname = "listarGruposParaImportarPaginado",
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
	@GetMapping("/listarGruposParaImportarPaginado/{idUsuario}/{inicio}")
	public ResponseEntity<List<GrupoDTO>> listarGruposParaImportarPaginado(@PathVariable("idUsuario") Long idUsuario, @PathVariable("inicio") Integer inicio) {
		log.info("Listando grupos para importar");
		List<GrupoDTO> listaGrupos = grupoService.listarGruposParaImportarPaginado(idUsuario, inicio);
		log.info("Lista de grupos para importar gerado com sucesso");
		return ResponseEntity.ok().body(listaGrupos);
	}
	
	@ApiOperation(value = "Recupera grupos por id usuario", 
			nickname = "listarGruposUsuarioIdPaginado",
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
	@GetMapping("/listarGruposUsuarioIdPaginado/{idUsuario}/{inicio}")
	public ResponseEntity<List<GrupoDTO>> listarGruposUsuarioIdPaginado(@PathVariable("idUsuario") Long idUsuario, @PathVariable("inicio") Integer inicio) {
		log.info("Listando grupos do usuario");
		List<GrupoDTO> listaGrupos = grupoService.listarGruposUsuarioIdPaginado(idUsuario, inicio);
		log.info("Lista de grupos do usuario gerado com sucesso");
		return ResponseEntity.ok().body(listaGrupos);
	}
	
	@ApiOperation(value = "Recupera grupos de liga por usuario", 
			nickname = "listarGruposLigaUsuarioIdPaginado",
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
	@GetMapping("/listarGruposLigaUsuarioIdPaginado/{idUsuario}/{idLiga}/{inicio}")
	public ResponseEntity<List<GrupoDTO>> listarGruposLigaUsuarioIdPaginado(@PathVariable("idUsuario") Long idUsuario, @PathVariable("idLiga") Long idLiga, @PathVariable("inicio") Integer inicio) {
		log.info("Listando grupos liga por usuario");
		List<GrupoDTO> listaGrupos = grupoService.listarGruposLigaUsuarioIdPaginado(idUsuario, idLiga, inicio);
		log.info("Lista de grupos liga por usuario gerado com sucesso");
		return ResponseEntity.ok().body(listaGrupos);
	}
	
	@ApiOperation(value = "Recupera grupo por id", 
			nickname = "buscarGrupoId",
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
	@GetMapping("/buscarGrupoId/{idGrupo}")
	public ResponseEntity<Grupo> buscarGrupoId(@PathVariable("idGrupo") Long idGrupo) {
		log.info("Buscando grupo por id");
		Grupo grupo = grupoService.buscarGrupoId(idGrupo);
		log.info("Busca de grupo por id gerado com sucesso");
		return ResponseEntity.ok().body(grupo);
	}
	
	@ApiOperation(value = "Recupera lista de solicitacoes de grupo", 
			nickname = "listarSolicitacaoGrupoPaginado",
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
	@GetMapping("/listarSolicitacaoGrupoPaginado/{idGrupo}/{inicio}")
	public ResponseEntity<List<SolicitacaoGrupoUsuarioDTO>> listarSolicitacaoGrupoUsuario(@PathVariable("idGrupo") Integer idGrupo, @PathVariable("inicio") Integer inicio) {
		log.info("Listando solicitacaoGrupoUsuario");
		List<SolicitacaoGrupoUsuarioDTO> listaSolicitacoes = solicitacaoGrupoUsuarioService.listarSolicitacaoGrupoUsuarioPaginado(idGrupo, inicio);
		log.info("Lista de solicitacaoGrupoUsuario gerado com sucesso");
		return ResponseEntity.ok().body(listaSolicitacoes);
	}
	
	@ApiOperation(value = "Cadastra grupo", 
			nickname = "cadastroGrupo",
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
	@PostMapping("/cadastroGrupo")
	public ResponseEntity<String> cadastrarGrupo(@RequestBody Grupo novoGrupo) throws JsonProcessingException {
		log.info("Cadastrando grupo");
		grupoService.cadastrarGrupo(novoGrupo);
		log.info("Grupo cadastrado com sucesso");	
		return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString("Grupo criado com sucesso."));
	}
	
	@ApiOperation(value = "Edita o grupo", 
			nickname = "edicaoGrupo",
			code = 204,
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
	@PutMapping("/edicaoGrupo")
	public ResponseEntity<String> edicaoGrupo(@RequestBody Grupo grupo) throws JsonProcessingException {
		log.info("Editando grupo");
		grupoService.alterarGrupo(grupo);
		log.info("Edicao do grupo realizado com sucesso");
		return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString("Grupo alterado com sucesso."));
	}
	
	@ApiOperation(value = "Cadastra solicitacao de grupo para o usuario", 
			nickname = "cadastroSolicitacaoGrupoUsuario",
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
	@PostMapping("/cadastroSolicitacaoGrupoUsuario")
	public ResponseEntity<String> cadastrarSolicitacaoGrupoUsuario(@RequestBody SolicitacaoGrupoUsuario novaSolicitacao) throws JsonProcessingException {
		log.info("Cadastrando solicitacaoGrupoUsuario");
			
		solicitacaoGrupoUsuarioService.inserirSolicitacaoGrupoUsuario(novaSolicitacao);
		//registra uma noticacao para o admin do grupo
		grupoService.insereNotificacaoSolicParticipacaoGrupo(novaSolicitacao);

		log.info("SolicitacaoGrupoUsuario cadastrado com sucesso");
		return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString("Solicitação enviada com sucesso."));
	}
	
	@ApiOperation(value = "Altera solicitação de grupo usuario", 
			nickname = "alteraSolicitacaoGrupoUsuario",
			code = 204,
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
	@PutMapping("/alteraSolicitacaoGrupoUsuario")
	public ResponseEntity<String> alterarSolicitacaoGrupoUsuario(@RequestBody SolicitacaoGrupoUsuario solicitacao) throws JsonProcessingException {
		log.info("Alterando solicitacaoGrupoUsuario");
		
		grupoUsuarioService.alterarSolicitacaoGrupoUsuario(solicitacao);
		//registra uma noticacao para o admin do grupo
		grupoService.insereNotificacaoSolicParticipacaoGrupo(solicitacao);
		
		log.info("SolicitacaoGrupoUsuario alterado com sucesso");
		return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString("Solicitação alterada com sucesso."));
	}
	
	@ApiOperation(value = "Cadastra grupo para usuario", 
			nickname = "cadastroGrupoUsuario",
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
	@PostMapping("/cadastroGrupoUsuario")
	public ResponseEntity<String> cadastrarGrupoUsuario(@RequestBody GrupoUsuario novoGrupoUsuario) throws JsonProcessingException {
		log.info("Cadastrando grupoUsuario");
		grupoUsuarioService.cadastrarGrupoUsuario(novoGrupoUsuario);
		log.info("GrupoUsuario cadastrado com sucesso");
		return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString("Solicitação realizada com sucesso."));
	}
}
