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

import br.com.phc.pitaco.crawler.service.AvatarService;
import br.com.phc.pitaco.utilities.dto.crud.AvatarDTO;
import br.com.phc.pitaco.utilities.model.Avatar;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "crud")
@RestController
@RequestMapping("/avatar")
public class CrudAvatarController {

	@Autowired
	private AvatarService avatarService;

	@ApiResponses(value = @ApiResponse(code = 200, message = "OK"))
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Avatar>> findAll() {
		List<Avatar> list = avatarService.findAll();
		return ResponseEntity.ok().body(list);
	}

	@ApiResponses(value = @ApiResponse(code = 200, message = "OK"))
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Avatar> findById(@PathVariable("id") Long id) {
		Avatar avatar = avatarService.findById(id);
		return ResponseEntity.ok().body(avatar);
	}

	@ApiResponses(value = @ApiResponse(code = 201, message = "Create"))
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> createAvatar(@RequestBody AvatarDTO avatarDTO) {
		avatarService.createAvatar(avatarDTO);
		return ResponseEntity.ok().body(Boolean.TRUE);
	}

	@ApiResponses(value = @ApiResponse(code = 200, message = "OK"))
	@PutMapping(name = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> updateAvatar(@PathVariable("id") Long id, @RequestBody AvatarDTO avatarDTO) {
		avatarService.updateAvatar(id, avatarDTO);
		return ResponseEntity.ok().body(Boolean.TRUE);
	}

	@ApiResponses(value = @ApiResponse(code = 204, message = "No content"))
	@DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id) {
		avatarService.deleteById(id);
		return ResponseEntity.ok().body(Boolean.TRUE);
	}
}
