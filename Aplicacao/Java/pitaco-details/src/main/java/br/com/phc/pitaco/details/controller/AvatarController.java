package br.com.phc.pitaco.details.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.phc.pitaco.details.service.AvatarService;
import br.com.phc.pitaco.utilities.dto.ErrorDetailsDTO;
import br.com.phc.pitaco.utilities.model.Avatar;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "avatar")
@RestController
@RequestMapping("/avatar")
public class AvatarController {

	private static final Logger log = LoggerFactory.getLogger(AvatarController.class);
	
	@Autowired
	private AvatarService avatarService;
	
	@ApiOperation(value = "Recupera avatares", 
			nickname = "listaAvataresPaginado",
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
	@GetMapping("/listaAvataresPaginado/{inicio}")
	public ResponseEntity<List<Avatar>> listarTodosAvataresPaginado(@PathVariable("inicio") int inicio) {
		log.info("Listando avatares");
		List<Avatar> listaAvatares = avatarService.listarTodosAvataresPaginado(inicio);
		log.info("Lista de avatares gerada com sucesso");
		return ResponseEntity.ok().body(listaAvatares);
	}
	
	@ApiOperation(value = "Altera avatar usuario", 
			nickname = "alterarAvatar",
			code = 202,
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
	@PutMapping("/alterarAvatar/{idAvatar}/{idUsuario}")
	public ResponseEntity<String> alterarAvatar(@PathVariable("idAvatar") Long idAvatar, @PathVariable("idUsuario") Long idUsuario) throws JsonProcessingException {
		log.info("Alterando avatar do usuario");
		avatarService.atualizaAvatar(idAvatar, idUsuario);
		log.info("Avatar alterado com sucesso");	
		return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString("Avatar atualizado com sucesso"));
	}
}

