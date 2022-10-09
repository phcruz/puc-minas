package br.com.phc.pitaco.utilities.repository.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.phc.pitaco.utilities.Constantes;
import br.com.phc.pitaco.utilities.dto.EquipeDTO;
import br.com.phc.pitaco.utilities.dto.PalpiteDTO;
import br.com.phc.pitaco.utilities.dto.PartidaDTO;
import br.com.phc.pitaco.utilities.model.Partida;
import br.com.phc.pitaco.utilities.util.UtilData;

@ConditionalOnProperty(name = Constantes.ENABLE_DATASOURCE, havingValue = "true", matchIfMissing = false)
@Repository
public class PartidaJdbcRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<PartidaDTO> listarPartidasHojeLigasUsuarioPaginado(Long idUsuario, Integer inicio) {
		return jdbcTemplate.query("select p.id_partida, p.id_liga, p.id_equipe_casa, p.id_equipe_visitante, p.placar_equipe_casa, p.placar_equipe_visitante, "
				+ "p.fase, p.tag_grupo, p.local_jogo, p.placar_extendido_equipe_casa, p.placar_extendido_equipe_visitante, p.data_hora_jogo, p.tempo_partida, "
				+ "p.gols_equipe_casa, p.gols_equipe_visitante, "
				+ "pal.id_palpite, pal.id_partida, pal.id_usuario, pal.placar_equipe_casa, pal.placar_equipe_visitante, pal.pontos, "
				+ "eqLo.nome, eqLo.url_imagem_equipe, eqVi.nome, eqVi.url_imagem_equipe, eqLo.sigla_equipe, eqVi.sigla_equipe, "
				+ "(select url_imagem from tb_liga as li where li.id_liga = p.id_liga) as urlImagemLogoLiga, "
				+ "if(p.data_hora_jogo > sysdate(),false,true) as mostraPontuacao, "
				+ "if(DATE_SUB(p.data_hora_jogo, INTERVAL 30 MINUTE) >= SYSDATE(), true, false) as permiteAposta, "
				//+ "(select ras.ativo from raspagem as ras where ras.idPartida = p.id_partida) as fimPartida "
				+ "if(ifnull(p.tempo_partida, 'Vazio') != 'Encerrado', true, false) as fimPartida, "
				+ "if(ifnull(p.local_jogo, 'Vazio') != 'JOGO ADIADO', true, false) as jogoAdiado "
				+ "from tb_partida as p left join tb_palpite as pal on p.id_partida = pal.id_partida and pal.id_usuario = ?, "
				+ "tb_equipe as eqLo, tb_equipe as eqVi "
				+ "where p.id_equipe_casa = eqLo.id_equipe "
				+ "and p.id_equipe_visitante = eqVi.id_equipe "
				+ "and DATEDIFF(p.data_hora_jogo, CURRENT_DATE()) = 0 "
				+ "and p.id_liga in (select lu.id_liga from tb_liga_usuario lu where lu.id_usuario = ? and lu.ativo = 1) "
				+ "order by fimPartida desc, jogoAdiado desc, p.data_hora_jogo asc, p.id_partida asc "
				//+ "order by p.data_hora_jogo desc "
				+ "limit ?, " + Constantes.LIMITE_CONSULTA_BANCO,
				new Object[]{ idUsuario, idUsuario, inicio }, 
				(rs, rowNum) -> {
					PartidaDTO partida = new PartidaDTO();

					partida.setIdPartida(rs.getLong("p.id_partida"));
					partida.setIdLiga(rs.getLong("p.id_liga"));
					partida.setPlacarEquipeCasa(rs.getInt("p.placar_equipe_casa"));
					partida.setPlacarEquipeVisitante(rs.getInt("p.placar_equipe_visitante"));
					partida.setFase(rs.getString("p.fase"));
					partida.setTagGrupo(rs.getString("p.tag_grupo"));
					partida.setLocalJogo(rs.getString("p.local_jogo"));
					partida.setPlacarExtendidoEquipeCasa(rs.getString("p.placar_extendido_equipe_casa"));
					partida.setPlacarExtendidoEquipeVisitante(rs.getString("p.placar_extendido_equipe_visitante"));
					partida.setDataHoraJogo(rs.getTimestamp("p.data_hora_jogo"));
					partida.setDataHoraJogoTexto(UtilData.formatarDataString(rs.getTimestamp("p.data_hora_jogo")));
					partida.setMostraPontuacao(rs.getBoolean("mostraPontuacao"));
					partida.setPermiteAposta(rs.getBoolean("permiteAposta"));
					partida.setTempoPartida(rs.getString("p.tempo_partida"));
					partida.setGolsEquipeCasa(rs.getString("p.gols_equipe_casa"));
					partida.setGolsEquipeVisitante(rs.getString("p.gols_equipe_visitante"));
					
					PalpiteDTO palpite = new PalpiteDTO();
					palpite.setIdPalpite(rs.getLong("pal.id_palpite"));
					palpite.setIdPartida(rs.getLong("pal.id_partida"));
					palpite.setIdUsuario(rs.getLong("pal.id_usuario"));
					palpite.setPlacarEquipeCasa(rs.getInt("pal.placar_equipe_casa"));
					palpite.setPlacarEquipeVisitante(rs.getInt("pal.placar_equipe_visitante"));
					palpite.setPontos(rs.getInt("pal.pontos"));
					partida.setPalpite(palpite);
					
					EquipeDTO mandante = new EquipeDTO(rs.getLong("p.id_equipe_casa"), rs.getString("eqLo.nome"), rs.getString("eqLo.sigla_equipe"), rs.getString("eqLo.url_imagem_equipe"));
					partida.setMandante(mandante);
					EquipeDTO visitante = new EquipeDTO(rs.getLong("p.id_equipe_visitante"), rs.getString("eqVi.nome"), rs.getString("eqVi.sigla_equipe"), rs.getString("eqVi.url_imagem_equipe"));
					partida.setVisitante(visitante);
					
					partida.setUrlImagemLogoLiga(rs.getString("urlImagemLogoLiga"));
										
					return partida;
				}
				);
	}

	public List<PartidaDTO> listarPartidasHojeLigasUsuarioFimPaginado(Long idUsuario, Integer inicio, Integer fim) {
		return jdbcTemplate.query("select p.id_partida, p.id_liga, p.id_equipe_casa, p.id_equipe_visitante, p.placar_equipe_casa, p.placar_equipe_visitante, "
				+ "p.fase, p.tag_grupo, p.local_jogo, p.placar_extendido_equipe_casa, p.placar_extendido_equipe_visitante, p.data_hora_jogo, p.tempo_partida, "
				+ "p.gols_equipe_casa, p.gols_equipe_visitante, "
				+ "pal.id_palpite, pal.id_partida, pal.id_usuario, pal.placar_equipe_casa, pal.placar_equipe_visitante, pal.pontos, "
				+ "eqLo.nome, eqLo.url_imagem_equipe, eqVi.nome, eqVi.url_imagem_equipe, eqLo.sigla_equipe, eqVi.sigla_equipe, "
				+ "(select url_imagem from tb_liga as li where li.id_liga = p.id_liga) as urlImagemLogoLiga, "
				+ "if(p.data_hora_jogo > sysdate(),false,true) as mostraPontuacao, "
				+ "if(DATE_SUB(p.data_hora_jogo, INTERVAL 30 MINUTE) >= SYSDATE(), true, false) as permiteAposta, "
				+ "(select ras.ativo from raspagem as ras where ras.idPartida = p.id_partida) as fimPartida "
				+ "from tb_partida as p left join tb_palpite as pal on p.id_partida = pal.id_partida and pal.id_usuario = ?, "
				+ "tb_equipe as eqLo, tb_equipe as eqVi "
				+ "where p.id_equipe_casa = eqLo.id_equipe "
				+ "and p.id_equipe_visitante = eqVi.id_equipe "
				+ "and DATEDIFF(p.data_hora_jogo, CURRENT_DATE()) = 0 "
				+ "and p.id_liga in (select lu.id_liga from tb_liga_usuario lu where lu.id_usuario = ? and lu.ativo = 1) "
				+ "order by fimPartida desc, p.data_hora_jogo asc, p.id_partida asc "
				//+ "order by p.data_hora_jogo desc "
				+ "limit ?, ? ",
				new Object[]{ idUsuario, idUsuario, inicio, fim }, 
				(rs, rowNum) -> {
					PartidaDTO partida = new PartidaDTO();

					partida.setIdPartida(rs.getLong("p.id_partida"));
					partida.setIdLiga(rs.getLong("p.id_liga"));
					partida.setPlacarEquipeCasa(rs.getInt("p.placar_equipe_casa"));
					partida.setPlacarEquipeVisitante(rs.getInt("p.placar_equipe_visitante"));
					partida.setFase(rs.getString("p.fase"));
					partida.setTagGrupo(rs.getString("p.tag_grupo"));
					partida.setLocalJogo(rs.getString("p.local_jogo"));
					partida.setPlacarExtendidoEquipeCasa(rs.getString("p.placar_extendido_equipe_casa"));
					partida.setPlacarExtendidoEquipeVisitante(rs.getString("p.placar_extendido_equipe_visitante"));
					partida.setDataHoraJogo(rs.getTimestamp("p.data_hora_jogo"));
					partida.setDataHoraJogoTexto(UtilData.formatarDataString(rs.getTimestamp("p.data_hora_jogo")));
					partida.setMostraPontuacao(rs.getBoolean("mostraPontuacao"));
					partida.setPermiteAposta(rs.getBoolean("permiteAposta"));
					partida.setTempoPartida(rs.getString("p.tempo_partida"));
					partida.setGolsEquipeCasa(rs.getString("p.gols_equipe_casa"));
					partida.setGolsEquipeVisitante(rs.getString("p.gols_equipe_visitante"));

					PalpiteDTO palpite = new PalpiteDTO();
					palpite.setIdPalpite(rs.getLong("pal.id_palpite"));
					palpite.setIdPartida(rs.getLong("pal.id_partida"));
					palpite.setIdUsuario(rs.getLong("pal.id_usuario"));
					palpite.setPlacarEquipeCasa(rs.getInt("pal.placar_equipe_casa"));
					palpite.setPlacarEquipeVisitante(rs.getInt("pal.placar_equipe_visitante"));
					palpite.setPontos(rs.getInt("pal.pontos"));
					partida.setPalpite(palpite);
					
					EquipeDTO mandante = new EquipeDTO(rs.getLong("p.id_equipe_casa"), rs.getString("eqLo.nome"), rs.getString("eqLo.sigla_equipe"), rs.getString("eqLo.url_imagem_equipe"));
					partida.setMandante(mandante);
					EquipeDTO visitante = new EquipeDTO(rs.getLong("p.id_equipe_visitante"), rs.getString("eqVi.nome"), rs.getString("eqVi.sigla_equipe"), rs.getString("eqVi.url_imagem_equipe"));
					partida.setVisitante(visitante);
					
					partida.setUrlImagemLogoLiga(rs.getString("urlImagemLogoLiga"));
					
					return partida;
				}
				);
	}

	public List<PartidaDTO> listarPartidasLigaUsuarioPaginado(Long idLiga, Long idUsuario, Integer inicio) {
		return jdbcTemplate.query("select p.id_partida, p.id_liga, p.id_equipe_casa, p.id_equipe_visitante, p.placar_equipe_casa, p.placar_equipe_visitante, "
				+ "p.fase, p.tag_grupo, p.local_jogo, p.placar_extendido_equipe_casa, p.placar_extendido_equipe_visitante, p.data_hora_jogo, p.tempo_partida, "
				+ "p.gols_equipe_casa, p.gols_equipe_visitante, "
				+ "pal.id_palpite, pal.id_partida, pal.id_usuario, pal.placar_equipe_casa, pal.placar_equipe_visitante, pal.pontos, "
				+ "eqLo.nome, eqLo.url_imagem_equipe, eqVi.nome, eqVi.url_imagem_equipe, eqLo.sigla_equipe, eqVi.sigla_equipe, "
				+ "(select url_imagem from tb_liga as li where li.id_liga = p.id_liga) as urlImagemLogoLiga, "
				+ "if(p.data_hora_jogo > sysdate(),false,true) as mostraPontuacao,"
				+ "if(DATE_SUB(p.data_hora_jogo, INTERVAL 30 MINUTE) >= SYSDATE(), true, false) as permiteAposta "
				+ "from tb_partida as p left join tb_palpite as pal on p.id_partida = pal.id_partida and pal.id_usuario = ?, "
				+ "tb_equipe as eqLo, tb_equipe as eqVi "
				+ "where p.id_equipe_casa = eqLo.id_equipe "
				+ "and p.id_equipe_visitante = eqVi.id_equipe "
				+ "and p.id_liga = ? and p.id_liga in "
				+ "(select lu.id_liga from tb_liga_usuario as lu where lu.ativo=1 and id_usuario=? )"
				+ "order by p.data_hora_jogo desc "
				+ "limit ?, " + Constantes.LIMITE_CONSULTA_BANCO,
				new Object[]{ idUsuario, idLiga, idUsuario, inicio }, 
				(rs, rowNum) -> {
					PartidaDTO partida = new PartidaDTO();

					partida.setIdPartida(rs.getLong("p.id_partida"));
					partida.setIdLiga(rs.getLong("p.id_liga"));
					partida.setPlacarEquipeCasa(rs.getInt("p.placar_equipe_casa"));
					partida.setPlacarEquipeVisitante(rs.getInt("p.placar_equipe_visitante"));
					partida.setFase(rs.getString("p.fase"));
					partida.setTagGrupo(rs.getString("p.tag_grupo"));
					partida.setLocalJogo(rs.getString("p.local_jogo"));
					partida.setPlacarExtendidoEquipeCasa(rs.getString("p.placar_extendido_equipe_casa"));
					partida.setPlacarExtendidoEquipeVisitante(rs.getString("p.placar_extendido_equipe_visitante"));
					partida.setDataHoraJogo(rs.getTimestamp("p.data_hora_jogo"));
					partida.setDataHoraJogoTexto(UtilData.formatarDataString(rs.getTimestamp("p.data_hora_jogo")));
					partida.setMostraPontuacao(rs.getBoolean("mostraPontuacao"));
					partida.setPermiteAposta(rs.getBoolean("permiteAposta"));
					partida.setTempoPartida(rs.getString("p.tempo_partida"));
					partida.setGolsEquipeCasa(rs.getString("p.gols_equipe_casa"));
					partida.setGolsEquipeVisitante(rs.getString("p.gols_equipe_visitante"));
					
					PalpiteDTO palpite = new PalpiteDTO();
					palpite.setIdPalpite(rs.getLong("pal.id_palpite"));
					palpite.setIdPartida(rs.getLong("pal.id_partida"));
					palpite.setIdUsuario(rs.getLong("pal.id_usuario"));
					palpite.setPlacarEquipeCasa(rs.getInt("pal.placar_equipe_casa"));
					palpite.setPlacarEquipeVisitante(rs.getInt("pal.placar_equipe_visitante"));
					palpite.setPontos(rs.getInt("pal.pontos"));
					partida.setPalpite(palpite);

					EquipeDTO mandante = new EquipeDTO(rs.getLong("p.id_equipe_casa"), rs.getString("eqLo.nome"), rs.getString("eqLo.sigla_equipe"), rs.getString("eqLo.url_imagem_equipe"));
					partida.setMandante(mandante);
					EquipeDTO visitante = new EquipeDTO(rs.getLong("p.id_equipe_visitante"), rs.getString("eqVi.nome"), rs.getString("eqVi.sigla_equipe"), rs.getString("eqVi.url_imagem_equipe"));
					partida.setVisitante(visitante);
					
					partida.setUrlImagemLogoLiga(rs.getString("urlImagemLogoLiga"));
					
					return partida;
				}
				);
	}

	public List<PartidaDTO> listarPartidasLigaUsuarioPosPalpitePaginado(Long idLiga, Long idUsuario, Integer inicio, Integer fim) {
		return jdbcTemplate.query("select p.id_partida, p.id_liga, p.id_equipe_casa, p.id_equipe_visitante, p.placar_equipe_casa, p.placar_equipe_visitante, "
				+ "p.fase, p.tag_grupo, p.local_jogo, p.placar_extendido_equipe_casa, p.placar_extendido_equipe_visitante, p.data_hora_jogo, p.tempo_partida, "
				+ "p.gols_equipe_casa, p.gols_equipe_visitante, "
				+ "pal.id_palpite, pal.id_partida, pal.id_usuario, pal.placar_equipe_casa, pal.placar_equipe_visitante, pal.pontos, "
				+ "eqLo.nome, eqLo.url_imagem_equipe, eqVi.nome, eqVi.url_imagem_equipe, eqLo.sigla_equipe, eqVi.sigla_equipe, "
				+ "(select url_imagem from tb_liga as li where li.id_liga = p.id_liga) as urlImagemLogoLiga, "
				+ "if(p.data_hora_jogo > sysdate(),false,true) as mostraPontuacao,"
				+ "if(DATE_SUB(p.data_hora_jogo, INTERVAL 30 MINUTE) >= SYSDATE(), true, false) as permiteAposta "
				+ "from tb_partida as p left join tb_palpite as pal on p.id_partida = pal.id_partida and pal.id_usuario = ?, "
				+ "tb_equipe as eqLo, tb_equipe as eqVi "
				+ "where p.id_equipe_casa = eqLo.id_equipe "
				+ "and p.id_equipe_visitante = eqVi.id_equipe "
				+ "and p.id_liga = ? and p.id_liga in "
				+ "(select lu.id_liga from tb_liga_usuario as lu where lu.ativo=1 and id_usuario=? )"
				+ "order by p.data_hora_jogo desc "
				+ "limit ?, ? ",
				new Object[]{ idUsuario, idLiga, idUsuario, inicio, fim }, 
				(rs, rowNum) -> {
					PartidaDTO partida = new PartidaDTO();

					partida.setIdPartida(rs.getLong("p.id_partida"));
					partida.setIdLiga(rs.getLong("p.id_liga"));
					partida.setPlacarEquipeCasa(rs.getInt("p.placar_equipe_casa"));
					partida.setPlacarEquipeVisitante(rs.getInt("p.placar_equipe_visitante"));
					partida.setFase(rs.getString("p.fase"));
					partida.setTagGrupo(rs.getString("p.tag_grupo"));
					partida.setLocalJogo(rs.getString("p.local_jogo"));
					partida.setPlacarExtendidoEquipeCasa(rs.getString("p.placar_extendido_equipe_casa"));
					partida.setPlacarExtendidoEquipeVisitante(rs.getString("p.placar_extendido_equipe_visitante"));
					partida.setDataHoraJogo(rs.getTimestamp("p.data_hora_jogo"));
					partida.setDataHoraJogoTexto(UtilData.formatarDataString(rs.getTimestamp("p.data_hora_jogo")));
					partida.setMostraPontuacao(rs.getBoolean("mostraPontuacao"));
					partida.setPermiteAposta(rs.getBoolean("permiteAposta"));
					partida.setTempoPartida(rs.getString("p.tempo_partida"));
					partida.setGolsEquipeCasa(rs.getString("p.gols_equipe_casa"));
					partida.setGolsEquipeVisitante(rs.getString("p.gols_equipe_visitante"));
					
					PalpiteDTO palpite = new PalpiteDTO();
					palpite.setIdPalpite(rs.getLong("pal.id_palpite"));
					palpite.setIdPartida(rs.getLong("pal.id_partida"));
					palpite.setIdUsuario(rs.getLong("pal.id_usuario"));
					palpite.setPlacarEquipeCasa(rs.getInt("pal.placar_equipe_casa"));
					palpite.setPlacarEquipeVisitante(rs.getInt("pal.placar_equipe_visitante"));
					palpite.setPontos(rs.getInt("pal.pontos"));
					partida.setPalpite(palpite);

					EquipeDTO mandante = new EquipeDTO(rs.getLong("p.id_equipe_casa"), rs.getString("eqLo.nome"), rs.getString("eqLo.sigla_equipe"), rs.getString("eqLo.url_imagem_equipe"));
					partida.setMandante(mandante);
					EquipeDTO visitante = new EquipeDTO(rs.getLong("p.id_equipe_visitante"), rs.getString("eqVi.nome"), rs.getString("eqVi.sigla_equipe"), rs.getString("eqVi.url_imagem_equipe"));
					partida.setVisitante(visitante);
					
					partida.setUrlImagemLogoLiga(rs.getString("urlImagemLogoLiga"));
					
					return partida;
				}
				);
	}

	public List<PartidaDTO> listarPartidasPalpiteUsuarioValidaDataPaginado(Long idUsuario, Integer inicio) {
		return jdbcTemplate.query("select p.id_partida, p.id_liga, p.id_equipe_casa, p.id_equipe_visitante, p.placar_equipe_casa, p.placar_equipe_visitante, "
				+ "p.fase, p.tag_grupo, p.local_jogo, p.placar_extendido_equipe_casa, p.placar_extendido_equipe_visitante, p.data_hora_jogo, p.tempo_partida, "
				+ "p.gols_equipe_casa, p.gols_equipe_visitante, "
				+ "pal.id_palpite, pal.id_partida, pal.id_usuario, pal.placar_equipe_casa, pal.placar_equipe_visitante, pal.pontos, "
				+ "eqLo.nome, eqLo.url_imagem_equipe, eqVi.nome, eqVi.url_imagem_equipe, eqLo.sigla_equipe, eqVi.sigla_equipe, "
				+ "(select url_imagem from tb_liga as li where li.id_liga = p.id_liga) as urlImagemLogoLiga, "
				+ "if(p.data_hora_jogo > sysdate(),false,true) as mostraPontuacao "
				+ "from tb_partida as p left join tb_palpite as pal on p.id_partida = pal.id_partida and pal.id_usuario = ?, "
				+ "tb_equipe as eqLo, tb_equipe as eqVi "
				+ "where p.id_partida = pal.id_partida "
				+ "and p.id_equipe_casa = eqLo.id_equipe "
				+ "and p.id_equipe_visitante = eqVi.id_equipe "
				+ "and sysdate() >= DATE_SUB(p.data_hora_jogo, INTERVAL 30 MINUTE) "
				+ "and p.id_liga in (select lu.id_liga from tb_liga_usuario as lu where id_usuario=? ) "
				+ "order by p.data_hora_jogo desc "
				+ "limit ?, " + Constantes.LIMITE_CONSULTA_BANCO,
				new Object[]{ idUsuario, idUsuario, inicio }, 
				(rs, rowNum) -> {
					PartidaDTO partida = new PartidaDTO();

					partida.setIdPartida(rs.getLong("p.id_partida"));
					partida.setIdLiga(rs.getLong("p.id_liga"));
					partida.setPlacarEquipeCasa(rs.getInt("p.placar_equipe_casa"));
					partida.setPlacarEquipeVisitante(rs.getInt("p.placar_equipe_visitante"));
					partida.setFase(rs.getString("p.fase"));
					partida.setTagGrupo(rs.getString("p.tag_grupo"));
					partida.setLocalJogo(rs.getString("p.local_jogo"));
					partida.setPlacarExtendidoEquipeCasa(rs.getString("p.placar_extendido_equipe_casa"));
					partida.setPlacarExtendidoEquipeVisitante(rs.getString("p.placar_extendido_equipe_visitante"));
					partida.setDataHoraJogo(rs.getTimestamp("p.data_hora_jogo"));
					partida.setDataHoraJogoTexto(UtilData.formatarDataString(rs.getTimestamp("p.data_hora_jogo")));
					partida.setMostraPontuacao(rs.getBoolean("mostraPontuacao"));
					partida.setPermiteAposta(false);
					partida.setTempoPartida(rs.getString("p.tempo_partida"));
					partida.setGolsEquipeCasa(rs.getString("p.gols_equipe_casa"));
					partida.setGolsEquipeVisitante(rs.getString("p.gols_equipe_visitante"));
					
					PalpiteDTO palpite = new PalpiteDTO();
					palpite.setIdPalpite(rs.getLong("pal.id_palpite"));
					palpite.setIdPartida(rs.getLong("pal.id_partida"));
					palpite.setIdUsuario(rs.getLong("pal.id_usuario"));
					palpite.setPlacarEquipeCasa(rs.getInt("pal.placar_equipe_casa"));
					palpite.setPlacarEquipeVisitante(rs.getInt("pal.placar_equipe_visitante"));
					palpite.setPontos(rs.getInt("pal.pontos"));
					partida.setPalpite(palpite);

					EquipeDTO mandante = new EquipeDTO(rs.getLong("p.id_equipe_casa"), rs.getString("eqLo.nome"), rs.getString("eqLo.sigla_equipe"), rs.getString("eqLo.url_imagem_equipe"));
					partida.setMandante(mandante);
					EquipeDTO visitante = new EquipeDTO(rs.getLong("p.id_equipe_visitante"), rs.getString("eqVi.nome"), rs.getString("eqVi.sigla_equipe"), rs.getString("eqVi.url_imagem_equipe"));
					partida.setVisitante(visitante);
					
					partida.setUrlImagemLogoLiga(rs.getString("urlImagemLogoLiga"));
					
					return partida;
				}
				);
	}

	public List<PartidaDTO> listarPartidasPalpiteLigaUsuarioValidaDataPaginado(Long idLiga, Long idUsuario, Integer inicio) {
		return jdbcTemplate.query("select p.id_partida, p.id_liga, p.id_equipe_casa, p.id_equipe_visitante, p.placar_equipe_casa, p.placar_equipe_visitante, "
				+ "p.fase, p.tag_grupo, p.local_jogo, p.placar_extendido_equipe_casa, p.placar_extendido_equipe_visitante, p.data_hora_jogo, p.tempo_partida, "
				+ "p.gols_equipe_casa, p.gols_equipe_visitante, "
				+ "pal.id_palpite, pal.id_partida, pal.id_usuario, pal.placar_equipe_casa, pal.placar_equipe_visitante, pal.pontos, "
				+ "eqLo.nome, eqLo.url_imagem_equipe, eqVi.nome, eqVi.url_imagem_equipe, eqLo.sigla_equipe, eqVi.sigla_equipe, "
				+ "(select url_imagem from tb_liga as li where li.id_liga = p.id_liga) as urlImagemLogoLiga, "
				+ "if(p.data_hora_jogo > sysdate(),false,true) as mostraPontuacao "
				+ "from tb_partida as p left join tb_palpite as pal on p.id_partida = pal.id_partida and pal.id_usuario = ?, "
				+ "tb_equipe as eqLo, tb_equipe as eqVi "
				+ "where p.id_partida = pal.id_partida "
				+ "and p.id_equipe_casa = eqLo.id_equipe "
				+ "and p.id_equipe_visitante = eqVi.id_equipe "
				+ "and p.local_jogo != '" + Constantes.JOGO_ADIADO + "' "
				+ "and sysdate() >= DATE_SUB(p.data_hora_jogo, INTERVAL 30 MINUTE) "
				+ "and p.id_liga = ? "
				+ "and p.id_liga in (select lu.id_liga from tb_liga_usuario as lu where id_usuario=? ) "
				+ "order by p.data_hora_jogo desc "
				+ "limit ?, " + Constantes.LIMITE_CONSULTA_BANCO,
				new Object[]{ idUsuario, idLiga, idUsuario, inicio }, 
				(rs, rowNum) -> {
					PartidaDTO partida = new PartidaDTO();

					partida.setIdPartida(rs.getLong("p.id_partida"));
					partida.setIdLiga(rs.getLong("p.id_liga"));
					partida.setPlacarEquipeCasa(rs.getInt("p.placar_equipe_casa"));
					partida.setPlacarEquipeVisitante(rs.getInt("p.placar_equipe_visitante"));
					partida.setFase(rs.getString("p.fase"));
					partida.setTagGrupo(rs.getString("p.tag_grupo"));
					partida.setLocalJogo(rs.getString("p.local_jogo"));
					partida.setPlacarExtendidoEquipeCasa(rs.getString("p.placar_extendido_equipe_casa"));
					partida.setPlacarExtendidoEquipeVisitante(rs.getString("p.placar_extendido_equipe_visitante"));
					partida.setDataHoraJogo(rs.getTimestamp("p.data_hora_jogo"));
					partida.setDataHoraJogoTexto(UtilData.formatarDataString(rs.getTimestamp("p.data_hora_jogo")));
					partida.setMostraPontuacao(rs.getBoolean("mostraPontuacao"));
					partida.setPermiteAposta(false);
					partida.setTempoPartida(rs.getString("p.tempo_partida"));
					partida.setGolsEquipeCasa(rs.getString("p.gols_equipe_casa"));
					partida.setGolsEquipeVisitante(rs.getString("p.gols_equipe_visitante"));
					
					PalpiteDTO palpite = new PalpiteDTO();
					palpite.setIdPalpite(rs.getLong("pal.id_palpite"));
					palpite.setIdPartida(rs.getLong("pal.id_partida"));
					palpite.setIdUsuario(rs.getLong("pal.id_usuario"));
					palpite.setPlacarEquipeCasa(rs.getInt("pal.placar_equipe_casa"));
					palpite.setPlacarEquipeVisitante(rs.getInt("pal.placar_equipe_visitante"));
					palpite.setPontos(rs.getInt("pal.pontos"));
					partida.setPalpite(palpite);

					EquipeDTO mandante = new EquipeDTO(rs.getLong("p.id_equipe_casa"), rs.getString("eqLo.nome"), rs.getString("eqLo.sigla_equipe"), rs.getString("eqLo.url_imagem_equipe"));
					partida.setMandante(mandante);
					EquipeDTO visitante = new EquipeDTO(rs.getLong("p.id_equipe_visitante"), rs.getString("eqVi.nome"), rs.getString("eqVi.sigla_equipe"), rs.getString("eqVi.url_imagem_equipe"));
					partida.setVisitante(visitante);
					
					partida.setUrlImagemLogoLiga(rs.getString("urlImagemLogoLiga"));
					
					return partida;
				}
				);
	}

	public List<PartidaDTO> listarPartidasPalpiteUsuarioPaginado(Long idUsuario, Integer inicio) {
		return jdbcTemplate.query("select p.id_partida, p.id_liga, p.id_equipe_casa, p.id_equipe_visitante, p.placar_equipe_casa, p.placar_equipe_visitante, "
				+ "p.fase, p.tag_grupo, p.local_jogo, p.placar_extendido_equipe_casa, p.placar_extendido_equipe_visitante, p.data_hora_jogo, p.tempo_partida, "
				+ "p.gols_equipe_casa, p.gols_equipe_visitante, "
				+ "pal.id_palpite, pal.id_partida, pal.id_usuario, pal.placar_equipe_casa, pal.placar_equipe_visitante, pal.pontos, "
				+ "eqLo.nome, eqLo.url_imagem_equipe, eqVi.nome, eqVi.url_imagem_equipe, eqLo.sigla_equipe, eqVi.sigla_equipe, "
				+ "(select url_imagem from tb_liga as li where li.id_liga = p.id_liga) as urlImagemLogoLiga, "
				+ "if(p.data_hora_jogo > sysdate(),false,true) as mostraPontuacao "
				+ "from tb_partida as p left join tb_palpite as pal on p.id_partida = pal.id_partida and pal.id_usuario = ?, "
				+ "tb_equipe as eqLo, tb_equipe as eqVi "
				+ "where p.id_partida = pal.id_partida "
				+ "and p.id_equipe_casa = eqLo.id_equipe "
				+ "and p.id_equipe_visitante = eqVi.id_equipe "
				//+ "and sysdate() >= DATE_SUB(p.data_hora_jogo, INTERVAL 30 MINUTE) "
				+ "and p.id_liga in (select lu.id_liga from tb_liga_usuario as lu where id_usuario=? ) "
				+ "order by p.data_hora_jogo desc "
				+ "limit ?, " + Constantes.LIMITE_CONSULTA_BANCO,
				new Object[]{ idUsuario, idUsuario, inicio }, 
				(rs, rowNum) -> {
					PartidaDTO partida = new PartidaDTO();

					partida.setIdPartida(rs.getLong("p.id_partida"));
					partida.setIdLiga(rs.getLong("p.id_liga"));
					partida.setPlacarEquipeCasa(rs.getInt("p.placar_equipe_casa"));
					partida.setPlacarEquipeVisitante(rs.getInt("p.placar_equipe_visitante"));
					partida.setFase(rs.getString("p.fase"));
					partida.setTagGrupo(rs.getString("p.tag_grupo"));
					partida.setLocalJogo(rs.getString("p.local_jogo"));
					partida.setPlacarExtendidoEquipeCasa(rs.getString("p.placar_extendido_equipe_casa"));
					partida.setPlacarExtendidoEquipeVisitante(rs.getString("p.placar_extendido_equipe_visitante"));
					partida.setDataHoraJogo(rs.getTimestamp("p.data_hora_jogo"));
					partida.setDataHoraJogoTexto(UtilData.formatarDataString(rs.getTimestamp("p.data_hora_jogo")));
					partida.setMostraPontuacao(rs.getBoolean("mostraPontuacao"));
					partida.setPermiteAposta(false);
					partida.setTempoPartida(rs.getString("p.tempo_partida"));
					partida.setGolsEquipeCasa(rs.getString("p.gols_equipe_casa"));
					partida.setGolsEquipeVisitante(rs.getString("p.gols_equipe_visitante"));
					
					PalpiteDTO palpite = new PalpiteDTO();
					palpite.setIdPalpite(rs.getLong("pal.id_palpite"));
					palpite.setIdPartida(rs.getLong("pal.id_partida"));
					palpite.setIdUsuario(rs.getLong("pal.id_usuario"));
					palpite.setPlacarEquipeCasa(rs.getInt("pal.placar_equipe_casa"));
					palpite.setPlacarEquipeVisitante(rs.getInt("pal.placar_equipe_visitante"));
					palpite.setPontos(rs.getInt("pal.pontos"));
					partida.setPalpite(palpite);

					EquipeDTO mandante = new EquipeDTO(rs.getLong("p.id_equipe_casa"), rs.getString("eqLo.nome"), rs.getString("eqLo.sigla_equipe"), rs.getString("eqLo.url_imagem_equipe"));
					partida.setMandante(mandante);
					EquipeDTO visitante = new EquipeDTO(rs.getLong("p.id_equipe_visitante"), rs.getString("eqVi.nome"), rs.getString("eqVi.sigla_equipe"), rs.getString("eqVi.url_imagem_equipe"));
					partida.setVisitante(visitante);
					
					partida.setUrlImagemLogoLiga(rs.getString("urlImagemLogoLiga"));
					
					return partida;
				}
				);
	}

	public List<PartidaDTO> listarPartidasPalpiteLigaUsuarioPaginado(Long idLiga, Long idUsuario, Integer inicio) {
		return jdbcTemplate.query("select p.id_partida, p.id_liga, p.id_equipe_casa, p.id_equipe_visitante, p.placar_equipe_casa, p.placar_equipe_visitante, "
				+ "p.fase, p.tag_grupo, p.local_jogo, p.placar_extendido_equipe_casa, p.placar_extendido_equipe_visitante, p.data_hora_jogo, p.tempo_partida, "
				+ "p.gols_equipe_casa, p.gols_equipe_visitante, "
				+ "pal.id_palpite, pal.id_partida, pal.id_usuario, pal.placar_equipe_casa, pal.placar_equipe_visitante, pal.pontos, "
				+ "eqLo.nome, eqLo.url_imagem_equipe, eqVi.nome, eqVi.url_imagem_equipe, eqLo.sigla_equipe, eqVi.sigla_equipe, "
				+ "(select url_imagem from tb_liga as li where li.id_liga = p.id_liga) as urlImagemLogoLiga, "
				+ "if(p.data_hora_jogo > sysdate(),false,true) as mostraPontuacao "
				+ "from tb_partida as p left join tb_palpite as pal on p.id_partida = pal.id_partida and pal.id_usuario = ?, "
				+ "tb_equipe as eqLo, tb_equipe as eqVi "
				+ "where p.id_partida = pal.id_partida "
				+ "and p.id_equipe_casa = eqLo.id_equipe "
				+ "and p.id_equipe_visitante = eqVi.id_equipe "
				//+ "and p.data_hora_jogo < sysdate() "
				+ "and p.id_liga = ? "
				+ "and p.id_liga in (select lu.id_liga from tb_liga_usuario as lu where id_usuario=? ) "
				+ "order by p.data_hora_jogo desc "
				+ "limit ?, " + Constantes.LIMITE_CONSULTA_BANCO,
				new Object[]{ idUsuario, idLiga, idUsuario, inicio }, 
				(rs, rowNum) -> {
					PartidaDTO partida = new PartidaDTO();

					partida.setIdPartida(rs.getLong("p.id_partida"));
					partida.setIdLiga(rs.getLong("p.id_liga"));
					partida.getMandante().setIdEquipe(rs.getLong("p.id_equipe_casa"));
					partida.getVisitante().setIdEquipe(rs.getLong("p.id_equipe_visitante"));
					partida.setPlacarEquipeCasa(rs.getInt("p.placar_equipe_casa"));
					partida.setPlacarEquipeVisitante(rs.getInt("p.placar_equipe_visitante"));
					partida.setFase(rs.getString("p.fase"));
					partida.setTagGrupo(rs.getString("p.tag_grupo"));
					partida.setLocalJogo(rs.getString("p.local_jogo"));
					partida.setPlacarExtendidoEquipeCasa(rs.getString("p.placar_extendido_equipe_casa"));
					partida.setPlacarExtendidoEquipeVisitante(rs.getString("p.placar_extendido_equipe_visitante"));
					partida.setDataHoraJogo(rs.getTimestamp("p.data_hora_jogo"));
					partida.setDataHoraJogoTexto(UtilData.formatarDataString(rs.getTimestamp("p.data_hora_jogo")));
					partida.setMostraPontuacao(rs.getBoolean("mostraPontuacao"));
					partida.setPermiteAposta(false);
					partida.setTempoPartida(rs.getString("p.tempo_partida"));
					partida.setGolsEquipeCasa(rs.getString("p.gols_equipe_casa"));
					partida.setGolsEquipeVisitante(rs.getString("p.gols_equipe_visitante"));
					
					PalpiteDTO palpite = new PalpiteDTO();
					palpite.setIdPalpite(rs.getLong("pal.id_palpite"));
					palpite.setIdPartida(rs.getLong("pal.id_partida"));
					palpite.setIdUsuario(rs.getLong("pal.id_usuario"));
					palpite.setPlacarEquipeCasa(rs.getInt("pal.placar_equipe_casa"));
					palpite.setPlacarEquipeVisitante(rs.getInt("pal.placar_equipe_visitante"));
					palpite.setPontos(rs.getInt("pal.pontos"));
					partida.setPalpite(palpite);
					
					EquipeDTO mandante = new EquipeDTO(rs.getLong("p.id_equipe_casa"), rs.getString("eqLo.nome"), rs.getString("eqLo.sigla_equipe"), rs.getString("eqLo.url_imagem_equipe"));
					partida.setMandante(mandante);
					EquipeDTO visitante = new EquipeDTO(rs.getLong("p.id_equipe_visitante"), rs.getString("eqVi.nome"), rs.getString("eqVi.sigla_equipe"), rs.getString("eqVi.url_imagem_equipe"));
					partida.setVisitante(visitante);

					partida.setUrlImagemLogoLiga(rs.getString("urlImagemLogoLiga"));
					
					return partida;
				}
				);
	}

	public List<PartidaDTO> listarHistoricoPartidasEquipePaginado(Long idLiga, Long idEquipe, Integer inicio) {
		return jdbcTemplate.query("select p.id_partida, p.id_liga, p.id_equipe_casa, p.id_equipe_visitante, p.placar_equipe_casa, p.placar_equipe_visitante, "
				+ "p.fase, p.tag_grupo, p.local_jogo, p.placar_extendido_equipe_casa, p.placar_extendido_equipe_visitante, p.data_hora_jogo, p.tempo_partida, "
				+ "l.url_imagem, p.gols_equipe_casa, p.gols_equipe_visitante, "
				+ "eqLo.nome, eqLo.url_imagem_equipe, eqVi.nome, eqVi.url_imagem_equipe, eqLo.sigla_equipe, eqVi.sigla_equipe, "
				+ "CASE "
				+ "when p.id_equipe_casa = ? and p.placar_equipe_casa > p.placar_equipe_visitante then 'Vitória' "
				+ "when p.id_equipe_casa = ? and p.placar_equipe_casa < p.placar_equipe_visitante then 'Derrota' "
				+ "when p.placar_equipe_casa = p.placar_equipe_visitante then 'Empate' "
				+ "when p.id_equipe_visitante = ? and p.placar_equipe_casa < p.placar_equipe_visitante then 'Vitória' "
				+ "when p.id_equipe_visitante = ? and p.placar_equipe_casa > p.placar_equipe_visitante then 'Derrota' "
				+ "END as statusPartida "
				+ "from partida as p, tb_equipe as eqLo, tb_equipe as eqVi, liga as l "
				+ "where p.id_equipe_casa = eqLo.id_equipe "
				+ "and p.id_equipe_visitante = eqVi.id_equipe "
				+ "and p.id_liga = ? "
				+ "and p.id_liga = l.idLiga "
				+ "and (p.id_equipe_casa = ? or p.id_equipe_visitante = ?) "
				+ "and p.data_hora_jogo < sysdate() "
				+ "order by p.data_hora_jogo desc "
				+ "limit ?, " + Constantes.LIMITE_CONSULTA_BANCO,
				new Object[]{ idEquipe, idEquipe, idEquipe, idEquipe, idLiga, idEquipe, idEquipe, inicio }, 
				(rs, rowNum) -> {
					PartidaDTO partida = new PartidaDTO();

					partida.setIdPartida(rs.getLong("p.id_partida"));
					partida.setIdLiga(rs.getLong("p.id_liga"));
					partida.setPlacarEquipeCasa(rs.getInt("p.placar_equipe_casa"));
					partida.setPlacarEquipeVisitante(rs.getInt("p.placar_equipe_visitante"));
					partida.setFase(rs.getString("p.fase"));
					partida.setTagGrupo(rs.getString("p.tag_grupo"));
					partida.setLocalJogo(rs.getString("p.local_jogo"));
					partida.setPlacarExtendidoEquipeCasa(rs.getString("p.placar_extendido_equipe_casa"));
					partida.setPlacarExtendidoEquipeVisitante(rs.getString("p.placar_extendido_equipe_visitante"));
					partida.setDataHoraJogo(rs.getTimestamp("p.data_hora_jogo"));
					partida.setDataHoraJogoTexto(UtilData.formatarDataString(rs.getTimestamp("p.data_hora_jogo")));
					partida.setTempoPartida(rs.getString("p.tempo_partida"));
					partida.setStatusPartida(rs.getString("statusPartida"));
					partida.setGolsEquipeCasa(rs.getString("p.gols_equipe_casa"));
					partida.setGolsEquipeVisitante(rs.getString("p.gols_equipe_visitante"));
					partida.setUrlImagemLogoLiga(rs.getString("l.url_imagem"));

					EquipeDTO mandante = new EquipeDTO(rs.getLong("p.id_equipe_casa"), rs.getString("eqLo.nome"), rs.getString("eqLo.sigla_equipe"), rs.getString("eqLo.url_imagem_equipe"));
					partida.setMandante(mandante);
					EquipeDTO visitante = new EquipeDTO(rs.getLong("p.id_equipe_visitante"), rs.getString("eqVi.nome"), rs.getString("eqVi.sigla_equipe"), rs.getString("eqVi.url_imagem_equipe"));
					partida.setVisitante(visitante);
					
					return partida;
				}
				);
	}

	public PartidaDTO buscarPartidaPorId(Long idPartida) {
		try {
			return jdbcTemplate.queryForObject("select p.id_partida, p.id_liga, p.placar_equipe_casa, p.placar_equipe_visitante, "
					+ "p.fase, p.tag_grupo, p.local_jogo, p.placar_extendido_equipe_casa, p.placar_extendido_equipe_visitante, p.data_hora_jogo, p.data_hora_jogo, "
					+ "p.tempo_partida, p.gols_equipe_casa, p.gols_equipe_visitante, l.url_imagem, "
					+ "p.id_equipe_casa, eqLo.nome, eqLo.sigla_equipe, eqLo.url_imagem_equipe, "
					+ "p.id_equipe_visitante, eqVi.nome, eqVi.sigla_equipe, eqVi.url_imagem_equipe "
					+ "from partida as p "
					+ "inner join equipe as eqLo on p.id_equipe_casa = eqLo.id_equipe "
					+ "inner join equipe as eqVi on p.id_equipe_visitante = eqVi.id_equipe "
					+ "inner join liga as l on p.id_liga = l.idLiga "
					+ "where  p.id_partida = ? ",
					new Object[]{ idPartida }, 
					(rs, rowNum) -> {
						PartidaDTO partida = new PartidaDTO();

						partida.setIdPartida(rs.getLong("p.id_partida"));
						partida.setIdLiga(rs.getLong("p.id_liga"));
						partida.setPlacarEquipeCasa(rs.getInt("p.placar_equipe_casa"));
						partida.setPlacarEquipeVisitante(rs.getInt("p.placar_equipe_visitante"));
						partida.setFase(rs.getString("p.fase"));
						partida.setTagGrupo(rs.getString("p.tag_grupo"));
						partida.setLocalJogo(rs.getString("p.local_jogo"));
						partida.setPlacarExtendidoEquipeCasa(rs.getString("p.placar_extendido_equipe_casa"));
						partida.setPlacarExtendidoEquipeVisitante(rs.getString("p.placar_extendido_equipe_visitante"));
						partida.setDataHoraJogo(rs.getTimestamp("p.data_hora_jogo"));
						partida.setDataHoraJogoTexto(UtilData.formatarDataString(rs.getTimestamp("p.data_hora_jogo")));
						partida.setTempoPartida(rs.getString("p.tempo_partida"));
						partida.setGolsEquipeCasa(rs.getString("p.gols_equipe_casa_equipe_casa"));
						partida.setGolsEquipeVisitante(rs.getString("p.gols_equipe_visitante"));
						partida.setUrlImagemLogoLiga(rs.getString("l.url_imagem"));

						EquipeDTO mandante = new EquipeDTO(rs.getLong("p.id_equipe_casa"), rs.getString("eqLo.nome"), rs.getString("eqLo.sigla_equipe"), rs.getString("eqLo.url_imagem_equipe"));
						partida.setMandante(mandante);
						EquipeDTO visitante = new EquipeDTO(rs.getLong("p.id_equipe_visitante"), rs.getString("eqVi.nome"), rs.getString("eqVi.sigla_equipe"), rs.getString("eqVi.url_imagem_equipe"));
						partida.setVisitante(visitante);
						
						return partida;
					});
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public void alterarTempoPartida(Long idPartida, String mensagemTempoJogo) {
		jdbcTemplate.update("update tb_partida "
				+ "set tempo_partida=? "
				+ "where id_partida=?", 
				new Object[]{
						mensagemTempoJogo,
						idPartida
				});
	}

	public void alterarPlacarPartida(Partida partida) {
		jdbcTemplate.update("update tb_partida "
				+ "set placar_equipe_casa=?, placar_equipe_visitante=? "
				+ "where id_partida=? ", 
				new Object[]{
						partida.getPlacarEquipeCasa(),
						partida.getPlacarEquipeVisitante(),
						partida.getIdPartida()
				});
	}

	public void alterarGolsPlacar(Partida partida) {
		jdbcTemplate.update("update tb_partida "
				+ "set placar_equipe_casa=?, placar_equipe_visitante=? "
				+ "where id_partida=? ", 
				new Object[]{
						partida.getGolsEquipeCasa(),
						partida.getGolsEquipeVisitante(),
						partida.getIdPartida()
				});
	}
	
	public void alterarPartidaPlacarExtendido(Partida partida) {
		jdbcTemplate.update("update tb_partida "
				+ "set placar_extendido_equipe_casa=?, placar_extendido_equipe_visitante=? "
				+ "where id_partida=?", 
				new Object[]{
						partida.getPlacarExtendidoEquipeCasa(),
						partida.getPlacarExtendidoEquipeVisitante(),
						partida.getIdPartida()
				});
	}
}
