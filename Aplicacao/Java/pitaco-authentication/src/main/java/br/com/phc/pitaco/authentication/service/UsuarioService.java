package br.com.phc.pitaco.authentication.service;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.phc.pitaco.utilities.Constantes;
import br.com.phc.pitaco.utilities.dto.UsuarioDTO;
import br.com.phc.pitaco.utilities.exception.CustomException;
import br.com.phc.pitaco.utilities.indicador.Role;
import br.com.phc.pitaco.utilities.model.Avatar;
import br.com.phc.pitaco.utilities.model.Credencial;
import br.com.phc.pitaco.utilities.model.Usuario;
import br.com.phc.pitaco.utilities.repository.UsuarioRepository;
import br.com.phc.pitaco.utilities.util.UtilMetodos;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private EmailService emailService;

	public Usuario buscaUsuarioEmail(String email) {
		log.info("Acessando buscaUsuarioEmail(String email)");
		return usuarioRepository.findByEmail(email);
	}
	
	public Usuario buscaUsuarioPorId(Long idUsuario) {
		log.info("Acessando buscaUsuarioPorId(Long idUsuario");
		return usuarioRepository.findByIdUsuario(idUsuario);
	}

	public Usuario validarCrendenciais(Credencial credencial) {
		log.info("Acessando validarCrendenciais(Credencial credencial)");
		Usuario user = buscaUsuarioEmail(credencial.getEmail());

		if (user == null) {
			throw new CustomException("Usuário/senha inválido", HttpStatus.PRECONDITION_FAILED);
		} else {
			if (!credencial.getEmail().equals(user.getEmail()) || !credencial.getSenha().equals(user.getSenha())) {
				throw new CustomException("Crendencias inválidas!", HttpStatus.PRECONDITION_FAILED);
			}
		}
		return user;

	}

	public void inserirUsuario(Usuario novoUsuario) {
		log.info("Acessando inserirUsuario(Usuario novoUsuario)");
		Usuario vUser = usuarioRepository.findByEmail(novoUsuario.getEmail());
		if(vUser != null) {
			String mensagem = String.format("INFO: Este e-mail já foi cadastrado -> %s", vUser.getEmail());
			log.info(mensagem);
			throw new CustomException("Este e-mail já foi cadastrado.", HttpStatus.PRECONDITION_FAILED);
		}
		
		novoUsuario.setPerfilAcesso(Role.ROLE_USUARIO.getCodigo());
		novoUsuario.setAvatar(new Avatar(Constantes.ID_IMAGEM_PADRAO));
		novoUsuario.setDataCadastro(Calendar.getInstance().getTime());

		usuarioRepository.save(novoUsuario);		
	}
	
	public void atualizarDadosUsuario(Usuario user) {
		log.info("Acessando atualizarDadosUsuario(Usuario user)");
		usuarioRepository.save(user);		
	}

	public void alterarSenhaUsuario(Usuario user) {
		log.info("Acessando alterarSenhaUsuario(Usuario user)");
		usuarioRepository.save(user);
	}

	public List<Usuario> listarUsuarioPorNome(String nomeUsuario, Integer inicio, Long idGrupo) {
		log.info("Acessando listarUsuarioPorNome(String nomeUsuario, Integer inicio, Integer idGrupo)");
		return usuarioRepository.listarUsuarioPorNome(nomeUsuario, inicio, idGrupo);
	}

	public void atualizaAvatarUsuario(Long idAvatar, Long idUsuario) {
		log.info("Acessando atualizaAvatarUsuario(String urlImagem, Integer idUsuario)");
		usuarioRepository.atualizaAvatarUsuario(new Avatar(idAvatar), idUsuario);
	}
	
	public String recuperarSenhaUsuario(String email) {
		log.info("Acessando recuperarSenhaUsuario(String email)");
		String msg;
		Usuario user = buscaUsuarioEmail(email);

		if (user != null) {
			// gera uma nova senha e faz a criptografia
			String novaSenha = UtilMetodos.geraNovaSenha();
			String novaSenhaCriptografada = UtilMetodos.criptografaSenha(novaSenha);
			
			//seta a nova senha e atualiza no banco de dados
			user.setSenha(novaSenhaCriptografada);
			alterarSenhaUsuario(user);
			
			emailService.recuparaSenhaEmail(user, novaSenha);
			
			msg = "Enviamos uma nova senha para o email informado.";
		} else {
			msg = "E-mail não encontrado. Verifique se está correto.";
		}
		return msg;
	}
	
	public void ativarUsuario(Usuario user) {
		log.info("Acessando ativarUsuario(Usuario user)");
		usuarioRepository.save(user);		
	}
	
	public void enviaEmailValidacaoCadastro(String nome, String email, String token) {
		log.info("Acessando enviaEmailValidacaoCadastro(String nome, String email, String token)");
		emailService.enviaValidacaoEmail(nome, email, token);
	}

	public String getEmailHtmlCadastroAtivo() {
		log.info("Acessando getEmailHtmlCadastroAtivo()");
		return emailService.getEmailHtmlCadastroAtivo();
	}
	
	public Integer buscarQtdUsuarioInativo() {
		log.info("Acessando buscarQtdUsuarioInativo()");
		return usuarioRepository.buscarQtdUsuarioInativo();
	}

	public List<Usuario> listarUsuariosInativos() {
		log.info("Acessando listarUsuariosInativos()");
		return usuarioRepository.findByAtivoFalse();
	}
	
	public UsuarioDTO buscaUsuarioConvertDTO(String email) {
		UsuarioDTO dto = new UsuarioDTO();
		Usuario user = buscaUsuarioEmail(email);
		if (user != null) {
			dto.setEmail(user.getEmail());
			dto.setNome(user.getNome());
			dto.setTransaction(UUID.randomUUID().toString() + "#" + user.getIdUsuario());
		}
		
		return dto;
	}
	
}
