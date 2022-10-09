package br.com.phc.pitaco.utilities.repository.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.phc.pitaco.utilities.Constantes;
import br.com.phc.pitaco.utilities.dto.RankingDTO;

@ConditionalOnProperty(name = Constantes.ENABLE_DATASOURCE, havingValue = "true", matchIfMissing = false)
@Repository
public class RankingJdbcRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<RankingDTO> listarRankingGlobalPaginado(Integer inicio) {
		return jdbcTemplate
				.query("select ifnull(sum(p.pontos), 0) as pontos, u.id_usuario as idUsuario, SUBSTR(u.nome, 1, "
						+ Constantes.LIMITE_TAMANHO_NOME + ") as nomeUsuario, "
						+ "0 as idLiga, 'Ranking global' as nomeLiga, a.url_imagem as urlImagem, "
						+ "(select count(*) from tb_palpite as pa where pa.id_usuario = u.id_usuario and pa.pontos = 5) as cincoPontos, "
						+ "(select count(*) from tb_palpite as pal where pal.id_usuario = u.id_usuario and pal.pontos = 3) as tresPontos, "
						+ "(select count(*) from tb_palpite as pal where pal.id_usuario = u.id_usuario and pal.pontos = 1) as umPonto, "
						+ "(select count(*) from tb_palpite as palp where palp.id_usuario = u.id_usuario and palp.pontos > 0) as pontuou "
						+ "from tb_palpite as p, tb_usuario as u, tb_liga as l, tb_avatar as a "
						+ "where p.id_usuario = u.id_usuario "
						+ "and p.id_liga = l.id_liga "
						+ "and a.id_avatar = u.id_avatar "
						+ "group by p.id_usuario "
						+ "order by pontos desc, cincoPontos desc, tresPontos desc, umPonto desc, pontuou desc, nomeUsuario asc "
						+ "limit ?, " + Constantes.LIMITE_CONSULTA_BANCO, new Object[] { inicio }, (rs, rowNum) -> {
							RankingDTO ranking = new RankingDTO();

							// ranking.setPosicao(rs.getInt("posicao"));
							ranking.setPosicao(rowNum + 1 + inicio);
							ranking.setPontos(rs.getInt("pontos"));
							ranking.setIdUsuario(rs.getLong("idUsuario"));
							ranking.setNomeUsuario(rs.getString("nomeUsuario"));
							ranking.setIdLiga(rs.getLong("idLiga"));
							ranking.setNomeLiga(rs.getString("nomeLiga"));
							ranking.setUrlImagem(rs.getString("urlImagem"));
							// cirterios desempate
							ranking.setAcertoCincoPontos(rs.getInt("cincoPontos"));
							ranking.setAcertoTresPontos(rs.getInt("tresPontos"));
							ranking.setAcertoUmPonto(rs.getInt("umPonto"));
							ranking.setAcertoPontuou(rs.getInt("pontuou"));

							return ranking;
						});
	}

	public List<RankingDTO> listarRankingPorLigaPaginado(Long idLiga, Integer inicio) {
		return jdbcTemplate.query(
				"select ifnull(sum(p.pontos), 0) as pontos, u.id_usuario as idUsuario, SUBSTR(u.nome, 1, "
						+ Constantes.LIMITE_TAMANHO_NOME + ") as nomeUsuario, "
						+ "l.id_liga as idLiga, l.nome_liga as nomeLiga, a.url_imagem as urlImagem, "
						+ "(select count(*) from tb_palpite as pa where pa.id_usuario = u.id_usuario and pa.pontos = 5 and pa.id_liga = l.id_liga) as cincoPontos, "
						+ "(select count(*) from tb_palpite as pal where pal.id_usuario = u.id_usuario and pal.pontos = 3  and pal.id_liga = l.id_liga) as tresPontos, "
						+ "(select count(*) from tb_palpite as pal where pal.id_usuario = u.id_usuario and pal.pontos = 1  and pal.id_liga = l.id_liga) as umPonto, "
						+ "(select count(*) from tb_palpite as palp where palp.id_usuario = u.id_usuario and palp.pontos > 0  and palp.id_liga = l.id_liga) as pontuou "
						+ "from tb_palpite as p, tb_usuario as u, tb_liga as l, tb_avatar as a "
						+ "where p.id_usuario = u.id_usuario "
						+ "and p.id_liga = l.id_liga "
						+ "and a.id_avatar = u.id_avatar "
						+ "and p.id_liga = ? "
						+ "group by p.id_usuario, p.id_liga "
						+ "order by pontos desc, cincoPontos desc, tresPontos desc, umPonto desc, pontuou desc, nomeUsuario asc "
						+ "limit ?, " + Constantes.LIMITE_CONSULTA_BANCO,
				new Object[] { idLiga, inicio }, (rs, rowNum) -> {
					RankingDTO ranking = new RankingDTO();

					// ranking.setPosicao(rs.getInt("posicao"));
					ranking.setPosicao(rowNum + 1 + inicio);
					ranking.setPontos(rs.getInt("pontos"));
					ranking.setIdUsuario(rs.getLong("idUsuario"));
					ranking.setNomeUsuario(rs.getString("nomeUsuario"));
					ranking.setIdLiga(rs.getLong("idLiga"));
					ranking.setNomeLiga(rs.getString("nomeLiga"));
					ranking.setUrlImagem(rs.getString("urlImagem"));
					// cirterios desempate
					ranking.setAcertoCincoPontos(rs.getInt("cincoPontos"));
					ranking.setAcertoTresPontos(rs.getInt("tresPontos"));
					ranking.setAcertoUmPonto(rs.getInt("umPonto"));
					ranking.setAcertoPontuou(rs.getInt("pontuou"));

					return ranking;
				});
	}

	public List<RankingDTO> listarRankingPorGrupoLigaPaginado(Long idLiga, Long idGrupo, Integer inicio) {
		return jdbcTemplate.query(
				"select ifnull(sum(p.pontos), 0) as pontos, u.id_usuario as idUsuario, SUBSTR(u.nome, 1, "
						+ Constantes.LIMITE_TAMANHO_NOME + ") as nomeUsuario, "
						+ "l.id_liga as idLiga, l.nome_liga as nomeLiga, a.url_imagem as urlImagem, "
						+ "(select count(*) from tb_palpite as pa where pa.id_usuario = u.id_usuario and pa.pontos = 5 and pa.id_liga = l.id_liga) as cincoPontos, "
						+ "(select count(*) from tb_palpite as pal where pal.id_usuario = u.id_usuario and pal.pontos = 3 and pal.id_liga = l.id_liga) as tresPontos, "
						+ "(select count(*) from tb_palpite as pal where pal.id_usuario = u.id_usuario and pal.pontos = 1 and pal.id_liga = l.id_liga) as umPonto, "
						+ "(select count(*) from tb_palpite as palp where palp.id_usuario = u.id_usuario and palp.pontos > 0 and palp.id_liga = l.id_liga) as pontuou "
						+ "from tb_palpite as p, tb_usuario as u, tb_liga as l, tb_avatar as a "
						+ "where p.id_usuario = u.id_usuario "
						+ "and p.id_liga = l.id_liga "
						+ "and a.id_avatar = u.id_avatar "
						+ "and p.id_liga = ? "
						+ "and u.id_usuario in (select gu.id_usuario from tb_grupo_usuario as gu where gu.id_grupo = ? and gu.ativo = 1) "
						+ "group by p.id_usuario, p.id_liga "
						+ "order by pontos desc, cincoPontos desc, tresPontos desc, umPonto desc, pontuou desc, nomeUsuario asc "
						+ "limit ?, " + Constantes.LIMITE_CONSULTA_BANCO,
				new Object[] { idLiga, idGrupo, inicio }, (rs, rowNum) -> {
					RankingDTO ranking = new RankingDTO();

					// ranking.setPosicao(rs.getInt("posicao"));
					ranking.setPosicao(rowNum + 1 + inicio);
					ranking.setPontos(rs.getInt("pontos"));
					ranking.setIdUsuario(rs.getLong("idUsuario"));
					ranking.setNomeUsuario(rs.getString("nomeUsuario"));
					ranking.setIdLiga(rs.getLong("idLiga"));
					ranking.setNomeLiga(rs.getString("nomeLiga"));
					ranking.setUrlImagem(rs.getString("urlImagem"));
					// cirterios desempate
					ranking.setAcertoCincoPontos(rs.getInt("cincoPontos"));
					ranking.setAcertoTresPontos(rs.getInt("tresPontos"));
					ranking.setAcertoUmPonto(rs.getInt("umPonto"));
					ranking.setAcertoPontuou(rs.getInt("pontuou"));

					return ranking;
				});
	}

	public RankingDTO buscarRankingGlobalUsuarioId(Long idUsuario) {
		criaTabelasTemporarias();

		try {
		return jdbcTemplate.queryForObject("select * from tb_ranking_ordenado ro where ro.idUsuario = ? ",
				new Object[] { idUsuario }, (rs, rowNum) -> {
					RankingDTO ranking = new RankingDTO();

					ranking.setPosicao(rs.getInt("posicao"));
					ranking.setPontos(rs.getInt("pontos"));
					ranking.setIdUsuario(rs.getLong("idUsuario"));
					ranking.setNomeUsuario(rs.getString("nomeUsuario"));
					ranking.setIdLiga(rs.getLong("idLiga"));
					ranking.setNomeLiga(rs.getString("nomeLiga"));
					ranking.setUrlImagem(rs.getString("urlImagem"));
					// cirterios desempate
					ranking.setAcertoCincoPontos(rs.getInt("cincoPontos"));
					ranking.setAcertoTresPontos(rs.getInt("tresPontos"));
					ranking.setAcertoUmPonto(rs.getInt("umPonto"));
					ranking.setAcertoPontuou(rs.getInt("pontuou"));

					return ranking;
				});
		} catch (Exception e) {
			return buscarRankingGlobalUsuarioIdSemPalpite(idUsuario);
		}
	}

	public List<RankingDTO> listarRankingUsuarioPorPalpite(Long idPartida, String placarPalpite, Integer inicio) {
		return jdbcTemplate.query(
				"select ifnull(sum(p.pontos), 0) as pontos, u.id_usuario as idUsuario, SUBSTR(u.nome, 1, "
						+ Constantes.LIMITE_TAMANHO_NOME + ") as nomeUsuario, "
						+ "l.id_liga as idLiga, l.nome_liga as nomeLiga, a.url_imagem as urlImagem, "
						+ "(select count(*) from tb_palpite as pa where pa.id_usuario = u.id_usuario and pa.pontos = 5 and pa.id_liga = l.id_liga) as cincoPontos, "
						+ "(select count(*) from tb_palpite as pal where pal.id_usuario = u.id_usuario and pal.pontos = 3  and pal.id_liga = l.id_liga) as tresPontos, "
						+ "(select count(*) from tb_palpite as pal where pal.id_usuario = u.id_usuario and pal.pontos = 1  and pal.id_liga = l.id_liga) as umPonto, "
						+ "(select count(*) from tb_palpite as palp where palp.id_usuario = u.id_usuario and palp.pontos > 0  and palp.id_liga = l.id_liga) as pontuou "
						+ "from tb_palpite as p, tb_usuario as u, tb_liga as l, tb_avatar as a "
						+ "where p.id_usuario = u.id_usuario "
						+ "and p.id_liga = l.id_liga "
						+ "and a.id_avatar = u.id_avatar "
						+ "and p.id_liga = (select pal.id_liga from tb_palpite as pal where pal.id_partida = ? limit 0, 1) "
						+ "and p.id_usuario in (select pa.id_usuario from tb_palpite as pa " + "where pa.id_partida = ? "
						+ "and CONCAT_WS('x', pa.placar_equipe_casa, pa.placar_equipe_visitante) = ? ) "
						+ "group by p.id_usuario, p.id_liga " + "order by u.nome " + "limit ?, "
						+ Constantes.LIMITE_CONSULTA_BANCO,
				new Object[] { idPartida, idPartida, placarPalpite, inicio }, (rs, rowNum) -> {
					RankingDTO ranking = new RankingDTO();

					// ranking.setPosicao(rs.getInt("posicao"));
					ranking.setPosicao(rowNum + 1 + inicio);
					ranking.setPontos(rs.getInt("pontos"));
					ranking.setIdUsuario(rs.getLong("idUsuario"));
					ranking.setNomeUsuario(rs.getString("nomeUsuario"));
					ranking.setIdLiga(rs.getLong("idLiga"));
					ranking.setNomeLiga(rs.getString("nomeLiga"));
					ranking.setUrlImagem(rs.getString("urlImagem"));
					// cirterios desempate
					ranking.setAcertoCincoPontos(rs.getInt("cincoPontos"));
					ranking.setAcertoTresPontos(rs.getInt("tresPontos"));
					ranking.setAcertoUmPonto(rs.getInt("umPonto"));
					ranking.setAcertoPontuou(rs.getInt("pontuou"));

					return ranking;
				});
	}
	
	private void criaTabelasTemporarias() {
		String procedure = "{ call deleta_tabela_temporaria_ranking() }";
		jdbcTemplate.execute(procedure);

		jdbcTemplate.execute("CREATE TEMPORARY TABLE IF NOT EXISTS tb_ranking AS ("
				+ "select ifnull(sum(p.pontos), 0) as pontos, u.id_usuario as idUsuario, SUBSTR(u.nome, 1, 30) as nomeUsuario, "
				+ "0 as idLiga, 'Ranking Global' as nomeLiga, a.url_imagem as urlImagem, "
				+ "(select count(*) from tb_palpite as pa where pa.id_usuario = u.id_usuario and pa.id_liga = l.id_liga and l.temporada like CONCAT('%', YEAR(CURDATE()), '%') and pa.pontos = 5) as cincoPontos, "
				+ "(select count(*) from tb_palpite as pa where pa.id_usuario = u.id_usuario and pa.id_liga = l.id_liga and l.temporada like CONCAT('%', YEAR(CURDATE()), '%') and pa.pontos = 3) as tresPontos, "
				+ "(select count(*) from tb_palpite as pa where pa.id_usuario = u.id_usuario and pa.id_liga = l.id_liga and l.temporada like CONCAT('%', YEAR(CURDATE()), '%') and pa.pontos = 1) as umPonto, "
				+ "(select count(*) from tb_palpite as pa where pa.id_usuario = u.id_usuario and pa.id_liga = l.id_liga and l.temporada like CONCAT('%', YEAR(CURDATE()), '%') and pa.pontos > 0) as pontuou "
				+ "from tb_palpite as p, tb_usuario as u, tb_liga as l, tb_avatar as a "
				+ "where p.id_usuario = u.id_usuario "
				+ "and p.id_liga = l.id_liga "
				+ "and a.id_avatar = u.id_avatar "
				+ "and l.temporada like CONCAT('%', YEAR(CURDATE()), '%') "
				+ "group by p.id_usuario "
				+ "order by pontos desc, cincoPontos desc, tresPontos desc, umPonto desc, pontuou desc, nomeUsuario asc)");

		jdbcTemplate.execute("CREATE TEMPORARY TABLE IF NOT EXISTS tb_ranking_ordenado AS( "
				+ "select @contador := @contador + 1 as posicao, r.* "
				+ "from tb_ranking as r, (SELECT @contador := 0) AS nada) ");
	}
	
	public RankingDTO buscarRankingLigaUsuarioId(Long idUsuario, Long idLiga) {
		criaTabelasTemporariasLiga(idLiga);

		return jdbcTemplate.queryForObject("select * from tb_ranking_ordenado_liga ro where ro.idUsuario = ? ",
				new Object[] { idUsuario }, (rs, rowNum) -> {
					RankingDTO ranking = new RankingDTO();

					ranking.setPosicao(rs.getInt("posicao"));
					ranking.setPontos(rs.getInt("pontos"));
					ranking.setIdUsuario(rs.getLong("idUsuario"));
					ranking.setNomeUsuario(rs.getString("nomeUsuario"));
					ranking.setIdLiga(rs.getLong("idLiga"));
					ranking.setNomeLiga(rs.getString("nomeLiga"));
					ranking.setUrlImagem(rs.getString("urlImagem"));
					// cirterios desempate
					ranking.setAcertoCincoPontos(rs.getInt("cincoPontos"));
					ranking.setAcertoTresPontos(rs.getInt("tresPontos"));
					ranking.setAcertoUmPonto(rs.getInt("umPonto"));
					ranking.setAcertoPontuou(rs.getInt("pontuou"));

					return ranking;
				});
	}
	
	private void criaTabelasTemporariasLiga(Long idLiga) {
		String procedure = "{ call deleta_tabela_temporaria_ranking_liga() }";
		jdbcTemplate.execute(procedure);

		jdbcTemplate.execute("CREATE TEMPORARY TABLE IF NOT EXISTS tb_ranking_liga AS (" 
				+ "select ifnull(sum(p.pontos), 0) as pontos, u.id_usuario as idUsuario, SUBSTR(u.nome, 1, "
				+ Constantes.LIMITE_TAMANHO_NOME + ") as nomeUsuario, "
				+ "l.id_liga as idLiga, CONCAT('Ranking ', l.nome_liga) as nomeLiga, a.url_imagem as urlImagem, " 
				+ "(select count(*) from tb_palpite as pa where pa.id_usuario = u.id_usuario and pa.pontos = 5) as cincoPontos, "
				+ "(select count(*) from tb_palpite as pal where pal.id_usuario = u.id_usuario and pal.pontos = 3) as tresPontos, "
				+ "(select count(*) from tb_palpite as pal where pal.id_usuario = u.id_usuario and pal.pontos = 1) as umPonto, "
				+ "(select count(*) from tb_palpite as palp where palp.id_usuario = u.id_usuario and palp.pontos > 0) as pontuou "
				+ "from tb_palpite as p, tb_usuario as u, tb_liga as l, tb_avatar as a "
				+ "where p.id_usuario = u.id_usuario "
				+ "and p.id_liga = l.id_liga "
				+ "and a.id_avatar = u.id_avatar "
				+ "and p.id_liga = " + idLiga + " "
				+ "group by p.id_usuario "
				+ "order by pontos desc, cincoPontos desc, tresPontos desc, umPonto desc, pontuou desc, nomeUsuario asc) ");

		jdbcTemplate.execute("CREATE TEMPORARY TABLE IF NOT EXISTS tb_ranking_ordenado_liga AS( "
				+ "select @contador := @contador + 1 as posicao, r.* "
				+ "from tb_ranking_liga as r, (SELECT @contador := 0) AS nada) ");
	}
	
	public RankingDTO buscarRankingGlobalUsuarioIdSemPalpite(Long idUsuario) {
		return jdbcTemplate.queryForObject("select nome, url_imagem from tb_usuario where id_usuario = ? ",
				new Object[] { idUsuario }, (rs, rowNum) -> {
					RankingDTO ranking = new RankingDTO();

					ranking.setPosicao(0);
					ranking.setPontos(0);
					ranking.setIdUsuario(idUsuario);
					ranking.setNomeUsuario(rs.getString("nome"));
					ranking.setIdLiga(0L);
					ranking.setNomeLiga("Ranking Global");
					ranking.setUrlImagem(rs.getString("urlImagem"));
					// cirterios desempate
					ranking.setAcertoCincoPontos(0);
					ranking.setAcertoTresPontos(0);
					ranking.setAcertoUmPonto(0);
					ranking.setAcertoPontuou(0);

					return ranking;
				});
	}
}
