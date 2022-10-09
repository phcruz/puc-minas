package br.com.phc.pitaco.api.controller;

import java.util.List;

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

import br.com.phc.pitaco.api.service.ClassificacaoGeralService;
import br.com.phc.pitaco.api.service.CompraLigaService;
import br.com.phc.pitaco.api.service.LigaService;
import br.com.phc.pitaco.api.service.LigaUsuarioService;
import br.com.phc.pitaco.utilities.dto.ClassificacaoGeralDTO;
import br.com.phc.pitaco.utilities.dto.ErrorDetailsDTO;
import br.com.phc.pitaco.utilities.dto.LigaUsuarioDTO;
import br.com.phc.pitaco.utilities.model.CompraLiga;
import br.com.phc.pitaco.utilities.model.Liga;
import br.com.phc.pitaco.utilities.model.LigaUsuario;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(value = "liga")
@RestController
@RequestMapping("/liga")
public class LigaController {

	@Autowired
	private LigaService ligaService;
	
	@Autowired
	private CompraLigaService compraLigaService;
	
	@Autowired
	private LigaUsuarioService ligaUsuarioService;
	
	@Autowired
	private ClassificacaoGeralService classificacaoGeralService;
	
	@ApiOperation(value = "Listar todas as ligas", 
			nickname = "listarTodasLigas",
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
	@GetMapping("/listaLigas")
	public ResponseEntity<List<Liga>> listarTodasLigas() {
		log.info("Listando todas as ligas");
		List<Liga> listaLigas = ligaService.listarTodasLigas();
		log.info("Lista com todas as ligas geradas com sucesso");
		return ResponseEntity.ok().body(listaLigas);
	}
	
	@ApiOperation(value = "Listar ligas ativas", 
			nickname = "listarLigasAtivas",
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
	@GetMapping("/listarLigasAtivas")
	public ResponseEntity<List<Liga>> listarLigasAtivas() {
		log.info("Listando ligas ativas");
		List<Liga> listaLigas = ligaService.listarLigasAtivas();
		log.info("Lista com ligas ativas gerada com sucesso");
		return ResponseEntity.ok().body(listaLigas);
	}
	
	@ApiOperation(value = "Listar ligas ativas", 
			nickname = "listarLigasAtivas",
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
	@GetMapping("/listarLigasAtivasUsuario/{idUsuario}")
	public ResponseEntity<List<Liga>> listarLigasAtivas(@PathVariable("idUsuario") Long idUsuario) {
		log.info("Listando ligas ativas por usuario");
		List<Liga> listaLigas = ligaService.listarLigasAtivasUsuario(idUsuario);
		log.info("Lista com ligas ativas por usuario gerada com sucesso");
		return ResponseEntity.ok().body(listaLigas);
	}
	
	@ApiOperation(value = "Listar ligas para importar", 
			nickname = "listarLigasParaImportar",
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
	@GetMapping("/listarLigasParaImportar/{idUsuario}")
	public ResponseEntity<List<Liga>> listarLigasParaImportar(@PathVariable("idUsuario") Long idUsuario) {
		log.info("Listando ligas para importar");
		List<Liga> listaLigas = ligaService.listarLigasParaImportar(idUsuario);
		log.info("Lista com ligas parra importar gerada com sucesso");
		return ResponseEntity.ok().body(listaLigas);
	}
	
	@ApiOperation(value = "Listar ligas para importar", 
			nickname = "listarLigasParaImportarPaginado",
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
	@GetMapping("/listarLigasParaImportarPaginado/{idUsuario}/{inicio}")
	public ResponseEntity<List<Liga>> listarLigasParaImportarPaginado(@PathVariable("idUsuario") Long idUsuario,
			@PathVariable("inicio") Integer inicio) {
		log.info("Listando ligas para importar paginado");
		List<Liga> listaLigas = ligaService.listarLigasParaImportarPaginado(idUsuario, inicio);
		log.info("Lista com ligas ativas para importar gerada com sucesso");
		return ResponseEntity.ok().body(listaLigas);
	}
	
	@ApiOperation(value = "Lista ligas do usuario paginado", 
			nickname = "listarLigasUsuarioId",
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
	@GetMapping("/listarLigasUsuarioId/{idUsuario}")
	public ResponseEntity<List<Liga>> listarLigasUsuarioId(@PathVariable("idUsuario") Long idUsuario) {
		log.info("Listando ligas do usuario");
		List<Liga> listaLigas = ligaService.listarLigasUsuarioId(idUsuario);
		log.info("Lista de ligas do usuario gerada com sucesso");
		return ResponseEntity.ok().body(listaLigas);
	}
	
	@ApiOperation(value = "Recupera ligas do usuario", 
			nickname = "listarLigasUsuarioIdPaginado",
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
	@GetMapping("/listarLigasUsuarioIdPaginado/{idUsuario}/{inicio}")
	public ResponseEntity<List<Liga>> listarLigasUsuarioIdPaginado(@PathVariable("idUsuario") Long idUsuario, 
			@PathVariable("inicio") Integer inicio) {
		log.info("Listando ligas do usuario paginado");
		List<Liga> listaLigas = ligaService.listarLigasUsuarioIdPaginado(idUsuario, inicio);
		log.info("Lista de ligas do usuario gerado com sucesso");
		return ResponseEntity.ok().body(listaLigas);
	}
	
	@ApiOperation(value = "Cadastra liga usuario", 
			nickname = "cadastrarUsuario",
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
	@PostMapping(value = "/cadastroLigaUsuario", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> cadastrarUsuario(@RequestBody LigaUsuarioDTO ligaUsuarioDTO) throws JsonProcessingException {
		log.info("Cadastrando usuario");
		
		ligaUsuarioService.alterarLigaUsuario(ligaUsuarioDTO);
		LigaUsuario liga = ligaUsuarioService.buscarLigaUsuario(ligaUsuarioDTO);
		
		String mensagem = liga.isAtivo() ? "Liga importada com sucesso." : "Liga removida com sucesso.";
		log.info("Cadastro de usuario gerado com sucesso");
		return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(mensagem));
	}
	
	@ApiOperation(value = "Cadastrar compra de liga", 
			nickname = "inserirCompraLiga",
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
	@PostMapping("/cadastroCompraLiga")
	public ResponseEntity<String> inserirCompraLiga(@RequestBody CompraLiga compraLigaUsuario) throws JsonProcessingException {
		log.info("Cadastrando compra de liga");
		compraLigaService.inserirCompraLiga(compraLigaUsuario);
		log.info("Compra de liga realizada com sucesso");
		return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString("CompraLiga registrada com sucesso."));
	}
	
	@ApiOperation(value = "Lista classificacao geral", 
			nickname = "listarClassificacaoGeral",
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
	@GetMapping("/classificacaoGeral/{idLiga}/{inicio}")
	public ResponseEntity<List<ClassificacaoGeralDTO>> listarClassificacaoGeral(@PathVariable("idLiga") Long idLiga,
			@PathVariable("inicio") Integer inicio) {
		log.info("Listando classificacao geral");
		List<ClassificacaoGeralDTO> listaClassificacao = classificacaoGeralService.listarClassificacaoGeral(idLiga, inicio);
		log.info("Lista com classificacao geral gerada com sucesso");
		return ResponseEntity.ok().body(listaClassificacao);
	}
}
