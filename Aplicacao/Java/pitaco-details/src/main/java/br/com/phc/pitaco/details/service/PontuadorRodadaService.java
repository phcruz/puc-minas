package br.com.phc.pitaco.details.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.com.phc.pitaco.utilities.dto.PontuadorRodadaDTO;
import br.com.phc.pitaco.utilities.repository.jdbc.PontuadorRodadaJdbcRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PontuadorRodadaService {

	@Autowired
	private PontuadorRodadaJdbcRepository pontuadorRodadaRepository;
	
	@Cacheable(value = "listarPontuadorRodadaPaginadoCache", keyGenerator = "customKeyGenerator")
	public List<PontuadorRodadaDTO> listarPontuadorRodadaPaginado(Integer inicio) {
		log.info("Acessando listarPontuadorRodadaPaginado(Integer inicio)");
		return pontuadorRodadaRepository.listarPontuadorRodadaPaginado(inicio);
	}

	@Cacheable(value = "listarPontuadorRodadaGrupoPaginadoCache", keyGenerator = "customKeyGenerator")
	public List<PontuadorRodadaDTO> listarPontuadorRodadaGrupoPaginado(Long idGrupo, Integer inicio) {
		log.info("Acessando listarPontuadorRodadaGrupoPaginado(Long idGrupo, Integer inicio)");
		return pontuadorRodadaRepository.listarPontuadorRodadaGrupoPaginado(idGrupo, inicio);
	}

}
