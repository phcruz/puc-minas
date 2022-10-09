package br.com.phc.pitaco.crawler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.phc.pitaco.utilities.model.Usuario;
import br.com.phc.pitaco.utilities.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Integer buscarQtdUsuarioInativo() {
		log.info("Acessando buscarQtdUsuarioInativo()");
		return usuarioRepository.buscarQtdUsuarioInativo();
	}

	public List<Usuario> listarUsuariosInativos() {
		log.info("Acessando listarUsuariosInativos()");
		return usuarioRepository.findByAtivoFalse();
	}
}
