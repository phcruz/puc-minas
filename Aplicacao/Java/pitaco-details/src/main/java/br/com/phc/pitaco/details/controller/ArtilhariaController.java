package br.com.phc.pitaco.details.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.phc.pitaco.details.service.ArtilhariaService;
import br.com.phc.pitaco.utilities.dto.ArtilheiroDTO;
import br.com.phc.pitaco.utilities.dto.ErrorDetailsDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/artilharia")
public class ArtilhariaController {

	@Autowired
	private ArtilhariaService artilhariaService;

	@ApiOperation(value = "Busca informações aobre a artilharia da Liga", 
			nickname = "artilharia",
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
	@GetMapping("/{idLiga}")
	public ResponseEntity<List<ArtilheiroDTO>> buscarArtilhariaLiga(@PathVariable("idLiga") Integer idLiga) throws JsonProcessingException {
		log.info("Acessando buscarArtilhariaLiga()");
		return ResponseEntity.ok().body(artilhariaService.buscarArtilhariaLiga(idLiga));
	}
}
