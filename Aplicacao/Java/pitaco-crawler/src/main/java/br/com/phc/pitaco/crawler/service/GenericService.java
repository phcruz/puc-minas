package br.com.phc.pitaco.crawler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.phc.pitaco.utilities.dto.EmailTimeCoracaoDTO;
import br.com.phc.pitaco.utilities.repository.jdbc.GenericRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GenericService {

	@Autowired
	private GenericRepository genericRepository;
	
	public List<EmailTimeCoracaoDTO> listaDadosEmailTimeCoracao() {
		log.info("Acessando listaDadosEmailTimeCoracao()");
		return genericRepository.listaDadosEmailTimeCoracao();
	}
}
