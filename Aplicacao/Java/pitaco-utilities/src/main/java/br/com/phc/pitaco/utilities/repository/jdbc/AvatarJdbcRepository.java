package br.com.phc.pitaco.utilities.repository.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.phc.pitaco.utilities.Constantes;
import br.com.phc.pitaco.utilities.model.Avatar;

@ConditionalOnProperty(name = Constantes.ENABLE_DATASOURCE, havingValue = "true", matchIfMissing = false)
@Repository
public class AvatarJdbcRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Avatar> listarTodosAvataresPaginado(int inicio) {
		return jdbcTemplate.query("select * from tb_avatar as av where av.ativo = 1 order by descricao asc limit ?, " 
				+ Constantes.LIMITE_CONSULTA_BANCO,
				new Object[]{ inicio }, 
				(rs, rowNum) -> {
					Avatar avatar = new Avatar();

					avatar.setIdAvatar(rs.getLong("id_avatar"));
					avatar.setUrlImagem(rs.getString("url_imagem"));
					avatar.setDescricao(rs.getString("descricao"));
					avatar.setDataCadastro(rs.getTimestamp("data_cadastro"));
					avatar.setAtivo(rs.getBoolean("ativo"));
					
					return avatar;
				}
				);
	}

	public Avatar buscarAvatarId(Long idAvatar) {
		try {
		return jdbcTemplate.queryForObject("select * from tb_avatar where id_avatar=? ",
				new Object[]{ idAvatar }, 
				(rs, rowNum) -> {
					Avatar avatar = new Avatar();
					avatar.setIdAvatar(rs.getLong("id_avatar"));
					avatar.setUrlImagem(rs.getString("url_imagem"));
					avatar.setDescricao(rs.getString("descricao"));
					avatar.setDataCadastro(rs.getTimestamp("data_cadastro"));
					avatar.setAtivo(rs.getBoolean("ativo"));
					
					return avatar;
				});
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
