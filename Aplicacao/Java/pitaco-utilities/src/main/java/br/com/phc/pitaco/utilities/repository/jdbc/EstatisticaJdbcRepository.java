package br.com.phc.pitaco.utilities.repository.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.phc.pitaco.utilities.Constantes;
import br.com.phc.pitaco.utilities.dto.EstatisticaPartidaDTO;
import br.com.phc.pitaco.utilities.dto.EstatisticaPlacarDTO;

@ConditionalOnProperty(name = Constantes.ENABLE_DATASOURCE, havingValue = "true", matchIfMissing = false)
@Repository
public class EstatisticaJdbcRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public EstatisticaPartidaDTO buscarEstatisticaPartidaId(Long idPartida) {
		return jdbcTemplate.queryForObject("select sum(if(p.placar_equipe_casa > p.placar_equipe_visitante,1,0)) as vitoriaCasa, "
				+ "sum(if(p.placar_equipe_casa < p.placar_equipe_visitante,1,0)) as vitoriaVisitante, "
				+ "sum(if(p.placar_equipe_casa = p.placar_equipe_visitante,1,0)) as empate, "
				+ "(select count(*) from tb_palpite pal where pal.id_partida = ?) as totalPalpites "
				+ "from tb_palpite p where p.id_partida = ? ",
				new Object[]{ idPartida, idPartida }, 
				(rs, rowNum) -> new EstatisticaPartidaDTO( 
						rs.getInt("vitoriaCasa"),
						rs.getInt("vitoriaVisitante"),
						rs.getInt("empate"),
						rs.getInt("totalPalpites"),
						new ArrayList<>()
				));
	}
	
	public List<EstatisticaPlacarDTO> listarEstatisticaPlacarPartidaId(Long idPartida) {
		return jdbcTemplate.query("SELECT CONCAT_WS('x', p.placar_equipe_casa, p.placar_equipe_visitante) as placarPalpite, "
				+ "(SELECT count(if(CONCAT_WS('x', p.placar_equipe_casa, p.placar_equipe_visitante) = placarPalpite, 1, 0))) as totalPlacar "
				+ "from tb_palpite as p "
				+ "where p.id_partida = ? "
				+ "group by placarPalpite "
				+ "order by placarPalpite ",
				new Object[]{ idPartida }, 
				(rs, rowNum) -> new EstatisticaPlacarDTO(
						rs.getString("placarPalpite"),
						rs.getInt("totalPlacar")
				));
	}

	public List<EstatisticaPlacarDTO> listarEstatisticaPlacarPartidaIdPaginado(Long idPartida, Integer inicio) {
		return jdbcTemplate.query("SELECT CONCAT_WS('x', p.placar_equipe_casa, p.placar_equipe_visitante) as placarPalpite, "
				+ "(SELECT count(if(CONCAT_WS('x', p.placar_equipe_casa, p.placar_equipe_visitante) = placarPalpite, 1, 0))) as totalPlacar "
				+ "from tb_palpite as p "
				+ "where p.id_partida = ? "
				+ "group by placarPalpite "
				+ "order by placarPalpite "
				+ "limit ?, " + Constantes.LIMITE_CONSULTA_BANCO,
				new Object[]{ idPartida, inicio }, 
				(rs, rowNum) -> new EstatisticaPlacarDTO(
						rs.getString("placarPalpite"),
						rs.getInt("totalPlacar")
				));
	}

}
