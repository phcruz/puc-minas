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

import br.com.phc.pitaco.crawler.service.NotificacaoService;
import br.com.phc.pitaco.utilities.dto.crud.NotificacaoDTO;
import br.com.phc.pitaco.utilities.model.Notificacao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "crud")
@RestController
@RequestMapping("/notificacao")
public class CrudNotificacaoController {

	@Autowired
	private NotificacaoService notificacaoService;

	@ApiResponses(value = @ApiResponse(code = 200, message = "OK"))
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Notificacao>> findAll() {
		List<Notificacao> list = notificacaoService.findAll();
		return ResponseEntity.ok().body(list);
	}

	@ApiResponses(value = @ApiResponse(code = 200, message = "OK"))
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Notificacao> findById(@PathVariable("id") Long id) {
		Notificacao notificacao = notificacaoService.findById(id);
		return ResponseEntity.ok().body(notificacao);
	}

	@ApiResponses(value = @ApiResponse(code = 201, message = "Create"))
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> createNotificacao(@RequestBody NotificacaoDTO notificacaoDTO) {
		notificacaoService.createNotificacao(notificacaoDTO);
		return ResponseEntity.ok().body(Boolean.TRUE);
	}

	@ApiResponses(value = @ApiResponse(code = 200, message = "OK"))
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> updateNotificacao(@PathVariable("id") Long id, @RequestBody NotificacaoDTO notificacaoDTO) {
		notificacaoService.updateNotificacao(id, notificacaoDTO);
		return ResponseEntity.ok().body(Boolean.TRUE);
	}

	@ApiResponses(value = @ApiResponse(code = 204, message = "No content"))
	@DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id) {
		notificacaoService.deleteById(id);
		return ResponseEntity.ok().body(Boolean.TRUE);
	}
}
