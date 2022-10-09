package br.com.phc.pitaco.api.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.phc.pitaco.utilities.dto.GrupoDTO;
import br.com.phc.pitaco.utilities.model.Grupo;
import br.com.phc.pitaco.utilities.model.GrupoUsuario;
import br.com.phc.pitaco.utilities.model.Liga;
import br.com.phc.pitaco.utilities.model.SolicitacaoGrupoUsuario;
import br.com.phc.pitaco.utilities.repository.GrupoRepository;
import br.com.phc.pitaco.utilities.repository.jdbc.GrupoJdbcRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GrupoService {

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private GrupoJdbcRepository grupoJdbcRepository;
	
	@Autowired
	private LigaService ligaService;
	
	@Autowired
	private GrupoUsuarioService grupoUsuarioService;
	
	@Autowired
	private NotificacaoService notificacaoService;
	
	public List<GrupoDTO> listarGruposParaImportarPaginado(Long idUsuario, Integer inicio) {
		log.info("Acessando listarGruposParaImportarPaginado(Integer idUsuario, Integer inicio)");
		return grupoJdbcRepository.listarGruposParaImportarPaginado(idUsuario, inicio);
	}

	public List<GrupoDTO> listarGruposUsuarioIdPaginado(Long idUsuario, Integer inicio) {
		log.info("Acessando listarGruposUsuarioIdPaginado(Integer idUsuario, Integer inicio)");
		return grupoJdbcRepository.listarGruposUsuarioIdPaginado(idUsuario, inicio);
	}

	public List<GrupoDTO> listarGruposLigaUsuarioIdPaginado(Long idUsuario, Long idLiga, Integer inicio) {
		log.info("Acessando listarGruposLigaUsuarioIdPaginado(Integer idUsuario, Integer idLiga, Integer inicio)");
		if(idLiga == 0) {
			return grupoJdbcRepository.listarGruposUsuarioIdPaginado(idUsuario, inicio);
		} else {
			return grupoJdbcRepository.listarGruposLigaUsuarioIdPaginado(idUsuario, idLiga, inicio);
		}
	}

	public Grupo buscarGrupoId(Long idGrupo) {
		log.info("Acessando buscarGrupoId(Integer idGrupo)");
		return grupoRepository.findByIdGrupo(idGrupo);
	}

	public void cadastrarGrupo(Grupo novoGrupo) {
		log.info("Acessando cadastrarGrupo(Grupo novoGrupo)");
		Calendar data = Calendar.getInstance();
		
		//recupera a urlImagem da liga ate ser implantado o esquema de imagem
		Liga liga = ligaService.buscarLigaId(novoGrupo.getLiga().getIdLiga());
		
		novoGrupo.setUrlImagem(liga.getUrlImagem());
		novoGrupo.setDataCriacao(data.getTime());
		novoGrupo.setDataAlteracao(data.getTime());
		novoGrupo.setPago(false);
		novoGrupo.setValor(Float.parseFloat("0.00"));
		novoGrupo = grupoRepository.save(novoGrupo);

		//insere grupoUsuario ja participando
		GrupoUsuario grupoUsuario = new GrupoUsuario();
		grupoUsuario.setGrupo(novoGrupo);
		grupoUsuario.setLiga(novoGrupo.getLiga());
		grupoUsuario.setUsuario(novoGrupo.getUsuario());
		grupoUsuario.setDataCadastro(data.getTime());
		grupoUsuario.setDataAlteracao(data.getTime());
		grupoUsuario.setAtivo(true);
		
		grupoUsuarioService.inserirGrupoUsuario(grupoUsuario);
	}

	public void alterarGrupo(Grupo grupo) {
		log.info("Acessando alterarGrupo(Grupo grupo)");
		grupo.setDataAlteracao(Calendar.getInstance().getTime());
		grupoRepository.save(grupo);
	}

	public void insereNotificacaoSolicParticipacaoGrupo(SolicitacaoGrupoUsuario novaSolicitacao) {
		log.info("Acessando insereNotificacaoSolicParticipacaoGrupo(SolicitacaoGrupoUsuario novaSolicitacao)");
		Grupo grupo = grupoRepository.findByIdGrupo(novaSolicitacao.getGrupo().getIdGrupo());
		
		notificacaoService.inserirNotificacao(grupo.getNomeGrupo(), novaSolicitacao.getUsuarioAdmin().getIdUsuario());
	}

	public void alterarGrupoPrivadoParaPublico(Grupo grupo) {
		log.info("Acessando alterarGrupoPrivadoParaPublico(Grupo grupo)");
		grupoRepository.save(grupo);
	}

}