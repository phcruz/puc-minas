package br.com.phc.pitaco.details.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.com.phc.pitaco.utilities.model.Avatar;
import br.com.phc.pitaco.utilities.repository.AvatarRepository;
import br.com.phc.pitaco.utilities.repository.UsuarioRepository;
import br.com.phc.pitaco.utilities.repository.jdbc.AvatarJdbcRepository;

@Service
public class AvatarService {

	private static final Logger log = LoggerFactory.getLogger(AvatarService.class);

	@Autowired
	private AvatarRepository avatarRepository;

	@Autowired
	private AvatarJdbcRepository avatarJdbcRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Cacheable(value = "listarTodosAvataresPaginadoCache", keyGenerator = "customKeyGenerator")
	public List<Avatar> listarTodosAvataresPaginado(int inicio) {
		log.info("Acessando listarTodosAvataresPaginado(int inicio)");
		return avatarJdbcRepository.listarTodosAvataresPaginado(inicio);
	}

	public Avatar buscarAvatarId(Long idAvatar) {
		log.info("Acessando buscarAvatarId(Long idAvatar)");
		return avatarRepository.findByIdAvatar(idAvatar);
	}

	public void atualizaAvatar(Long idAvatar, Long idUsuario) {
		log.info("Acessando atualizaAvatar(Long idAvatar, Long idUsuario)");
		Avatar avatar = buscarAvatarId(idAvatar);
		usuarioRepository.atualizaAvatarUsuario(avatar, idUsuario);
	}
}
