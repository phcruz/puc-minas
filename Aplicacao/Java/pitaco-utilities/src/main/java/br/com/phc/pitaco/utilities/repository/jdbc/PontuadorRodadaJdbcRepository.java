package br.com.phc.pitaco.utilities.repository.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.phc.pitaco.utilities.Constantes;
import br.com.phc.pitaco.utilities.dto.PontuadorRodadaDTO;

@ConditionalOnProperty(name = Constantes.ENABLE_DATASOURCE, havingValue = "true", matchIfMissing = false)
@Repository
public class PontuadorRodadaJdbcRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<PontuadorRodadaDTO> listarPontuadorRodadaPaginado(Integer inicio) {
		return jdbcTemplate.query("select ifnull(sum(pal.pontos), 0) as pontos, u.id_usuario, SUBSTR(u.nome, 1, " + Constantes.LIMITE_TAMANHO_NOME + ") as nome, u.url_imagem "
				+ "from tb_palpite as pal, tb_partida as par, tb_usuario as u "
				+ "where pal.id_partida = par.id_partida "
				+ "and pal.id_usuario = u.id_usuario "
				+ "and date_format(par.data_hora_jogo, '%d/%m/%Y') = date_format(date_sub(sysdate(), INTERVAL 1 day), '%d/%m/%Y') "
				+ "group by pal.id_usuario "
				+ "order by pontos desc, u.nome "
				+ "limit ?, " + Constantes.LIMITE_CONSULTA_BANCO,
				new Object[]{ inicio }, 
				(rs, rowNum) -> new PontuadorRodadaDTO( 
						rs.getLong("u.idUsuario"),
						rs.getString("nome"),
						rs.getString("u.url_imagem"),
						rs.getInt("pontos")
				));
	}

	public List<PontuadorRodadaDTO> listarPontuadorRodadaGrupoPaginado(Long idGrupo, Integer inicio) {
		return jdbcTemplate.query("select ifnull(sum(pal.pontos), 0) as pontos, u.id_usuario, SUBSTR(u.nome, 1, " + Constantes.LIMITE_TAMANHO_NOME + ") as nome, u.url_imagem "
				+ "from tb_palpite as pal, tb_partida as par, tb_usuario as u "
				+ "where pal.id_partida = par.id_partida "
				+ "and pal.id_usuario = u.id_usuario "
				+ "and u.id_usuario in (select gu.id_usuario from grupo_usuario as gu where gu.id_grupo = ? and gu.ativo = 1) "
				+ "and date_format(par.data_hora_jogo, '%d/%m/%Y') = date_format(date_sub(sysdate(), INTERVAL 1 day), '%d/%m/%Y') "
				+ "group by pal.id_usuario "
				+ "order by pontos desc, u.nome "
				+ "limit ?, " + Constantes.LIMITE_CONSULTA_BANCO,
				new Object[]{ idGrupo, inicio }, 
				(rs, rowNum) -> new PontuadorRodadaDTO( 
						rs.getLong("u.id_usuario"),
						rs.getString("nome"),
						rs.getString("u.url_imagem"),
						rs.getInt("pontos")
				));
	}

}
