package br.com.phc.pitaco.api.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.phc.pitaco.utilities.Constantes;
import br.com.phc.pitaco.utilities.dto.PalpiteDTO;
import br.com.phc.pitaco.utilities.exception.CustomException;
import br.com.phc.pitaco.utilities.model.Liga;
import br.com.phc.pitaco.utilities.model.Palpite;
import br.com.phc.pitaco.utilities.model.Partida;
import br.com.phc.pitaco.utilities.model.Usuario;
import br.com.phc.pitaco.utilities.repository.PalpiteRepository;
import br.com.phc.pitaco.utilities.util.UtilData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PalpiteService {

	@Autowired
	private PalpiteRepository palpiteRepository;
	
	@Autowired
	private PartidaService partidaService;
	
	public void verificaPeriodoPalpite(PalpiteDTO novoPalpite) {
		log.info("Acessando verificaPeriodoPalpite(Palpite novoPalpite)");
		Partida p = partidaService.buscarPartidaPorId(novoPalpite.getIdPartida());

		Calendar dataPartida = GregorianCalendar.getInstance();
		dataPartida.setTime(p.getDataHoraJogo());
		dataPartida.add(Calendar.MINUTE, Constantes.QUANTIDADE_MINUTOS_ANTES_PARTIDA);				

		Calendar data = Calendar.getInstance();

		if (p.getLocalJogo().equalsIgnoreCase(Constantes.JOGO_ADIADO)) {
			return;
		}
		boolean permitePalpite = UtilData.verificaDataExpiradaCompareTo(
				new Date(data.getTimeInMillis()), new Date(dataPartida.getTimeInMillis()));
		
		if (!permitePalpite) {
			throw new CustomException("OPS. Horário limite de apostas encerrado", HttpStatus.EXPECTATION_FAILED);
		}
	}

	public Palpite buscarPalpiteId(Long idPartida, Long idUsuario) {
		log.info("Acessando buscarPalpiteId(Long idPartida, Long idUsuario)");
		return palpiteRepository.buscarPalpiteIdPartidaIdUsuario(idPartida, idUsuario);
	}

	public void atualizaPalpitePartida(PalpiteDTO palpiteDTO) {
		log.info("Acessando atualizaPalpitePartida(Palpite novoPalpite)");
		Palpite palpiteExistente = this.buscarPalpiteId(palpiteDTO.getIdPartida(), palpiteDTO.getIdUsuario());
		Date data = new Date(Calendar.getInstance().getTimeInMillis());
		
		if (palpiteExistente != null) {
			//alterar palpite cadastrado
			palpiteExistente.setPlacarEquipeCasa(palpiteDTO.getPlacarEquipeCasa());
			palpiteExistente.setPlacarEquipeVisitante(palpiteDTO.getPlacarEquipeVisitante());
			palpiteExistente.setDataAlteracao(data);
			palpiteRepository.save(palpiteExistente);
		} else {
			//inserir novo palpite
			Palpite palpite = new Palpite();
			palpite.setPartida(new Partida(palpiteDTO.getIdPartida()));
			palpite.setLiga(new Liga(palpiteDTO.getIdLiga()));
			palpite.setUsuario(new Usuario(palpiteDTO.getIdUsuario()));
			palpite.setPlacarEquipeCasa(palpiteDTO.getPlacarEquipeCasa());
			palpite.setPlacarEquipeVisitante(palpiteDTO.getPlacarEquipeVisitante());
			palpite.setDataCadastro(data);
			palpite.setDataAlteracao(data);
			palpiteRepository.save(palpite);
		}
	}

	public Palpite buscarPalpiteUsuarioPartida(PalpiteDTO palpiteDTO) {
		log.info("Acessando buscarPalpiteUsuarioPartida(Palpite novoPalpite)");
		return palpiteRepository.buscarPalpiteUsuarioPartida(palpiteDTO);
	}
	
	public List<Palpite> listarPalpitesIdPartida(Long idPartida) {
		log.info("Acessando listarPalpitesIdPartida(Long idPartida)");
		return palpiteRepository.findByPartidaIdPartida(idPartida);
	}

	public void alterarPontosPalpite(Palpite palpite) {
		log.info("Acessando alterarPontosPalpite(Palpite palpite)");
		palpiteRepository.alterarPontosPalpite(palpite);
	}
}
