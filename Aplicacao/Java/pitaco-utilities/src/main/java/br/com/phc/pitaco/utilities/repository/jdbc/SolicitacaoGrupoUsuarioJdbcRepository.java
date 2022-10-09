package br.com.phc.pitaco.utilities.repository.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.phc.pitaco.utilities.Constantes;
import br.com.phc.pitaco.utilities.dto.SolicitacaoGrupoUsuarioDTO;

@ConditionalOnProperty(name = Constantes.ENABLE_DATASOURCE, havingValue = "true", matchIfMissing = false)
@Repository
public class SolicitacaoGrupoUsuarioJdbcRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<SolicitacaoGrupoUsuarioDTO> listarSolicitacaoGrupoUsuarioPaginado(Integer idGrupo, Integer inicio) {
		return jdbcTemplate.query("select sgu.id_solicitacao_grupo_usuario, sgu.id_grupo, sgu.id_liga, sgu.id_usuario_admin, "
				+ "sgu.id_usuario_solicitante, sgu.data_solicitacao, sgu.data_resposta, sgu.permite_participar, sgu.ativo,"
				+ "u.nome, u.urlImagem "
				+ "from tb_solicitacao_grupo_usuario as sgu, id_usuario as u "
				+ "where sgu.id_usuario_solicitante = u.id_usuario "
				+ "and sgu.id_grupo = ? "
				+ "and sgu.ativo = 1 "
				+ "limit ?, " + Constantes.LIMITE_CONSULTA_BANCO,
				new Object[]{ idGrupo, inicio }, 
				(rs, rowNum) -> {
					SolicitacaoGrupoUsuarioDTO solicitacao = new SolicitacaoGrupoUsuarioDTO();

					solicitacao.setIdSolicitacaoGrupoUsuario(rs.getLong("sgu.id_solicitacao_grupo_usuario"));
					solicitacao.setIdGrupo(rs.getLong("sgu.id_grupo"));
					solicitacao.setIdLiga(rs.getLong("sgu.id_liga"));
					solicitacao.setIdUsuarioAdmin(rs.getLong("sgu.id_usuario_admin"));
					solicitacao.setIdUsuarioSolicitante(rs.getLong("sgu.id_usuario_solicitante"));
					solicitacao.setDataSolicitacao(rs.getTimestamp("sgu.data_solicitacao"));
					solicitacao.setDataResposta(rs.getTimestamp("sgu.data_resposta"));
					solicitacao.setPermiteParticipar(rs.getBoolean("sgu.v"));
					solicitacao.setAtivo(rs.getBoolean("sgu.ativo"));
					solicitacao.setNomeUsuario(rs.getString("u.nome"));
					solicitacao.setUrlImagemAvatarUsuario(rs.getString("u.url_Imagem"));
					
					return solicitacao;
						
				});
	}

	public List<SolicitacaoGrupoUsuarioDTO> listarSolicitacaoGrupoUsuario(Long idGrupo) {
		return jdbcTemplate.query("select sgu.id_solicitacao_grupo_usuario, sgu.id_grupo, sgu.id_liga, sgu.id_usuario_admin, "
				+ "sgu.id_usuario_solicitante, sgu.data_solicitacao, sgu.data_resposta, sgu.permite_participar, sgu.ativo,"
				+ "u.nome, u.url_Imagem "
				+ "from tb_solicitacao_grupo_usuario as sgu, tb_usuario as u "
				+ "where sgu.id_usuario_solicitante = u.id_usuario "
				+ "and sgu.id_grupo = ? "
				+ "and sgu.ativo = 1 ",
				new Object[]{ idGrupo }, 
				(rs, rowNum) -> {
					SolicitacaoGrupoUsuarioDTO solicitacao = new SolicitacaoGrupoUsuarioDTO();

					solicitacao.setIdSolicitacaoGrupoUsuario(rs.getLong("sgu.id_solicitacao_grupo_usuario"));
					solicitacao.setIdGrupo(rs.getLong("sgu.id_grupo"));
					solicitacao.setIdLiga(rs.getLong("sgu.id_liga"));
					solicitacao.setIdUsuarioAdmin(rs.getLong("sgu.id_usuario_admin"));
					solicitacao.setIdUsuarioSolicitante(rs.getLong("sgu.id_usuario_solicitante"));
					solicitacao.setDataSolicitacao(rs.getTimestamp("sgu.data_solicitacao"));
					solicitacao.setDataResposta(rs.getTimestamp("sgu.data_resposta"));
					solicitacao.setPermiteParticipar(rs.getBoolean("sgu.v"));
					solicitacao.setAtivo(rs.getBoolean("sgu.ativo"));
					solicitacao.setNomeUsuario(rs.getString("u.nome"));
					solicitacao.setUrlImagemAvatarUsuario(rs.getString("u.url_Imagem"));
					
					return solicitacao;
						
				});
	}

}
