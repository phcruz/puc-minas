package br.com.phc.pitaco.api.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.phc.pitaco.utilities.Constantes;
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

	public NotificacaoUsuario buscarNotificacaoUsuarioPorIdUsuario(Long idUsuario) {
		log.info("Acessando buscarNotificacaoUsuarioPorIdUsuario(Integer idUsuario)");
		return notificacaoUsuarioRepository.findOneByUsuarioIdUsuario(idUsuario);
	}
	
	public Notificacao verificaNotificacaoUsuario(Long idUsuario) {
		log.info("Acessando verificaNotificacaoUsuario(Integer idUsuario)");
		Notificacao notificacaoRecente = buscarNotificacao();
		NotificacaoUsuario notificacaoUsuario = buscarNotificacaoUsuarioPorIdUsuario(idUsuario);

		if(notificacaoUsuario != null
				&& notificacaoUsuario.getNotificacao().getIdNotificacao() == notificacaoRecente.getIdNotificacao()) {
			notificacaoRecente = null;
		}
		return notificacaoRecente;
	}
	
	public void atualizaNotificacaoUsuario(NotificacaoUsuario notificacaoUsuario) {
		log.info("Acessando atualizaNotificacaoUsuario(NotificacaoUsuario notificacaoUsuario)");
		NotificacaoUsuario notificacao = buscarNotificacaoUsuarioPorIdUsuario(notificacaoUsuario.getUsuario().getIdUsuario());

		if(notificacao != null) {
			//atualiza notificacao
			notificacao.setNotificacao(notificacaoUsuario.getNotificacao());
			notificacao.setDataRecebimento(Calendar.getInstance().getTime());
			notificacaoUsuarioRepository.save(notificacao);
		} else {
			//insere notificacao
			notificacaoUsuario.setDataRecebimento(Calendar.getInstance().getTime());
			notificacaoUsuarioRepository.save(notificacaoUsuario);
		}
	}

	public void inserirNotificacao(String nomeGrupo, Long idUsuarioAdmin) {
		log.info("Acessando inserirNotificacao(String nomeGrupo, Integer idUsuarioAdmin)");
		Notificacao n = new Notificacao();
		n.setTitulo("Solicitação de participação no grupo");
		n.setMensagem("Olá, gostaria de participar do grupo '" + nomeGrupo +"'. Poderia me aceitar?");
		n.setUsuario(new Usuario(idUsuarioAdmin));
		n.setDataCadastro(new Date());
		n.setToqueNotificacao(Constantes.JOGO_BONITO);
		
		notificacaoRepository.save(n);
	}
}
