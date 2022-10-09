package br.com.phc.pitaco.utilities.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.phc.pitaco.utilities.Constantes;

@ConditionalOnProperty(name = Constantes.ENABLE_DATASOURCE, havingValue = "true", matchIfMissing = false)
@Repository
public class EquipeJdbcRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public String buscarNomeEquipeId(Long idEquipe) {
		return jdbcTemplate.queryForObject("select nome from tb_equipe where id_equipe=? ",
				new Object[]{ idEquipe },
				String.class);
	}
}
