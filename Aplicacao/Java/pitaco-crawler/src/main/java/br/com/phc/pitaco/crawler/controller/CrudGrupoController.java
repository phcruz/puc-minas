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

import br.com.phc.pitaco.crawler.service.GrupoService;
import br.com.phc.pitaco.utilities.dto.crud.GrupoDTO;
import br.com.phc.pitaco.utilities.model.Grupo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "crud")
@RestController
@RequestMapping("/grupo")
public class CrudGrupoController {

	@Autowired
	private GrupoService grupoService;

	@ApiResponses(value = @ApiResponse(code = 200, message = "OK"))
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Grupo>> findAll() {
		List<Grupo> list = grupoService.findAll();
		return ResponseEntity.ok().body(list);
	}

	@ApiResponses(value = @ApiResponse(code = 200, message = "OK"))
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Grupo> findById(@PathVariable("id") Long id) {
		Grupo grupo = grupoService.findById(id);
		return ResponseEntity.ok().body(grupo);
	}

	@ApiResponses(value = @ApiResponse(code = 201, message = "Create"))
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> createGrupo(@RequestBody GrupoDTO grupoDTO) {
		grupoService.createGrupo(grupoDTO);
		return ResponseEntity.ok().body(Boolean.TRUE);
	}

	@ApiResponses(value = @ApiResponse(code = 200, message = "OK"))
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> updateGrupo(@PathVariable("id") Long id, @RequestBody GrupoDTO grupoDTO) {
		grupoService.updateGrupo(id, grupoDTO);
		return ResponseEntity.ok().body(Boolean.TRUE);
	}

	@ApiResponses(value = @ApiResponse(code = 204, message = "No content"))
	@DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id) {
		grupoService.deleteById(id);
		return ResponseEntity.ok().body(Boolean.TRUE);
	}
}
