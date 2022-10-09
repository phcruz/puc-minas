package br.com.phc.pitaco.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.com.phc.pitaco.utilities.dto.ClassificacaoGeralDTO;
import br.com.phc.pitaco.utilities.repository.jdbc.ClassificacaoGeralJdbcRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClassificacaoGeralService {

	@Autowired
	private ClassificacaoGeralJdbcRepository classificacaoGeralJdbcRepository;
	
	@Cacheable(value = "listarClassificacaoGeralCache", keyGenerator = "customKeyGenerator")
	public List<ClassificacaoGeralDTO> listarClassificacaoGeral(Long idLiga, Integer inicio) {
		log.info("Acessando listarClassificacaoGeral(Integer idLiga, Integer inicio)");
		return classificacaoGeralJdbcRepository.listarClassificacaoGeral(idLiga, inicio);
	}

}
