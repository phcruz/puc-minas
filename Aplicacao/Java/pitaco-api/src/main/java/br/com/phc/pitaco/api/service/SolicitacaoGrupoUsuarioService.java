package br.com.phc.pitaco.api.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.phc.pitaco.utilities.dto.SolicitacaoGrupoUsuarioDTO;
import br.com.phc.pitaco.utilities.model.SolicitacaoGrupoUsuario;
import br.com.phc.pitaco.utilities.repository.SolicitacaoGrupoUsuarioRepository;
import br.com.phc.pitaco.utilities.repository.jdbc.SolicitacaoGrupoUsuarioJdbcRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SolicitacaoGrupoUsuarioService {

	@Autowired
	private SolicitacaoGrupoUsuarioRepository solicitacaoGrupoUsuarioRepository;
	
	@Autowired
	private SolicitacaoGrupoUsuarioJdbcRepository solicitacaoGrupoUsuarioJdbcRepository;
	
	public List<SolicitacaoGrupoUsuarioDTO> listarSolicitacaoGrupoUsuarioPaginado(Integer idGrupo, Integer inicio) {
		log.info("Acessando listarSolicitacaoGrupoUsuarioPaginado(Integer idGrupo, Integer inicio)");
		return solicitacaoGrupoUsuarioJdbcRepository.listarSolicitacaoGrupoUsuarioPaginado(idGrupo, inicio);
	}

	public void inserirSolicitacaoGrupoUsuario(SolicitacaoGrupoUsuario novaSolicitacao) {
		log.info("Acessando inserirSolicitacaoGrupoUsuario(SolicitacaoGrupoUsuario novaSolicitacao)");
		novaSolicitacao.setDataSolicitacao(Calendar.getInstance().getTime());
		novaSolicitacao.setAtivo(true);
		solicitacaoGrupoUsuarioRepository.save(novaSolicitacao);
	}

	public List<SolicitacaoGrupoUsuarioDTO> listarSolicitacaoGrupoUsuario(Long idGrupo) {
		log.info("Acessando listarSolicitacaoGrupoUsuario(Long idGrupo)");
		return solicitacaoGrupoUsuarioJdbcRepository.listarSolicitacaoGrupoUsuario(idGrupo);
	}

	public void alterarSolicitacaoGrupoUsuario(SolicitacaoGrupoUsuario solicitacao) {
		log.info("Acessando alterarSolicitacaoGrupoUsuario(SolicitacaoGrupoUsuario solicitacao)");
		solicitacaoGrupoUsuarioRepository.alterarSolicitacaoGrupoUsuario(solicitacao);
	}

}
