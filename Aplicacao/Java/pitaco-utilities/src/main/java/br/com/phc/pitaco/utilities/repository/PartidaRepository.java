package br.com.phc.pitaco.utilities.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.phc.pitaco.utilities.model.Partida;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {

	public Partida findByIdPartida(Long idPartida);

	public Optional<Partida> findByIdExternoPartida(Integer idExternoPartida);

	@Query(value = "select count(*) from tb_partida as p "
			+ "where p.data_hora_jogo between date_sub(sysdate(), INTERVAL '3' DAY_HOUR) and sysdate() "
			+ "and ifnull(p.tempo_partida, 'Vazio') != 'Encerrado' ", nativeQuery = true)
	public Integer buscarPartidasRaspagemHoje();

	@Modifying
	@Query(value = "update Partida p "
			+ "set p.placarEquipeCasa=:#{#partida.placarEquipeCasa}, p.placarEquipeVisitante=:#{#partida.placarEquipeVisitante} "
			+ "where p.idPartida=:#{#partida.idPartida} ")
	public void alterarPartidaPlacar(@Param("partida") Partida partida);

	@Modifying
	@Query(value = "update Partida p set p.tempoPartida=:tempoPartida where p.idPartida=:idPartida")
	public void alterarTempoPartida(@Param("tempoPartida") String tempoPartida, @Param("idPartida") Long idPartida);

	@Modifying
	@Query(value = "update Partida p "
			+ "set p.placarExtendidoEquipeCasa=:#{#partida.placarExtendidoEquipeCasa}, p.placarExtendidoEquipeVisitante=:#{#partida.placarExtendidoEquipeVisitante} "
			+ "where p.idPartida=:#{#partida.idPartida} ")
	public void alterarPartidaPlacarExtendido(@Param("partida") Partida partida);

	@Modifying
	@Query(value = "update Partida p "
			+ "set p.placarEquipeCasa=:#{#partida.golsEquipeCasa}, p.placarEquipeVisitante=:#{#partida.golsEquipeVisitante} "
			+ "where p.idPartida=:#{#partida.idPartida} ")
	public void alterarGolsPlacar(@Param("partida") Partida partida);

	@Query(value = "select * from tb_partida as p "
			+ "where p.data_hora_jogo between date_sub(sysdate(), INTERVAL '3' DAY_HOUR) and sysdate() "
			+ "and ifnull(p.tempo_partida, 'Vazio') != 'Encerrado' "
			+ "and ifnull(p.local_jogo, 'Vazio') != 'JOGO ADIADO' ", nativeQuery = true)
	public List<Partida> listarTodasPartidasRaspagemHoje();

	@Query(value = "select count(*) as total from tb_partida "
			+ "where date_format(data_hora_jogo, '%d/%m/%Y') = date_format(date_sub(sysdate(), INTERVAL 1 day), '%d/%m/%Y') ", nativeQuery = true)
	public Integer buscarTotalPartidasDiaAnterior();
}
