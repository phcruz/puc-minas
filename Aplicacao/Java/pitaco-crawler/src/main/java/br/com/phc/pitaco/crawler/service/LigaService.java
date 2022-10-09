package br.com.phc.pitaco.crawler.service;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.phc.pitaco.utilities.dto.crud.LigaDTO;
import br.com.phc.pitaco.utilities.model.Liga;
import br.com.phc.pitaco.utilities.repository.LigaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LigaService {

	@Autowired
	private LigaRepository ligaRepository;

	@Autowired
	private ModelMapper modelMapper;

	public List<Liga> findAll() {
		log.info("Lista todos os ligaes");
		return ligaRepository.findAll();
	}

	public Liga findById(Long id) {
		log.info("Busca liga de id [{}]", id);
		return ligaRepository.findByIdLiga(id);
	}

	public void createLiga(LigaDTO ligaDTO) {
		Liga liga = modelMapper.map(ligaDTO, Liga.class);
		log.info("Insere novo liga");
		liga.setDataInicio(new Date());
		ligaRepository.save(liga);
	}

	public void updateLiga(Long id, LigaDTO ligaDTO) {
		log.info("Atualiza liga de id [{}]", id);
		Liga liga = this.findById(id);
		liga.setAtiva(ligaDTO.isAtiva());
		liga.setDescricao(ligaDTO.getDescricao());
		liga.setUrlImagem(ligaDTO.getUrlImagem());
		liga.setNomeLiga(ligaDTO.getNomeLiga());
		liga.setPaisLiga(ligaDTO.getPaisLiga());
		liga.setTemporada(ligaDTO.getTemporada());
		ligaRepository.save(liga);
	}

	public void deleteById(Long id) {
		log.info("Deleta liga de id [{}]", id);
		ligaRepository.deleteByIdLiga(id);
	}
}
