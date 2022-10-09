package br.com.phc.pitaco.utilities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.phc.pitaco.utilities.model.GrupoUsuario;

@Repository
public interface GrupoUsuarioRepository extends JpaRepository<GrupoUsuario, Long> {

	@Query(value = "select gu from GrupoUsuario gu "
			+ "where gu.grupo=:#{#grupoUsuario.idPartida} and gu.liga=:#{#grupoUsuario.liga} and gu.usuario=:#{#grupoUsuario.usuario} ")
	public GrupoUsuario buscarGrupoUsuario(@Param("grupoUsuario") GrupoUsuario grupoUsuario);

}
