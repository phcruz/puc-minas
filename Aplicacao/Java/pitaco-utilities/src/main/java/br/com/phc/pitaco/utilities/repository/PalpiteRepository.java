package br.com.phc.pitaco.utilities.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.phc.pitaco.utilities.dto.PalpiteDTO;
import br.com.phc.pitaco.utilities.model.Palpite;

@Repository
public interface PalpiteRepository extends JpaRepository<Palpite, Long> {

	@Modifying
	@Query(value = "update Palpite p "
			+ "set p.pontos=:#{#palpite.pontos} "
			+ "where p.idPalpite=:#{#palpite.idPalpite} ")
	public void alterarPontosPalpite(@Param("palpite") Palpite palpite);

	public List<Palpite> findByPartidaIdPartida(Long idPartida);

	@Query("select p from Palpite p where p.partida.id=:#{#palpite.idPartida} "
			+ "and p.liga.id=:#{#palpite.idLiga} and p.usuario.id=:#{#palpite.idUsuario} ")
	public Palpite buscarPalpiteUsuarioPartida(@Param("palpite") PalpiteDTO palpiteDTO);

	@Query("select p from Palpite p where p.partida.idPartida=:idPartida and p.usuario.idUsuario=:idUsuario ")
	public Palpite buscarPalpiteIdPartidaIdUsuario(@Param("idPartida") Long idPartida, @Param("idUsuario") Long idUsuario);
}
