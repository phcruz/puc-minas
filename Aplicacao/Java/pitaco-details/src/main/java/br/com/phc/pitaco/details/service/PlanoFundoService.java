package br.com.phc.pitaco.details.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.phc.pitaco.utilities.dto.PlanoFundoDTO;
import br.com.phc.pitaco.utilities.model.PlanoFundo;
import br.com.phc.pitaco.utilities.repository.PlanoFundoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PlanoFundoService {

	@Autowired
	private PlanoFundoRepository planoFundoRepository;
	
	public PlanoFundoDTO getImagemPlanoFundo() {
		log.info("Acessando getImagemPlanoFundo()");
		PlanoFundo planoFundo = planoFundoRepository.findRandomImage();
		return PlanoFundoDTO.builder().urlImagem(planoFundo.getUrlImagem()).nome(planoFundo.getNome()).build();
	}

}
