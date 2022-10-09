package br.com.phc.pitaco.crawler.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.phc.pitaco.utilities.dto.crud.PlanoFundoDTO;
import br.com.phc.pitaco.utilities.model.PlanoFundo;
import br.com.phc.pitaco.utilities.repository.PlanoFundoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PlanoFundoService {

	@Autowired
	private PlanoFundoRepository planoFundoRepository;

	@Autowired
	private ModelMapper modelMapper;

	public List<PlanoFundo> findAll() {
		log.info("Lista todos os planoFundoes");
		return planoFundoRepository.findAll();
	}

	public PlanoFundo findById(Long id) {
		log.info("Busca planoFundo de id [{}]", id);
		return planoFundoRepository.findByIdPlanoFundo(id);
	}

	public void createPlanoFundo(PlanoFundoDTO planoFundoDTO) {
		PlanoFundo planoFundo = modelMapper.map(planoFundoDTO, PlanoFundo.class);
		log.info("Insere novo planoFundo");
		planoFundoRepository.save(planoFundo);
	}

	public void updatePlanoFundo(Long id, PlanoFundoDTO planoFundoDTO) {
		log.info("Atualiza planoFundo de id [{}]", id);
		PlanoFundo planoFundo = this.findById(id);
		planoFundo.setNome(planoFundoDTO.getNome());
		planoFundo.setUrlImagem(planoFundoDTO.getUrlImagem());
		planoFundoRepository.save(planoFundo);
	}

	public void deleteById(Long id) {
		log.info("Deleta planoFundo de id [{}]", id);
		planoFundoRepository.deleteByIdPlanoFundo(id);
	}
}
