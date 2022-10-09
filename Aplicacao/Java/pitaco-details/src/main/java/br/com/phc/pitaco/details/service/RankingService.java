package br.com.phc.pitaco.details.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.com.phc.pitaco.utilities.dto.RankingDTO;
import br.com.phc.pitaco.utilities.repository.jdbc.RankingJdbcRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RankingService {

	@Autowired
	private RankingJdbcRepository rankingRepository;
	
	public List<RankingDTO> listarRankingGlobalPaginado(Integer inicio) {
		log.info("Acessando listarRankingGlobalPaginado(Integer inicio)");
		return rankingRepository.listarRankingGlobalPaginado(inicio);
	}

	@Cacheable(value = "listarRankingPorLigaPaginadoCache", keyGenerator = "customKeyGenerator")
	public List<RankingDTO> listarRankingPorLigaPaginado(Long idLiga, Integer inicio) {
		log.info("Acessando listarRankingPorLigaPaginado(Long idLiga, Integer inicio)");
		return rankingRepository.listarRankingPorLigaPaginado(idLiga, inicio);
	}

	public List<RankingDTO> listarRankingPorGrupoLigaPaginado(Long idLiga, Long idGrupo, Integer inicio) {
		log.info("Acessando listarRankingPorGrupoLigaPaginado(Long idLiga, Long idGrupo, Integer inicio)");
		return rankingRepository.listarRankingPorGrupoLigaPaginado(idLiga, idGrupo, inicio);
	}

	@Cacheable(value = "buscarRankingGlobalUsuarioIdCache", keyGenerator = "customKeyGenerator")
	public RankingDTO buscarRankingGlobalUsuarioId(Long idUsuario) {
		log.info("Acessando buscarRankingGlobalUsuarioId(Long idUsuario)");
		return rankingRepository.buscarRankingGlobalUsuarioId(idUsuario);
	}
	
	@Cacheable(value="buscarRankingLigaUsuarioIdCache", keyGenerator = "customKeyGenerator")
	public RankingDTO buscarRankingLigaUsuarioId(Long idUsuario, Long idLiga) {
		log.info("Acessando buscarRankingLigaUsuarioId(Long idUsuario, Long idLiga)");
		return rankingRepository.buscarRankingLigaUsuarioId(idUsuario, idLiga);
	}

	public List<RankingDTO> listarRankingUsuarioPorPalpite(Long idPartida, String placarPalpite, Integer inicio) {
		log.info("Acessando listarRankingUsuarioPorPalpite(Long idPartida, String placarPalpite, Integer inicio)");
		return rankingRepository.listarRankingUsuarioPorPalpite(idPartida, placarPalpite, inicio);
	}

}
