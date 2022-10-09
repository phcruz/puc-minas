package br.com.phc.pitaco.crawler.service;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.phc.pitaco.utilities.dto.crud.AvatarDTO;
import br.com.phc.pitaco.utilities.model.Avatar;
import br.com.phc.pitaco.utilities.repository.AvatarRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AvatarService {

	@Autowired
	private AvatarRepository avatarRepository;

	@Autowired
	private ModelMapper modelMapper;

	public List<Avatar> findAll() {
		log.info("Lista todos os avatares");
		return avatarRepository.findAll();
	}

	public Avatar findById(Long id) {
		log.info("Busca avatar de id [{}]", id);
		return avatarRepository.findByIdAvatar(id);
	}

	public void createAvatar(AvatarDTO avatarDTO) {
		Avatar avatar = modelMapper.map(avatarDTO, Avatar.class);
		log.info("Insere novo avatar");
		avatar.setDataCadastro(new Date());
		avatarRepository.save(avatar);
	}

	public void updateAvatar(Long id, AvatarDTO avatarDTO) {
		log.info("Atualiza avatar de id [{}]", id);
		Avatar avatar = this.findById(id);
		avatar.setAtivo(avatarDTO.isAtivo());
		avatar.setDescricao(avatarDTO.getDescricao());
		avatar.setUrlImagem(avatarDTO.getUrlImagem());
		avatarRepository.save(avatar);
	}

	public void deleteById(Long id) {
		log.info("Deleta avatar de id [{}]", id);
		avatarRepository.deleteByIdAvatar(id);
	}
}
