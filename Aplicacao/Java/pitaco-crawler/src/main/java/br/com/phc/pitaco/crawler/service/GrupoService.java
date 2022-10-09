package br.com.phc.pitaco.crawler.service;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.phc.pitaco.utilities.dto.crud.GrupoDTO;
import br.com.phc.pitaco.utilities.model.Grupo;
import br.com.phc.pitaco.utilities.repository.GrupoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GrupoService {

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private ModelMapper modelMapper;

	public List<Grupo> findAll() {
		log.info("Lista todos os grupos");
		return grupoRepository.findAll();
	}

	public Grupo findById(Long id) {
		log.info("Busca grupo de id [{}]", id);
		return grupoRepository.findByIdGrupo(id);
	}

	public void createGrupo(GrupoDTO grupoDTO) {
		Grupo grupo = modelMapper.map(grupoDTO, Grupo.class);
		log.info("Insere novo grupo");
		grupo.setDataCriacao(new Date());
		grupoRepository.save(grupo);
	}

	public void updateGrupo(Long id, GrupoDTO grupoDTO) {
		log.info("Atualiza grupo de id [{}]", id);
		Grupo grupo = this.findById(id);
		grupo.setNomeGrupo(grupoDTO.getNomeGrupo());
		grupo.setDescricaoGrupo(grupoDTO.getDescricaoGrupo());
		grupo.setDescricaoPremioGrupo(grupoDTO.getDescricaoPremioGrupo());
		grupo.setUrlImagem(grupoDTO.getUrlImagem());
		grupo.setFechado(grupoDTO.isFechado());
		grupoRepository.save(grupo);
	}

	public void deleteById(Long id) {
		log.info("Deleta grupo de id [{}]", id);
		grupoRepository.deleteByIdGrupo(id);
	}
}
