package br.com.phc.pitaco.authentication.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.phc.pitaco.authentication.service.LigaService;
import br.com.phc.pitaco.authentication.service.UsuarioService;
import br.com.phc.pitaco.utilities.Constantes;
import br.com.phc.pitaco.utilities.config.JwtTokenProvider;
import br.com.phc.pitaco.utilities.dto.ErrorDetailsDTO;
import br.com.phc.pitaco.utilities.dto.UsuarioDTO;
import br.com.phc.pitaco.utilities.indicador.Role;
import br.com.phc.pitaco.utilities.model.Credencial;
import br.com.phc.pitaco.utilities.model.Usuario;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(value = "usuario")
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private LigaService ligaService;

	@ApiOperation(value = "Realiza login", 
			nickname = "login",
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
	@ApiImplicitParams({@ApiImplicitParam(name = "x-register-public", paramType = "header",
						defaultValue = "cmVnaXN0ZXItcHVibGljOmFwcGxpY2F0aW9uLXdlYjoyMi8wNC8yMDIwIDEwOjMw", required = true) })
	@PostMapping("/login")
	public ResponseEntity<Usuario> login(@RequestBody Usuario user) {
		log.info("Realizando o login");

		Credencial crendencial = new Credencial(user.getEmail(), user.getSenha());
		// Verifica se a crendencial é valida, se não for vai dar exception
		user = usuarioService.validarCrendenciais(crendencial);
		String token = jwtTokenProvider.gerarToken(user.getEmail(),
				new ArrayList<>(Arrays.asList(Role.getRoleCodigo(user.getPerfilAcesso()))));

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set(Constantes.HEADER_STRING, Constantes.TOKEN_PREFIXO + token);

		log.info("Login realizado com sucesso");
		return ResponseEntity.ok().headers(responseHeaders).body(user);
	}

	@ApiOperation(value = "Cadastrar usuario", 
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
	@ApiImplicitParams({@ApiImplicitParam(name = "x-register-public", paramType = "header",
						defaultValue = "cmVnaXN0ZXItcHVibGljOmFwcGxpY2F0aW9uLXdlYjoyMi8wNC8yMDIwIDEwOjMw", required = true) })
	@PostMapping("/cadastro")
	public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario novoUsuario) {
		log.info("Cadastrando usuario");
		
		usuarioService.inserirUsuario(novoUsuario);
		Usuario user = usuarioService.buscaUsuarioEmail(novoUsuario.getEmail());

		String token = jwtTokenProvider.gerarToken(novoUsuario.getEmail(),
				new ArrayList<>(Arrays.asList(Role.ROLE_USUARIO)));

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set(Constantes.HEADER_STRING, Constantes.TOKEN_PREFIXO + token);

		if(novoUsuario.getLocalCadastro().equals(Constantes.APLICATIVO)) {
			usuarioService.enviaEmailValidacaoCadastro(novoUsuario.getNome(), novoUsuario.getEmail(), token);
		} else {
			ligaService.insereLigaAtivaNovoUsuario(user.getIdUsuario());
		}
		
		log.info("Usuario cadastrado com sucesso");
		// Retorna um reponse com o status 200 OK com o token gerado
		return ResponseEntity.ok().headers(responseHeaders).body(user);
	}

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
	@PutMapping("/atualizarUsuario")
	public ResponseEntity<String> atualizarDadosUsuario(@RequestBody Usuario user) throws JsonProcessingException {
		log.info("Atualizando dados do usuario");
		usuarioService.atualizarDadosUsuario(user);
		log.info("Usuario atualizado com sucesso");
		
		if (Objects.nonNull(user.getIdUsuario()) && user.getIdUsuario() > 0) {
			user = usuarioService.buscaUsuarioPorId(user.getIdUsuario());
			String token = jwtTokenProvider.gerarToken(user.getEmail(),
					new ArrayList<>(Arrays.asList(Role.getRoleCodigo(user.getPerfilAcesso()))));

			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set(Constantes.HEADER_STRING, Constantes.TOKEN_PREFIXO + token);

			return ResponseEntity.ok().headers(responseHeaders).body(new ObjectMapper().writeValueAsString("Dados atualizados com sucesso"));	
		} else {
			return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString("Dados atualizados com sucesso"));
		}
	}

	@ApiOperation(value = "Revalida token", 
			nickname = "revalidarTokenUsuario",
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
	@ApiImplicitParams({@ApiImplicitParam(name = "x-register-public", paramType = "header",
						defaultValue = "cmVnaXN0ZXItcHVibGljOmFwcGxpY2F0aW9uLXdlYjoyMi8wNC8yMDIwIDEwOjMw", required = true) })
	@PostMapping("/revalidaToken")
	public ResponseEntity<Usuario> revalidarTokenUsuario(@RequestBody Usuario user) {
		log.info("Revalidando token do usuario");
		
		String token = jwtTokenProvider.gerarToken(user.getEmail(),
				new ArrayList<>(Arrays.asList(Role.getRoleCodigo(user.getPerfilAcesso()))));

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set(Constantes.HEADER_STRING, Constantes.TOKEN_PREFIXO + token);

		log.info("Token do usuario revalidado com sucesso");
		// Retorna um reponse com o status 200 OK com o token gerado
		return ResponseEntity.ok().headers(responseHeaders).body(user);
	}
	
	@ApiOperation(value = "Buscar usuario por email", 
				nickname = "buscarUsuarioEmail",
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
	@ApiImplicitParams({@ApiImplicitParam(name = "x-register-public", paramType = "header",
						defaultValue = "cmVnaXN0ZXItcHVibGljOmFwcGxpY2F0aW9uLXdlYjoyMi8wNC8yMDIwIDEwOjMw", required = true) })
	@GetMapping("/buscarEmailUsuario")
	public ResponseEntity<Usuario> buscarUsuarioEmail(@RequestParam("email") String email) throws JsonProcessingException {
		log.info("Buscando usuario por email: {}", email);
		Usuario user = usuarioService.buscaUsuarioEmail(email);
		log.info("Busca de email realizada com sucesso");
		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Alterar senha do usuario", 
			nickname = "alterarUsuarioSenha",
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
	@PutMapping("/alterarSenhaUsuario")
	public ResponseEntity<String> alterarUsuarioSenha(@RequestBody Usuario user) throws JsonProcessingException {
		log.info("Alterando senha do usuario");
		usuarioService.alterarSenhaUsuario(user);
		log.info("Senha do usuario alterada com sucesso");
		return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString("Dados atualizados com sucesso."));
	}

	@ApiOperation(value = "Listar usuario por nome", 
			nickname = "listarUsuarioPorNome",
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
	@GetMapping("/listarUsuarioPorNome/{nomeUsuario}/{inicio}/{idGrupo}")
	public ResponseEntity<List<Usuario>> listarUsuarioPorNome(@PathVariable("nomeUsuario") String nomeUsuario, 
			@PathVariable("inicio") Integer inicio, @PathVariable("idGrupo") Long idGrupo) {
		log.info("Listando usuario por nome");
		List<Usuario> listaUsuarios = usuarioService.listarUsuarioPorNome(nomeUsuario, inicio, idGrupo);
		log.info("Lista de usuario gerada com sucesso");
		return ResponseEntity.ok().body(listaUsuarios);
	}
	
	@ApiOperation(value = "Recupera senha do usuario", 
			nickname = "recuperaSenhaUsuario",
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
	@ApiImplicitParams({@ApiImplicitParam(name = "x-register-public", paramType = "header",
						defaultValue = "cmVnaXN0ZXItcHVibGljOmFwcGxpY2F0aW9uLXdlYjoyMi8wNC8yMDIwIDEwOjMw", required = true) })
	@GetMapping("/recuperaSenhaUsuario")
	public ResponseEntity<String> recuperaSenhaUsuario(@RequestParam("email") String email) throws JsonProcessingException {
		log.info("Recuperando senha do usuario");
		String msg = usuarioService.recuperarSenhaUsuario(email);
		log.info("Recuperacao do usuario realizada com sucesso");
		return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(msg));
	}

	@ApiOperation(value = "Ativa usuario", 
			nickname = "ativarUsuario",
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
	@ApiImplicitParams({@ApiImplicitParam(name = "x-register-public", paramType = "header",
						defaultValue = "cmVnaXN0ZXItcHVibGljOmFwcGxpY2F0aW9uLXdlYjoyMi8wNC8yMDIwIDEwOjMw", required = true) })
	@GetMapping(value = "/ativarUsuario", produces = "text/html;charset=UTF-8")
	public ResponseEntity<String> ativarUsuario(@RequestParam("token") String token) {
		log.info("Ativando usuario");
		
		String msg = usuarioService.getEmailHtmlCadastroAtivo();
		String email = jwtTokenProvider.getUsername(token);
		
		Usuario user = usuarioService.buscaUsuarioEmail(email);
		// verifica usuario cadastrado, depois atualiza o status para ativo e insere ligas ativas para novo usuario
		if (user != null && !user.isAtivo()) {
			usuarioService.ativarUsuario(user);
			ligaService.insereLigaAtivaNovoUsuario(user.getIdUsuario());
		}

		log.info("Ativacao do usuario realizada com sucesso");
		return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(msg);
	}
	
	@ApiOperation(value = "Recupera informacoes de usuario", 
			nickname = "getUserInfo",
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
	@GetMapping("/userInfo")
	public ResponseEntity<UsuarioDTO> getUserInfo(@RequestHeader("Authorization") String bearerToken) {
		log.info("Obtendo dados do usuario via token");
		
		String jwt = "";
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			jwt = bearerToken.substring(7);
		}
		UsuarioDTO user = jwtTokenProvider.getInfosUserJWT(jwt);
		
		log.info("Dados do usuario recuperados com sucesso");
		return ResponseEntity.ok().body(user);
	}
}
