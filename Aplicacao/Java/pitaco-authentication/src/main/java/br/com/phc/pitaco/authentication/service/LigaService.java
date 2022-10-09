package br.com.phc.pitaco.authentication.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.com.phc.pitaco.utilities.model.Liga;
import br.com.phc.pitaco.utilities.repository.LigaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LigaService {

	@Autowired
	private LigaRepository ligaRepository;
	
	@Autowired
	private LigaUsuarioService ligaUsuarioService;
	
	public void insereLigaAtivaNovoUsuario(Long idUsuario) {
		log.info("Acessando insereLigaAtivaNovoUsuario(Long idUsuario)");
		List<Liga> lista = ligaRepository.listarLigasAtivasParaImportar(idUsuario);
		//insere liga usuario
		lista.forEach(item -> ligaUsuarioService.inserirLigaUsuario(item.getIdLiga(), idUsuario));
	}

	public List<Liga> listarTodasLigas() {
		log.info("Acessando listarTodasLigas()");
		return ligaRepository.findAll();
	}

	public List<Liga> listarLigasAtivas() {
		log.info("Acessando listarLigasAtivas()");
		return ligaRepository.findByAtivaTrue();
	}

	@Cacheable(value = "listarLigasAtivasUsuarioCache", keyGenerator = "customKeyGenerator")
	public List<Liga> listarLigasAtivasUsuario(Long idUsuario) {
		log.info("Acessando listarLigasAtivasUsuario(Long idUsuario)");
		return ligaRepository.listarLigasAtivasUsuario(idUsuario);
	}

	public List<Liga> listarLigasParaImportar(Long idUsuario) {
		log.info("Acessando listarLigasParaImportar(Long idUsuario)");
		return ligaRepository.listarLigasParaImportar(idUsuario);
	}

	public List<Liga> listarLigasParaImportarPaginado(Long idUsuario, Integer inicio) {
		log.info("Acessando listarLigasParaImportarPaginado(Long idUsuario, Integer inicio)");
		return ligaRepository.listarLigasParaImportarPaginado(idUsuario, inicio);
	}

	public List<Liga> listarLigasUsuarioId(Long idUsuario) {
		log.info("Acessando listarLigasUsuarioId(Long idUsuario)");
		return ligaRepository.listarLigasUsuarioId(idUsuario);
	}

	public List<Liga> listarLigasUsuarioIdPaginado(Long idUsuario, Integer inicio) {
		log.info("Acessando listarLigasUsuarioIdPaginado(Long idUsuario, Integer inicio)");
		return ligaRepository.listarLigasUsuarioIdPaginado(idUsuario, inicio);
	}

	public Liga buscarLigaId(Long idLiga) {
		log.info("Acessando buscarLigaId(Long idLiga)");
		return ligaRepository.findByIdLiga(idLiga);
	}

}
