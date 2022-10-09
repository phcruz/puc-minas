package br.com.phc.pitaco.crawler.service;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.phc.pitaco.utilities.dto.crud.NotificacaoDTO;
import br.com.phc.pitaco.utilities.model.Notificacao;
import br.com.phc.pitaco.utilities.repository.NotificacaoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NotificacaoService {

	@Autowired
	private NotificacaoRepository notificacaoRepository;

	@Autowired
	private ModelMapper modelMapper;

	public List<Notificacao> findAll() {
		log.info("Lista todos os notificacaoes");
		return notificacaoRepository.findAll();
	}

	public Notificacao findById(Long id) {
		log.info("Busca notificacao de id [{}]", id);
		return notificacaoRepository.findByIdNotificacao(id);
	}

	public void createNotificacao(NotificacaoDTO notificacaoDTO) {
		Notificacao notificacao = modelMapper.map(notificacaoDTO, Notificacao.class);
		log.info("Insere novo notificacao");
		notificacao.setDataCadastro(new Date());
		notificacaoRepository.save(notificacao);
	}

	public void updateNotificacao(Long id, NotificacaoDTO notificacaoDTO) {
		log.info("Atualiza notificacao de id [{}]", id);
		Notificacao notificacao = this.findById(id);
		notificacao.setTitulo(notificacaoDTO.getTitulo());
		notificacao.setMensagem(notificacaoDTO.getMensagem());
		notificacaoRepository.save(notificacao);
	}

	public void deleteById(Long id) {
		log.info("Deleta notificacao de id [{}]", id);
		notificacaoRepository.deleteByIdNotificacao(id);
	}
}
