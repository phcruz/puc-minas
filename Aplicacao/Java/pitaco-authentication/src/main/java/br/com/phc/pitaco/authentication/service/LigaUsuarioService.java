package br.com.phc.pitaco.authentication.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.phc.pitaco.utilities.model.Liga;
import br.com.phc.pitaco.utilities.model.LigaUsuario;
import br.com.phc.pitaco.utilities.model.Usuario;
import br.com.phc.pitaco.utilities.repository.LigaUsuarioRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LigaUsuarioService {

	@Autowired
	private LigaUsuarioRepository ligaUsuarioRepository;
	
	public void inserirLigaUsuario(Long idLiga, Long idUsuario) {
		log.info("Acessando inserirLigaUsuario(Long idLiga, Long idUsuario)");
		LigaUsuario l = criaLigaUsuario(idLiga, idUsuario);
		insereLigaUsuario(l);
	}
	
	private LigaUsuario criaLigaUsuario(Long idLiga, Long idUsuario) {
		log.info("Acessando criaLigaUsuario(Long idLiga, Long idUsuario)");
		LigaUsuario ligaUsuario = new LigaUsuario();
		ligaUsuario.setLiga(new Liga(idLiga));
		ligaUsuario.setUsuario(new Usuario(idUsuario));
		ligaUsuario.setDataCadastro(new Date());
		ligaUsuario.setDataAlteracao(new Date());
		ligaUsuario.setAtivo(true);
		return ligaUsuario;
	}

	private void insereLigaUsuario(LigaUsuario ligaUsuario) {
		log.info("Acessando insereLigaUsuario(LigaUsuario ligaUsuario)");
		ligaUsuarioRepository.save(ligaUsuario);
	}

	public void alterarLigaUsuario(LigaUsuario novaLigaUsuario) {
		log.info("Acessando alterarLigaUsuario(LigaUsuario novaLigaUsuario)");
		Calendar data = Calendar.getInstance();

		LigaUsuario liga = buscarLigaUsuario(novaLigaUsuario);
		if (liga != null && liga.getIdLigaUsuario() > 0) {
			// ja existe, apenas atualiza
			liga.setAtivo(!liga.isAtivo());
			liga.setDataAlteracao(data.getTime());
			ligaUsuarioRepository.save(liga);
		} else {
			liga=  new LigaUsuario();
			// nao existe, cadastra no banco
			liga.setLiga(novaLigaUsuario.getLiga());
			liga.setUsuario(novaLigaUsuario.getUsuario());
			liga.setAtivo(true);
			liga.setDataCadastro(data.getTime());
			liga.setDataAlteracao(data.getTime());
			insereLigaUsuario(liga);
		}
	}
	
	public LigaUsuario buscarLigaUsuario(LigaUsuario ligaUsuario) {
		log.info("Acessando buscarLigaUsuario(LigaUsuario ligaUsuario)");
		return ligaUsuarioRepository.findByLigaAndUsuario(ligaUsuario.getLiga(), ligaUsuario.getUsuario());
	}
}
