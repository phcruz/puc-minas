package br.com.phc.pitaco.crawler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.com.phc.pitaco.utilities.dto.PartidaDTO;
import br.com.phc.pitaco.utilities.model.Partida;
import br.com.phc.pitaco.utilities.repository.PartidaRepository;
import br.com.phc.pitaco.utilities.repository.jdbc.PartidaJdbcRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PartidaService {

	@Autowired
	private PartidaRepository partidaRepository;
	
	@Autowired
	private PartidaJdbcRepository partidaJdbcRepository;

	public Partida buscarPartidaPorId(Long idPartida) {
		log.info("Acessando buscarPartidaPorId(Long idPartida)");
		return partidaRepository.findByIdPartida(idPartida);
	}

	public List<PartidaDTO> listarPartidasHojeLigasUsuarioPaginado(Long idUsuario, Integer inicio) {
		log.info("Acessando listarPartidasHojeLigasUsuarioPaginado(Long idUsuario, Integer inicio)");
		return partidaJdbcRepository.listarPartidasHojeLigasUsuarioPaginado(idUsuario, inicio);
	}

	public List<PartidaDTO> listarPartidasHojeLigasUsuarioFimPaginado(Long idUsuario, Integer inicio, Integer fim) {
		log.info("Acessando listarPartidasHojeLigasUsuarioFimPaginado(Long idUsuario, Integer inicio, Integer fim)");
		return partidaJdbcRepository.listarPartidasHojeLigasUsuarioFimPaginado(idUsuario, inicio, fim);
	}

	public List<PartidaDTO> listarPartidasLigaUsuarioPaginado(Long idLiga, Long idUsuario, Integer inicio) {
		log.info("Acessando listarPartidasLigaUsuarioPaginado(Long idLiga, Long idUsuario, Integer inicio)");
		return partidaJdbcRepository.listarPartidasLigaUsuarioPaginado(idLiga, idUsuario, inicio);
	}

	public List<PartidaDTO> listarPartidasLigaUsuarioPosPalpitePaginado(Long idLiga, Long idUsuario, Integer inicio, Integer fim) {
		log.info(
				"Acessando listarPartidasLigaUsuarioPosPalpitePaginado(Long idLiga, Long idUsuario, Integer inicio, Integer fim)");
		return partidaJdbcRepository.listarPartidasLigaUsuarioPosPalpitePaginado(idLiga, idUsuario, inicio, fim);
	}

	public List<PartidaDTO> listarPalpitePartidaUsuarioDetalhePaginado(Long idLiga, Long idUsuario, Integer inicio) {
		log.info("Acessando listarPalpitePartidaUsuarioDetalhePaginado(Long idLiga, Long idUsuario, Integer inicio)");
		if (idLiga == 0) {
			return partidaJdbcRepository.listarPartidasPalpiteUsuarioValidaDataPaginado(idUsuario, inicio);
		} else {
			return partidaJdbcRepository.listarPartidasPalpiteLigaUsuarioValidaDataPaginado(idLiga, idUsuario, inicio);
		}
	}

	public List<PartidaDTO> listarPalpitePartidaUsuarioPerfilPaginado(Long idLiga, Long idUsuario, Integer inicio) {
		log.info("Acessando listarPalpitePartidaUsuarioPerfilPaginado(Long idLiga, Long idUsuario, Integer inicio)");
		if (idLiga == 0) {
			return partidaJdbcRepository.listarPartidasPalpiteUsuarioPaginado(idUsuario, inicio);
		} else {
			return partidaJdbcRepository.listarPartidasPalpiteLigaUsuarioPaginado(idLiga, idUsuario, inicio);
		}
	}

	@Cacheable(value = "listarHistoricoPartidasEquipePaginadoCache", keyGenerator = "customKeyGenerator")
	public List<PartidaDTO> listarHistoricoPartidasEquipePaginado(Long idLiga, Long idEquipe, Integer inicio) {
		log.info("Acessando listarHistoricoPartidasEquipePaginado(Long idLiga, Long idEquipe, Integer inicio)");
		return partidaJdbcRepository.listarHistoricoPartidasEquipePaginado(idLiga, idEquipe, inicio);
	}

	public Integer buscarTotalPartidasDiaAnterior() {
		log.info("Acessando buscarTotalPartidasDiaAnterior()");
		return partidaRepository.buscarTotalPartidasDiaAnterior();
	}

	public List<Partida> listarTodasPartidasRaspagemHoje() {
		log.info("Acessando listarTodasPartidasRaspagemHoje()");
		return partidaRepository.listarTodasPartidasRaspagemHoje();
	}

	public void alterarGolsPlacar(Partida partida) {
		log.info("Acessando alterarGolsPlacar(Partida partida)");
		partidaRepository.alterarGolsPlacar(partida);
	}

	public void alterarPartidaPlacarExtendido(Partida partida) {
		log.info("Acessando alterarPartidaPlacarExtendido(Partida partida)");
		partidaRepository.alterarPartidaPlacarExtendido(partida);
	}

	public void alterarTempoPartida(Long idPartida, String mensagemTempoJogo) {
		log.info("Acessando alterarTempoPartida(Long idPartida, String mensagemTempoJogo)");
		partidaRepository.alterarTempoPartida(mensagemTempoJogo, idPartida);
	}

	public void alterarPartidaPlacar(Partida partida) {
		log.info("Acessando alterarPartidaPlacar(Partida partida)");
		partidaRepository.alterarPartidaPlacar(partida);
	}

	public Integer buscarPartidasRaspagemHoje() {
		log.info("Acessando buscarPartidasRaspagemHoje()");
		return partidaRepository.buscarPartidasRaspagemHoje();
	}

}
