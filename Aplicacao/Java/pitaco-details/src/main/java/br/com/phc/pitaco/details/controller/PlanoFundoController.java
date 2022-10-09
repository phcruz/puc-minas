package br.com.phc.pitaco.details.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.phc.pitaco.details.service.PlanoFundoService;
import br.com.phc.pitaco.utilities.dto.ErrorDetailsDTO;
import br.com.phc.pitaco.utilities.dto.PlanoFundoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(value = "planoFundo")
@RestController
@RequestMapping("/planoFundo")
public class PlanoFundoController {

	@Autowired
	private PlanoFundoService planoFundoService;

	@ApiOperation(value = "Recupera uma imagem aleatória para plano de fundo", nickname = "imagemPlanoFundo", code = 200, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = PlanoFundoDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 404, message = "Not Found", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 415, message = "Unsupported Media Type", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 503, message = "Service Unavailable", response = ErrorDetailsDTO.class),
			@ApiResponse(code = 504, message = "Gateway Timeout", response = ErrorDetailsDTO.class) })
	@GetMapping("/imagem")
	public ResponseEntity<PlanoFundoDTO> getImagemPlanoFundo() {
		log.info("Gerando imagem de plano de fundo");
		PlanoFundoDTO planoFundo = planoFundoService.getImagemPlanoFundo();
		log.info("Imagem de plano de fundo gerada com sucesso");
		return ResponseEntity.ok().body(planoFundo);
	}
}
