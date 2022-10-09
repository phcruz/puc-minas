package br.com.phc.pitaco.utilities.repository.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.phc.pitaco.utilities.Constantes;
import br.com.phc.pitaco.utilities.dto.ClassificacaoGeralDTO;

@ConditionalOnProperty(name = Constantes.ENABLE_DATASOURCE, havingValue = "true", matchIfMissing = false)
@Repository
public class ClassificacaoGeralJdbcRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<ClassificacaoGeralDTO> listarClassificacaoGeral(Long idLiga, Integer inicio) {
		criaTabelasTemporarias(idLiga);
		
		return jdbcTemplate.query("select * from tb_classificacao_geral_ordenado "
				+ "limit ?, " + Constantes.LIMITE_CONSULTA_BANCO,
				new Object[]{ inicio }, 
				(rs, rowNum) -> {
					ClassificacaoGeralDTO classificacao = new ClassificacaoGeralDTO();

					classificacao.setPosicao(rs.getInt("posicao"));
					classificacao.setIdEquipe(rs.getLong("id_equipe"));
					classificacao.setNomeEquipe(rs.getString("nome"));
					classificacao.setUrlImagemEquipe(rs.getString("url_imagem"));
					classificacao.setQuantidadeJogos(rs.getInt("jogos"));
					classificacao.setQuantidadeVitorias(rs.getInt("vitorias"));
					classificacao.setQuantidadeEmpates(rs.getInt("empates"));
					classificacao.setQuantidadeDerrotas(rs.getInt("derrotas"));
					classificacao.setQuantidadePontos(rs.getInt("pontos"));
					classificacao.setSaldoGols(rs.getInt("saldo_gols"));
					
					return classificacao;
				});
	}

	private void criaTabelasTemporarias(Long idLiga) {
		String procedure = "{ call deleta_tabela_temporaria_classificacao_geral() }";
		jdbcTemplate.execute(procedure);

		jdbcTemplate.execute("CREATE TEMPORARY TABLE IF NOT EXISTS tb_classificacao_geral AS ("
				+ "select eq.id_equipe as id_equipe, eq.nome as nome, eq.url_imagem_equipe as url_imagem, "
				+ "(select count(*) from tb_partida as p where p.tempo_partida is not null and p.id_liga = " + idLiga + " and (p.id_equipe_casa = eq.id_equipe or p.id_equipe_visitante = eq.id_equipe)) as jogos, "
				+ "(select count(*) from tb_partida as p where p.tempo_partida is not null and p.id_liga = " + idLiga + " and (p.id_equipe_casa = eq.id_equipe and p.placar_equipe_casa > p.placar_equipe_visitante or p.id_equipe_visitante = eq.id_equipe and p.placar_equipe_casa < p.placar_equipe_visitante)) as vitorias, "
				+ "(select count(*) from tb_partida as p where p.tempo_partida is not null and p.id_liga = " + idLiga + " and (p.id_equipe_casa = eq.id_equipe and p.placar_equipe_casa = p.placar_equipe_visitante or p.id_equipe_visitante = eq.id_equipe and p.placar_equipe_casa = p.placar_equipe_visitante)) as empates, "
				+ "(select count(*) from tb_partida as p where p.tempo_partida is not null and p.id_liga = " + idLiga + " and (p.id_equipe_casa = eq.id_equipe and p.placar_equipe_casa < p.placar_equipe_visitante or p.id_equipe_visitante = eq.id_equipe and p.placar_equipe_casa > p.placar_equipe_visitante)) as derrotas, "
				+ "((select count(*)*3 from tb_partida as p where p.tempo_partida is not null and p.id_liga = " + idLiga + " and (p.id_equipe_casa = eq.id_equipe and p.placar_equipe_casa > p.placar_equipe_visitante or p.id_equipe_visitante = eq.id_equipe and p.placar_equipe_casa < p.placar_equipe_visitante)) "
				+ "+"
				+ "(select count(*) from tb_partida as p where p.tempo_partida is not null and p.id_liga = " + idLiga + " and (p.id_equipe_casa = eq.id_equipe and p.placar_equipe_casa = p.placar_equipe_visitante or p.id_equipe_visitante = eq.id_equipe and p.placar_equipe_casa = p.placar_equipe_visitante))) as pontos, "
				+ "ifnull(("
				+ "((select sum(p.placar_equipe_casa) from tb_partida as p where p.tempo_partida is not null and p.id_liga = " + idLiga + " and (p.id_equipe_casa = eq.id_equipe))"
				+ "+"
				+ "(select sum(p.placar_equipe_visitante) from tb_partida as p  where p.tempo_partida is not null and p.id_liga = " + idLiga + " and (p.id_equipe_visitante = eq.id_equipe)))"
				+ "-"
				+ "((select sum(p.placar_equipe_visitante) from tb_partida as p where p.tempo_partida is not null and p.id_liga = " + idLiga + " and (p.id_equipe_casa = eq.id_equipe))"
				+ "+"
				+ "(select sum(p.placar_equipe_casa) from tb_partida as p  where p.tempo_partida is not null and p.id_liga = " + idLiga + " and (p.id_equipe_visitante = eq.id_equipe)))"
				+ "), 0) as saldo_gols "
				+ "from tb_partida as part, tb_liga as l, tb_equipe as eq "
				+ "where part.id_liga = l.id_liga "
				+ "and (part.id_equipe_casa = eq.id_equipe or part.id_equipe_visitante = eq.id_equipe) "
				+ "and part.id_liga = " + idLiga + " "
				+ "group by eq.id_equipe "
				+ "order by pontos desc, vitorias desc, saldo_gols desc, nome asc ) ");

		jdbcTemplate.execute("CREATE TEMPORARY TABLE IF NOT EXISTS tb_classificacao_geral_ordenado AS( "
				+ "select @contador := @contador + 1 as posicao, cg.* "
				+ "from tb_classificacao_geral as cg, (SELECT @contador := 0) AS nada) ");
	}
}
