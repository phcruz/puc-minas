package br.com.phc.pitaco.api.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.phc.pitaco.utilities.dto.SolicitacaoGrupoUsuarioDTO;
import br.com.phc.pitaco.utilities.model.Grupo;
import br.com.phc.pitaco.utilities.model.GrupoUsuario;
import br.com.phc.pitaco.utilities.model.Liga;
import br.com.phc.pitaco.utilities.model.SolicitacaoGrupoUsuario;
import br.com.phc.pitaco.utilities.model.Usuario;
import br.com.phc.pitaco.utilities.repository.GrupoUsuarioRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GrupoUsuarioService {

	@Autowired
	private GrupoUsuarioRepository grupoUsuarioRepository;
	
	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private SolicitacaoGrupoUsuarioService solicitacaoGrupoUsuarioService; 

	public void inserirGrupoUsuario(GrupoUsuario grupoUsuario) {
		log.info("Acessando inserirGrupoUsuario()");
		grupoUsuarioRepository.save(grupoUsuario);
	}

	public void alterarSolicitacaoGrupoUsuario(SolicitacaoGrupoUsuario solicitacao) {
		log.info("Acessando alterarSolicitacaoGrupoUsuario(SolicitacaoGrupoUsuario solicitacao)");
		Calendar data = Calendar.getInstance();
		
		solicitacao.setDataResposta(data.getTime());
		solicitacao.setAtivo(false);
		solicitacaoGrupoUsuarioService.alterarSolicitacaoGrupoUsuario(solicitacao);
		
		if (solicitacao.isPermiteParticipar()) {
			GrupoUsuario novoGrupoUsuario = new GrupoUsuario();
			novoGrupoUsuario.setGrupo(solicitacao.getGrupo());
			novoGrupoUsuario.setLiga(solicitacao.getLiga());
			novoGrupoUsuario.setUsuario(solicitacao.getUsuarioSolicitante());


			GrupoUsuario grupo = grupoUsuarioRepository.buscarGrupoUsuario(novoGrupoUsuario);
			if (grupo != null && grupo.getIdGrupoUsuario() > 0) {
				// ja existe, apenas atualiza
				grupo.setAtivo(!grupo.isAtivo());
				grupo.setDataAlteracao(data.getTime());
				grupoUsuarioRepository.save(grupo);
			} else {
				grupo = new GrupoUsuario();
				// nao existe, cadastra no banco
				grupo.setGrupo(novoGrupoUsuario.getGrupo());
				grupo.setLiga(novoGrupoUsuario.getLiga());
				grupo.setUsuario(novoGrupoUsuario.getUsuario());
				grupo.setAtivo(true);
				grupo.setDataCadastro(data.getTime());
				grupo.setDataAlteracao(data.getTime());
				grupoUsuarioRepository.save(grupo);
			}
		}
		
	}

	public void cadastrarGrupoUsuario(GrupoUsuario novoGrupoUsuario) {
		log.info("Acessando cadastrarGrupoUsuario(GrupoUsuario novoGrupoUsuario)");
		Calendar data = Calendar.getInstance();

		GrupoUsuario grupo = grupoUsuarioRepository.buscarGrupoUsuario(novoGrupoUsuario);
		if (grupo != null && grupo.getIdGrupoUsuario() > 0) {
			// ja existe, apenas atualiza
			grupo.setAtivo(!grupo.isAtivo());
			grupo.setDataAlteracao(data.getTime());
			grupoUsuarioRepository.save(grupo);
			
			//verificar se o admin saiu do grupo
			Grupo gp = grupoService.buscarGrupoId(grupo.getGrupo().getIdGrupo());
			if (gp.isFechado() && gp.getUsuario().getIdUsuario() == grupo.getUsuario().getIdUsuario()) {
				//caso o usuario seja o admin de um grupo fechado o grupo deve passar a ser publico
				gp.setFechado(false);
				gp.setDataAlteracao(new Date());
				
				grupoService.alterarGrupoPrivadoParaPublico(gp);
				
				// finalizar solics pendentes
				this.finalizarSolicsPendentes(grupo.getGrupo().getIdGrupo(), data);
			}
		} else {
			grupo = new GrupoUsuario();
			// nao existe, cadastra no banco
			grupo.setGrupo(novoGrupoUsuario.getGrupo());
			grupo.setLiga(novoGrupoUsuario.getLiga());
			grupo.setUsuario(novoGrupoUsuario.getUsuario());
			grupo.setAtivo(true);
			grupo.setDataCadastro(data.getTime());
			grupo.setDataAlteracao(data.getTime());
			grupoUsuarioRepository.save(grupo);	
		}
	}
	
	private void finalizarSolicsPendentes(Long idGrupo, Calendar data) {
		List<SolicitacaoGrupoUsuarioDTO> solics = solicitacaoGrupoUsuarioService.listarSolicitacaoGrupoUsuario(idGrupo);
		
		if(solics != null) {
			for(SolicitacaoGrupoUsuarioDTO sgu : solics) {
				GrupoUsuario gu = new GrupoUsuario();
				gu.setGrupo(new Grupo(sgu.getIdGrupo()));
				gu.setLiga(new Liga(sgu.getIdLiga()));
				gu.setUsuario(new Usuario(sgu.getIdUsuarioSolicitante()));
	
				GrupoUsuario gUser = grupoUsuarioRepository.buscarGrupoUsuario(gu);
				if (gUser != null && gUser.getIdGrupoUsuario() > 0) {
					// ja existe, apenas atualiza
					gUser.setAtivo(!gUser.isAtivo());
					gUser.setDataAlteracao(data.getTime());
					grupoUsuarioRepository.save(gUser);
				} else {
					gUser = new GrupoUsuario();
					// nao existe, cadastra no banco
					gUser.setGrupo(gu.getGrupo());
					gUser.setLiga(gu.getLiga());
					gUser.setUsuario(gu.getUsuario());
					gUser.setAtivo(true);
					gUser.setDataCadastro(data.getTime());
					gUser.setDataAlteracao(data.getTime());
					grupoUsuarioRepository.save(gUser);
				}
			}
		}
	}

}
