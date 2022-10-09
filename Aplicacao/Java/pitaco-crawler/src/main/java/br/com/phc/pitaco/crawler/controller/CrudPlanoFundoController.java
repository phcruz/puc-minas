package br.com.phc.pitaco.crawler.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.phc.pitaco.crawler.service.PlanoFundoService;
import br.com.phc.pitaco.utilities.dto.crud.PlanoFundoDTO;
import br.com.phc.pitaco.utilities.model.PlanoFundo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "crud")
@RestController
@RequestMapping("/planoFundo")
public class CrudPlanoFundoController {

	@Autowired
	private PlanoFundoService planoFundoService;

	@ApiResponses(value = @ApiResponse(code = 200, message = "OK"))
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PlanoFundo>> findAll() {
		List<PlanoFundo> list = planoFundoService.findAll();
		return ResponseEntity.ok().body(list);
	}

	@ApiResponses(value = @ApiResponse(code = 200, message = "OK"))
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PlanoFundo> findById(@PathVariable("id") Long id) {
		PlanoFundo planoFundo = planoFundoService.findById(id);
		return ResponseEntity.ok().body(planoFundo);
	}

	@ApiResponses(value = @ApiResponse(code = 201, message = "Create"))
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> createPlanoFundo(@RequestBody PlanoFundoDTO planoFundoDTO) {
		planoFundoService.createPlanoFundo(planoFundoDTO);
		return ResponseEntity.ok().body(Boolean.TRUE);
	}

	@ApiResponses(value = @ApiResponse(code = 200, message = "OK"))
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> updatePlanoFundo(@PathVariable("id") Long id, @RequestBody PlanoFundoDTO planoFundoDTO) {
		planoFundoService.updatePlanoFundo(id, planoFundoDTO);
		return ResponseEntity.ok().body(Boolean.TRUE);
	}

	@ApiResponses(value = @ApiResponse(code = 204, message = "No content"))
	@DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id) {
		planoFundoService.deleteById(id);
		return ResponseEntity.ok().body(Boolean.TRUE);
	}
}
