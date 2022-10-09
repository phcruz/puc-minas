package br.com.phc.pitaco.utilities.repository.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.phc.pitaco.utilities.Constantes;
import br.com.phc.pitaco.utilities.dto.GrupoDTO;

@ConditionalOnProperty(name = Constantes.ENABLE_DATASOURCE, havingValue = "true", matchIfMissing = false)
@Repository
public class GrupoJdbcRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<GrupoDTO> listarGruposParaImportarPaginado(Long idUsuario, Integer inicio) {
		return jdbcTemplate.query("select g.id_grupo as idGrupo, g.id_liga as idLiga, g.id_usuario as idUsuario, u.nome as nomeUsuarioAdmin, "
				+ "g.nome_grupo as nomeGrupo, l.nome_liga as nomeLiga, g.descricao_grupo as descricaoGrupo, "
				+ "g.descricao_premio_grupo as descricaoPremioGrupo, l.temporada as temporada, "
				//+ "ifnull((select avg(ppt.pontos) from tb_palpite as ppt where ppt.id_usuario in (select gru.id_usuario from tb_grupo_usuario as gru where gru.id_grupo = g.id_grupo)), 0) as mediaPontos, "
				+ "ifnull((select sum(ppt.pontos) from tb_palpite as ppt where ppt.id_usuario in (select gru.id_usuario from grupo_usuario as gru where gru.id_grupo = g.id_grupo and gru.id_liga = g.id_liga and ativo = 1) and ppt.id_liga = g.id_liga  and ppt.pontos is not null), 0) / "
				+ "ifnull((select count(gu.id_grupo) from tb_grupo_usuario gu where gu.id_grupo = g.id_grupo and gu.ativo = 1), 0) as mediaPontos, "
				+ "ifnull((select count(gu.id_grupo) from tb_grupo_usuario gu where gu.id_grupo = g.id_grupo and gu.ativo = 1), 0) as quantidadeMembros, "
				+ "g.url_imagem as urlImagem, g.data_criacao as dataCriacao, g.data_alteracao as dataAlteracao, l.data_fim as dataFim, g.fechado as fechado, "
				+ "g.pago as pago, g.valor as valor "
				+ "from tb_tb_grupo as g, tb_tb_usuario as u, tb_tb_liga as l "
				+ "where g.id_liga = l.id_liga "
				+ "and l.ativa = 1 "
				+ "and l.data_fim > sysdate() "
				+ "and g.id_usuario = u.id_usuario "
				+ "and g.id_grupo not in ( "
				+ "select gu.id_grupo from tb_grupo_gsuario as gu "
				+ "where gu.id_usuario = ? and gu.ativo = 1) "
				+ "and g.id_grupo not in( "
				+ "select sgu.id_grupo from tb_solicitacao_grupo_usuario sgu "
				+ "where sgu.id_grupo = g.id_grupo and sgu.id_usuario_solicitante = ? and sgu.ativo = 1) "
				+ "order by dataFim desc "
				+ "limit ?, " + Constantes.LIMITE_CONSULTA_BANCO,
				new Object[]{ idUsuario, idUsuario, inicio }, 
				(rs, rowNum) -> {
					GrupoDTO grupo = new GrupoDTO();

					grupo.setIdGrupo(rs.getLong("idGrupo"));
					grupo.setIdLiga(rs.getLong("idLiga"));
					grupo.setIdUsuario(rs.getLong("idUsuario"));
					grupo.setNomeUsuarioAdmin(rs.getString("nomeUsuarioAdmin"));
					grupo.setNomeGrupo(rs.getString("nomeGrupo"));
					grupo.setNomeLiga(rs.getString("nomeLiga"));
					grupo.setDescricaoGrupo(rs.getString("descricaoGrupo"));
					grupo.setDescricaoPremioGrupo(rs.getString("descricaoPremioGrupo"));
					grupo.setTemporada(rs.getString("temporada"));
					grupo.setUrlImagem(rs.getString("urlImagem"));
					grupo.setMediaPontos(rs.getFloat("mediaPontos"));
					grupo.setQuantidadeMembros(rs.getInt("quantidadeMembros"));
					grupo.setDataCriacao(rs.getTimestamp("dataCriacao"));
					grupo.setDataAlteracao(rs.getTimestamp("dataAlteracao"));
					grupo.setDataFim(rs.getTimestamp("dataFim"));
					grupo.setFechado(rs.getBoolean("fechado"));
					grupo.setPago(rs.getBoolean("pago"));
					grupo.setValor(rs.getFloat("valor"));
					
					return grupo;
				}
				);
	}

	public List<GrupoDTO> listarGruposUsuarioIdPaginado(Long idUsuario, Integer inicio) {
		return jdbcTemplate.query("select g.id_grupo as idGrupo, g.id_liga as idLiga, g.id_usuario as idUsuario, u.nome as nomeUsuarioAdmin, "
				+ "g.nome_grupo as nomeGrupo, l.nome_liga as nomeLiga, g.descricao_grupo as descricaoGrupo, "
				+ "g.descricao_premio_grupo as descricaoPremioGrupo, l.temporada as temporada, "
				//+ "ifnull((select avg(ppt.pontos) from tb_palpite as ppt where ppt.id_usuario in (select gru.id_usuario from tb_grupo_usuario as gru where gru.id_grupo = g.id_grupo)), 0) as mediaPontos, "
				+ "ifnull((select sum(ppt.pontos) from tb_palpite as ppt where ppt.id_usuario in (select gru.id_usuario from tb_grupo_usuario as gru where gru.id_grupo = g.id_grupo and gru.id_liga = g.id_liga and ativo = 1) and ppt.id_liga = g.id_liga  and ppt.pontos is not null), 0) / "
				+ "ifnull((select count(gu.id_grupo) from tb_grupo_usuario gu where gu.id_grupo = g.id_grupo and gu.ativo = 1), 0) as mediaPontos, "
				+ "ifnull((select count(gu.id_grupo) from tb_grupo_usuario gu where gu.id_grupo = g.id_grupo and gu.ativo = 1), 0) as quantidadeMembros, "
				+ "g.url_imagem as urlImagem, g.data_criacao as dataCriacao, g.data_alteracao as dataAlteracao, l.data_fim as dataFim, g.fechado as fechado, "
				+ "g.pago as pago, g.valor as valor "
				+ "from tb_grupo as g, tb_usuario as u, tb_liga as l "
				+ "where g.id_liga = l.id_liga "
				+ "and l.ativa = 1 "
				+ "and g.id_usuario = u.id_usuario "
				+ "and g.id_grupo in ( "
				+ "select gu.id_grupo from tb_grupo_usuario as gu "
				+ "where gu.id_usuario = ? and gu.ativo = 1) "
				+ "order by dataFim desc "
				+ "limit ?, " + Constantes.LIMITE_CONSULTA_BANCO,
				new Object[]{ idUsuario, inicio }, 
				(rs, rowNum) -> {
					GrupoDTO grupo = new GrupoDTO();

					grupo.setIdGrupo(rs.getLong("idGrupo"));
					grupo.setIdLiga(rs.getLong("idLiga"));
					grupo.setIdUsuario(rs.getLong("idUsuario"));
					grupo.setNomeUsuarioAdmin(rs.getString("nomeUsuarioAdmin"));
					grupo.setNomeGrupo(rs.getString("nomeGrupo"));
					grupo.setNomeLiga(rs.getString("nomeLiga"));
					grupo.setDescricaoGrupo(rs.getString("descricaoGrupo"));
					grupo.setDescricaoPremioGrupo(rs.getString("descricaoPremioGrupo"));
					grupo.setTemporada(rs.getString("temporada"));
					grupo.setUrlImagem(rs.getString("urlImagem"));
					grupo.setMediaPontos(rs.getFloat("mediaPontos"));
					grupo.setQuantidadeMembros(rs.getInt("quantidadeMembros"));
					grupo.setDataCriacao(rs.getTimestamp("dataCriacao"));
					grupo.setDataAlteracao(rs.getTimestamp("dataAlteracao"));
					grupo.setDataFim(rs.getTimestamp("dataFim"));
					grupo.setFechado(rs.getBoolean("fechado"));
					grupo.setPago(rs.getBoolean("pago"));
					grupo.setValor(rs.getFloat("valor"));
					
					return grupo;
				}
				);
	}

	public List<GrupoDTO> listarGruposLigaUsuarioIdPaginado(Long idUsuario, Long idLiga, Integer inicio) {
		return jdbcTemplate.query("select g.id_grupo as idGrupo, g.id_liga as idLiga, g.id_usuario as idUsuario, u.nome as nomeUsuarioAdmin, "
				+ "g.nome_grupo as nomeGrupo, l.nome_liga as nomeLiga, g.descricao_grupo as descricaoGrupo, "
				+ "g.descricao_premio_grupo as descricaoPremioGrupo, l.temporada as temporada, "
				//+ "ifnull((select avg(ppt.pontos) from tb_palpite as ppt where ppt.id_usuario in (select gru.id_usuario from tb_grupo_usuario as gru where gru.id_grupo = g.id_grupo)), 0) as mediaPontos, "
				+ "ifnull((select sum(ppt.pontos) from tb_palpite as ppt where ppt.id_usuario in (select gru.id_usuario from tb_grupo_usuario as gru where gru.id_grupo = g.id_grupo and gru.id_liga = g.id_liga and ativo = 1) and ppt.id_liga = g.id_liga  and ppt.pontos is not null), 0) / "
				+ "ifnull((select count(gu.id_grupo) from tb_grupo_usuario gu where gu.id_grupo = g.id_grupo and gu.ativo = 1), 0) as mediaPontos, "
				+ "ifnull((select count(gu.id_grupo) from tb_grupo_usuario gu where gu.id_grupo = g.id_grupo and gu.ativo = 1), 0) as quantidadeMembros, "
				+ "g.url_imagem as urlImagem, g.data_criacao as dataCriacao, g.data_alteracao as dataAlteracao, l.data_fim as dataFim, g.fechado as fechado, "
				+ "g.pago as pago, g.valor as valor "
				+ "from tb_grupo as g, tb_usuario as u, tb_liga as l "
				+ "where g.id_liga = l.id_liga "
				+ "and l.ativa = 1 "
				+ "and g.id_usuario = u.id_usuario "
				+ "and g.id_grupo in ( "
				+ "select gu.id_grupo from tb_grupo_usuario as gu "
				+ "where gu.id_usuario = ? and gu.ativo = 1 and gu.id_liga = ?) "
				+ "limit ?, " + Constantes.LIMITE_CONSULTA_BANCO,
				new Object[]{ idUsuario, idLiga, inicio }, 
				(rs, rowNum) -> {
					GrupoDTO grupo = new GrupoDTO();

					grupo.setIdGrupo(rs.getLong("idGrupo"));
					grupo.setIdLiga(rs.getLong("idLiga"));
					grupo.setIdUsuario(rs.getLong("idUsuario"));
					grupo.setNomeUsuarioAdmin(rs.getString("nomeUsuarioAdmin"));
					grupo.setNomeGrupo(rs.getString("nomeGrupo"));
					grupo.setNomeLiga(rs.getString("nomeLiga"));
					grupo.setDescricaoGrupo(rs.getString("descricaoGrupo"));
					grupo.setDescricaoPremioGrupo(rs.getString("descricaoPremioGrupo"));
					grupo.setTemporada(rs.getString("temporada"));
					grupo.setUrlImagem(rs.getString("urlImagem"));
					grupo.setMediaPontos(rs.getFloat("mediaPontos"));
					grupo.setQuantidadeMembros(rs.getInt("quantidadeMembros"));
					grupo.setDataCriacao(rs.getTimestamp("dataCriacao"));
					grupo.setDataAlteracao(rs.getTimestamp("dataAlteracao"));
					grupo.setDataFim(rs.getTimestamp("dataFim"));
					grupo.setFechado(rs.getBoolean("fechado"));
					grupo.setPago(rs.getBoolean("pago"));
					grupo.setValor(rs.getFloat("valor"));
					
					return grupo;
				}
				);
	}

	public GrupoDTO buscarGrupoId(Long idGrupo) {
		return jdbcTemplate.queryForObject("select g.id_grupo as idGrupo, g.id_liga as idLiga, g.id_usuario as idUsuario, u.nome as nomeUsuarioAdmin, "
				+ "g.nome_grupo as nomeGrupo, l.nome_liga as nomeLiga, g.descricao_grupo as descricaoGrupo, "
				+ "g.descricao_premio_grupo as descricaoPremioGrupo, l.temporada as temporada, "
				//+ "ifnull((select avg(ppt.pontos) from tb_palpite as ppt where ppt.id_usuario in (select gru.id_usuario from tb_grupo_usuario as gru where gru.id_grupo = g.id_grupo)), 0) as mediaPontos, "
				+ "ifnull((select sum(ppt.pontos) from tb_palpite as ppt where ppt.id_usuario in (select gru.id_usuario from tb_grupo_usuario as gru where gru.id_grupo = g.id_grupo and gru.id_liga = g.id_liga and ativo = 1) and ppt.id_liga = g.id_liga  and ppt.pontos is not null), 0) / "
				+ "ifnull((select count(gu.id_grupo) from tb_grupo_usuario gu where gu.id_grupo = g.id_grupo and gu.ativo = 1), 0) as mediaPontos, "
				+ "ifnull((select count(gu.id_grupo) from tb_grupo_usuario gu where gu.id_grupo = g.id_grupo and gu.ativo = 1), 0) as quantidadeMembros, "
				+ "g.url_imagem as urlImagem, g.data_criacao as dataCriacao, g.data_alteracao as dataAlteracao, l.data_fim as dataFim, g.fechado as fechado, "
				+ "g.pago as pago, g.valor as valor "
				+ "from tb_grupo as g, tb_usuario as u, tb_liga as l "
				+ "where g.id_liga = l.id_liga "
				+ "and g.id_usuario = u.id_usuario "
				+ "and g.id_grupo = ? ",
				new Object[]{ idGrupo }, 
				(rs, rowNum) -> {
					GrupoDTO grupo = new GrupoDTO();

					grupo.setIdGrupo(rs.getLong("idGrupo"));
					grupo.setIdLiga(rs.getLong("idLiga"));
					grupo.setIdUsuario(rs.getLong("idUsuario"));
					grupo.setNomeUsuarioAdmin(rs.getString("nomeUsuarioAdmin"));
					grupo.setNomeGrupo(rs.getString("nomeGrupo"));
					grupo.setNomeLiga(rs.getString("nomeLiga"));
					grupo.setDescricaoGrupo(rs.getString("descricaoGrupo"));
					grupo.setDescricaoPremioGrupo(rs.getString("descricaoPremioGrupo"));
					grupo.setTemporada(rs.getString("temporada"));
					grupo.setUrlImagem(rs.getString("urlImagem"));
					grupo.setMediaPontos(rs.getFloat("mediaPontos"));
					grupo.setQuantidadeMembros(rs.getInt("quantidadeMembros"));
					grupo.setDataCriacao(rs.getTimestamp("dataCriacao"));
					grupo.setDataAlteracao(rs.getTimestamp("dataAlteracao"));
					grupo.setDataFim(rs.getTimestamp("dataFim"));
					grupo.setFechado(rs.getBoolean("fechado"));
					grupo.setPago(rs.getBoolean("pago"));
					grupo.setValor(rs.getFloat("valor"));
					
					return grupo;
				});
	}

}
