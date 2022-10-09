package br.com.phc.pitaco.notification.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.phc.pitaco.utilities.Constantes;
import br.com.phc.pitaco.utilities.dto.NotificacaoDTO;
import br.com.phc.pitaco.utilities.dto.NotificacaoUsuarioDTO;
import br.com.phc.pitaco.utilities.model.Notificacao;
import br.com.phc.pitaco.utilities.model.NotificacaoUsuario;
import br.com.phc.pitaco.utilities.model.Usuario;
import br.com.phc.pitaco.utilities.repository.NotificacaoRepository;
import br.com.phc.pitaco.utilities.repository.NotificacaoUsuarioRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NotificacaoService {

	@Autowired
	private NotificacaoRepository notificacaoRepository;

	@Autowired
	private NotificacaoUsuarioRepository notificacaoUsuarioRepository;

	public Notificacao buscarNotificacao() {
		log.info("Acessando buscarNotificacao()");
		return notificacaoRepository.findTopByOrderByIdNotificacaoDesc();
	}

	public NotificacaoUsuario buscarNotificacaoUsuarioPorUsuario(Usuario usuario) {
		log.info("Acessando buscarNotificacaoUsuarioPorUsuario(Usuario usuario)");
		return notificacaoUsuarioRepository.findOneByUsuario(usuario);
	}

	public NotificacaoUsuario buscarNotificacaoUsuarioPorIdUsuario(Long idUsuario) {
		log.info("Acessando buscarNotificacaoUsuarioPorIdUsuario(Long idUsuario)");
		return notificacaoUsuarioRepository.findOneByUsuarioIdUsuario(idUsuario);
	}

	public NotificacaoDTO verificaNotificacaoUsuario(Long idUsuario) {
		log.info("Acessando verificaNotificacaoUsuario(Long idUsuario)");
		Notificacao notificacao = buscarNotificacao();
		NotificacaoUsuario notificacaoUsuario = buscarNotificacaoUsuarioPorIdUsuario(idUsuario);

		if (notificacaoUsuario != null
				&& notificacaoUsuario.getNotificacao().getIdNotificacao().equals(notificacao.getIdNotificacao())) {
			return null;
		}
		
		return NotificacaoDTO.builder()
				.idNotificacao(notificacao.getIdNotificacao())
				.idUsuario(notificacao.getUsuario().getIdUsuario())
				.mensagem(notificacao.getMensagem())
				.titulo(notificacao.getTitulo())
				.toqueNotificacao(Constantes.GRANDE_JOGADA).build();
	}

	public void atualizaNotificacaoUsuario(NotificacaoUsuarioDTO notificacaoUsuarioDTO) {
		log.info("Acessando atualizaNotificacaoUsuario(NotificacaoUsuario notificacaoUsuario)");
		NotificacaoUsuario notificacao = buscarNotificacaoUsuarioPorIdUsuario(notificacaoUsuarioDTO.getIdUsuario());

		if (notificacao != null) {
			// atualiza notificacao
			notificacao.setIdNotificacaoUsuario(notificacaoUsuarioDTO.getIdNotificacaoUsuario());
			notificacao.setDataRecebimento(Calendar.getInstance().getTime());
			notificacaoUsuarioRepository.save(notificacao);
		} else {
			// insere notificacao
			NotificacaoUsuario novaNotificacao = new NotificacaoUsuario();
			novaNotificacao.setUsuario(new Usuario(notificacaoUsuarioDTO.getIdUsuario()));
			novaNotificacao.setNotificacao(new Notificacao(notificacaoUsuarioDTO.getIdNotificacao()));
			novaNotificacao.setDataRecebimento(Calendar.getInstance().getTime());
			notificacaoUsuarioRepository.save(novaNotificacao);
		}
	}

	public void inserirNotificacao(String nomeGrupo, Long idUsuarioAdmin) {
		log.info("Acessando inserirNotificacao(String nomeGrupo, Long idUsuarioAdmin)");
		Notificacao n = new Notificacao();
		n.setTitulo("Solicitação de participação no grupo");
		n.setMensagem("Olá, gostaria de participar do grupo '" + nomeGrupo + "'. Poderia me aceitar?");
		n.setUsuario(new Usuario(idUsuarioAdmin));
		n.setDataCadastro(new Date());
		n.setToqueNotificacao(Constantes.JOGO_BONITO);

		notificacaoRepository.save(n);
	}
}
