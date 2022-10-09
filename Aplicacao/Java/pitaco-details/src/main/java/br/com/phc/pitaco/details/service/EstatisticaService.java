package br.com.phc.pitaco.details.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.com.phc.pitaco.utilities.dto.EstatisticaPartidaDTO;
import br.com.phc.pitaco.utilities.dto.EstatisticaPlacarDTO;
import br.com.phc.pitaco.utilities.repository.jdbc.EstatisticaJdbcRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EstatisticaService {

	@Autowired
	private EstatisticaJdbcRepository estatisticaRepository;
	
	@Cacheable(value = "buscarEstatisticaPartidaIdCache", keyGenerator = "customKeyGenerator")
	public EstatisticaPartidaDTO buscarEstatisticaPartidaId(Long idPartida) {
		log.info("Acessando buscarEstatisticaPartidaId(Long idPartida)");
		EstatisticaPartidaDTO estatistica = estatisticaRepository.buscarEstatisticaPartidaId(idPartida);
		estatistica.setListaEstatisticaPlacar(listarEstatisticaPlacarPartidaId(idPartida));
		return estatistica;
	}
	
	@Cacheable(value = "listarEstatisticaPlacarPartidaIdCache", keyGenerator = "customKeyGenerator")
	public List<EstatisticaPlacarDTO> listarEstatisticaPlacarPartidaId(Long idPartida) {
		log.info("Acessando listarEstatisticaPlacarPartidaId(Long idPartida)");
		return estatisticaRepository.listarEstatisticaPlacarPartidaId(idPartida);
	}

	@Cacheable(value = "listarEstatisticaPlacarPartidaIdPaginadoCache", keyGenerator = "customKeyGenerator")
	public List<EstatisticaPlacarDTO> listarEstatisticaPlacarPartidaIdPaginado(Long idPartida, Integer inicio) {
		log.info("Acessando listarEstatisticaPlacarPartidaIdPaginado(Long idPartida, Integer inicio)");
		return estatisticaRepository.listarEstatisticaPlacarPartidaIdPaginado(idPartida, inicio);
	}

}
