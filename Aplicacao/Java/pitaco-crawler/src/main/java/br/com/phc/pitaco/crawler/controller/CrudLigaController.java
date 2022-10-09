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

import br.com.phc.pitaco.crawler.service.LigaService;
import br.com.phc.pitaco.utilities.dto.crud.LigaDTO;
import br.com.phc.pitaco.utilities.model.Liga;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "crud")
@RestController
@RequestMapping("/liga")
public class CrudLigaController {

	@Autowired
	private LigaService ligaService;

	@ApiResponses(value = @ApiResponse(code = 200, message = "OK"))
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Liga>> findAll() {
		List<Liga> list = ligaService.findAll();
		return ResponseEntity.ok().body(list);
	}

	@ApiResponses(value = @ApiResponse(code = 200, message = "OK"))
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Liga> findById(@PathVariable("id") Long id) {
		Liga liga = ligaService.findById(id);
		return ResponseEntity.ok().body(liga);
	}

	@ApiResponses(value = @ApiResponse(code = 201, message = "Create"))
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> createLiga(@RequestBody LigaDTO ligaDTO) {
		ligaService.createLiga(ligaDTO);
		return ResponseEntity.ok().body(Boolean.TRUE);
	}

	@ApiResponses(value = @ApiResponse(code = 200, message = "OK"))
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> updateLiga(@PathVariable("id") Long id, @RequestBody LigaDTO ligaDTO) {
		ligaService.updateLiga(id, ligaDTO);
		return ResponseEntity.ok().body(Boolean.TRUE);
	}

	@ApiResponses(value = @ApiResponse(code = 204, message = "No content"))
	@DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id) {
		ligaService.deleteById(id);
		return ResponseEntity.ok().body(Boolean.TRUE);
	}
}
