package br.com.phc.pitaco.utilities.repository.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.phc.pitaco.utilities.Constantes;
import br.com.phc.pitaco.utilities.dto.EmailTimeCoracaoDTO;
import br.com.phc.pitaco.utilities.util.UtilData;

@ConditionalOnProperty(name = Constantes.ENABLE_DATASOURCE, havingValue = "true", matchIfMissing = false)
@Repository
public class GenericRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<EmailTimeCoracaoDTO> listaDadosEmailTimeCoracao() {
		return jdbcTemplate.query("select ec.nome, ev.nome , p.dataHoraJogo, " + 
				"p.localJogo, u.timeCoracao, u.email, u.nome, ec.urlImagemEquipe, ev.urlImagemEquipe " + 
				"from partida as p " + 
				"inner join equipe as ec on p.idEquipeCasa = ec.idEquipe " + 
				"inner join equipe as ev on p.idEquipeVisitante = ev.idEquipe " + 
				"right join usuario as u on ec.nome = u.timeCoracao || ev.nome = u.timeCoracao " + 
				"where DATE_FORMAT(dataHoraJogo, '%Y-%m-%d') = CURDATE() " + 
				"and ifnull(p.localJogo, 'Vazio') != 'JOGO ADIADO' ",
				(rs, rowNum) -> {
				EmailTimeCoracaoDTO dadosEmail = new EmailTimeCoracaoDTO();
				
				dadosEmail.setNomeUsuario(rs.getString("u.nome"));
				dadosEmail.setEmail(rs.getString("u.email"));
				dadosEmail.setTimeCoracao(rs.getString("u.timeCoracao"));
				dadosEmail.setNomeEquipeCasa(rs.getString("ec.nome"));
				dadosEmail.setNomeEquipeVisitante(rs.getString("ev.nome"));
				dadosEmail.setLocalJogo(rs.getString("p.localJogo"));
				dadosEmail.setDataHoraJogo(UtilData.formatarDataString(rs.getTimestamp("p.dataHoraJogo")));
				dadosEmail.setUrlImagemEquipeCasa(rs.getString("ec.urlImagemEquipe"));
				dadosEmail.setUrlImagemEquipeVisitante(rs.getString("ev.urlImagemEquipe"));
				
				return dadosEmail;
		});
	}
}
