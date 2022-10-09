package br.com.phc.pitaco.crawler.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import br.com.phc.pitaco.utilities.dto.external.EquipeDTO;
import br.com.phc.pitaco.utilities.dto.external.PartidaDTO;
import br.com.phc.pitaco.utilities.dto.external.PartidaExternaDTO;
import br.com.phc.pitaco.utilities.exception.BadRequestException;
import br.com.phc.pitaco.utilities.model.Equipe;
import br.com.phc.pitaco.utilities.model.Liga;
import br.com.phc.pitaco.utilities.model.Partida;
import br.com.phc.pitaco.utilities.repository.EquipeRepository;
import br.com.phc.pitaco.utilities.repository.LigaRepository;
import br.com.phc.pitaco.utilities.repository.PartidaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PartidaExternaService {

	@Autowired
	private PartidaRepository partidaRepository;

	@Autowired
	private EquipeRepository equipeRepository;
	
	@Autowired
	private LigaRepository ligaRepository;

	public void execute(PartidaExternaDTO info) {
		this.validateInformations(info);

		Liga liga = this.findLigaByName(info.getNomeLiga());
		List<PartidaDTO> partidasDTO = this.getExternalData(info.getUrl());
		this.executeChanges(partidasDTO, liga, info); 
	}

	public List<PartidaDTO> getExternalData(String url) {
		try {
			ResponseEntity<List<PartidaDTO>> response = new RestTemplate().exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<PartidaDTO>>() {});
	
			return response.getBody();
		} catch (Exception e) {
			log.error("Erro ao tentar recuperar partidas consulta externa: {}", e.getMessage());
		}
		return List.of();
	}
	
	public void executeChanges(List<PartidaDTO> partidas, Liga liga, PartidaExternaDTO info) {
		log.info("Executa insercao/alteracao Liga [{}] - Total de partidas encontradas: [{}]", liga.getNomeLiga(), partidas.size());
		for (PartidaDTO partidaDTO : partidas) {
			Optional<Partida> op = partidaRepository.findByIdExternoPartida(partidaDTO.getId());
			
			if (op.isEmpty()) {				
				Equipe mandante = this.insertEquipe(partidaDTO.getEquipes().getMandante());
				Equipe visitante = this.insertEquipe(partidaDTO.getEquipes().getVisitante());

				this.insertPartida(partidaDTO, mandante, visitante, liga, info);
			} else {
				this.updatePartida(op.get(), partidaDTO, info);
			}
		}
	}

	public Partida insertPartida(PartidaDTO partidaDTO, Equipe mandante, Equipe visitante, Liga liga, PartidaExternaDTO info) {
		log.info("Inserindo partida de idExterno: [{}]", partidaDTO.getId());
		Partida partida = new Partida();
		partida.setIdExternoPartida(partidaDTO.getId());

		partida.setLiga(liga);
		partida.setMandante(mandante);
		partida.setVisitante(visitante);

		partida.setPlacarEquipeCasa(Objects.nonNull(partidaDTO.getPlacarMandante()) ? Integer.valueOf(partidaDTO.getPlacarMandante()) : 0);
		partida.setPlacarEquipeVisitante(Objects.nonNull(partidaDTO.getPlacarVisitante()) ? Integer.valueOf(partidaDTO.getPlacarVisitante()) : 0);
		partida.setFase(info.getFase());
		partida.setTagGrupo(info.getTag());
		partida.setLocalJogo(partidaDTO.getSede().getNome());
		partida.setPlacarExtendidoEquipeCasa(partidaDTO.getPlacarPenaltisMandante());
		partida.setPlacarExtendidoEquipeVisitante(partidaDTO.getPlacarPenaltisVisitante());
		partida.setDataHoraJogo(partidaDTO.getDataRealizacao());
		partida.setTempoPartida(null);

		return partidaRepository.saveAndFlush(partida);
	}

	public Partida updatePartida(Partida partida, PartidaDTO partidaDTO, PartidaExternaDTO info) {
		log.info("Atualiza informacoes da partida de idExterno: [{}]", partidaDTO.getId());
		partida.setIdExternoPartida(partidaDTO.getId());
		partida.setLocalJogo(partidaDTO.getSede().getNome());
		partida.setPlacarEquipeCasa(Objects.nonNull(partidaDTO.getPlacarMandante()) ? Integer.valueOf(partidaDTO.getPlacarMandante()) : 0);
		partida.setPlacarEquipeVisitante(Objects.nonNull(partidaDTO.getPlacarVisitante()) ? Integer.valueOf(partidaDTO.getPlacarVisitante()) : 0);
		partida.setPlacarExtendidoEquipeCasa(partidaDTO.getPlacarPenaltisMandante());
		partida.setPlacarExtendidoEquipeVisitante(partidaDTO.getPlacarPenaltisVisitante());
		partida.setDataHoraJogo(partidaDTO.getDataRealizacao());

		return partidaRepository.saveAndFlush(partida);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Equipe insertEquipe(EquipeDTO equipeDTO) {
		Optional<Equipe> op = equipeRepository.findByIdExternoEquipe(equipeDTO.getId());
		if (op.isEmpty()) {
			log.info("Inserindo equipe de idExterno: [{}]", equipeDTO.getId());
			Equipe equipe = new Equipe();
			equipe.setIdEquipe(null);
			equipe.setIdExternoEquipe(equipeDTO.getId());
			equipe.setNome(equipeDTO.getNome());
			equipe.setSiglaEquipe(equipeDTO.getSigla());
			equipe.setUrlImagemEquipe(equipeDTO.getEscudo());
			equipe.setDataCadastro(new Date());

			return equipeRepository.saveAndFlush(equipe);
		}
		return op.get();
	}

	public Liga findLigaByName(String name) {
		log.info("Busca Liga por nome: [{}]", name);
		Optional<Liga> op = ligaRepository.findByNomeLigaAndAtivaTrue(name);
		if (op.isEmpty())
			throw new BadRequestException("Não foi encontrada nenhuma liga com o nome " + name);
		return op.get();
	}
	
	private void validateInformations(PartidaExternaDTO info) {
		if (Objects.isNull(info)) {
			throw new BadRequestException("Informe os dados para realizar a insercao/atualizacao");
		}
		
		if (Objects.isNull(info.getNomeLiga())) {
			throw new BadRequestException("Informe o nome da Liga");
		}
		
		if (Objects.isNull(info.getUrl())) {
			throw new BadRequestException("Informe a url de busca das informacoes");
		}
	}
}
