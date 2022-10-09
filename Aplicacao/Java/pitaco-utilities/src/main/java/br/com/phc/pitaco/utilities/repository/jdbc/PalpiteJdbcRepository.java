package br.com.phc.pitaco.utilities.repository.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.phc.pitaco.utilities.Constantes;
import br.com.phc.pitaco.utilities.dto.PalpiteDTO;

@ConditionalOnProperty(name = Constantes.ENABLE_DATASOURCE, havingValue = "true", matchIfMissing = false)
@Repository
public class PalpiteJdbcRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<PalpiteDTO> listarPalpitesIdPartida(Long idPartida) {
		return jdbcTemplate.query("select * from tb_palpite where id_partida=? ", new Object[] { idPartida },
				(rs, rowNum) -> {
					PalpiteDTO palpite = new PalpiteDTO();

					palpite.setIdPalpite(rs.getLong("idPalpite"));
					palpite.setIdPartida(rs.getLong("idPartida"));
					palpite.setIdUsuario(rs.getLong("idUsuario"));
					palpite.setIdLiga(rs.getLong("idLiga"));
					palpite.setPlacarEquipeCasa(rs.getInt("placarEquipeCasa"));
					palpite.setPlacarEquipeVisitante(rs.getInt("placarEquipeVisitante"));
					palpite.setPontos(rs.getInt("pontos"));

					return palpite;
				});
	}

	public void alterarPontosPalpite(PalpiteDTO palpite) {
		jdbcTemplate.update("update tb_palpite set pontos=? where id_palpite=? ",
				new Object[] { palpite.getPontos(), palpite.getIdPalpite() });
	}
}
