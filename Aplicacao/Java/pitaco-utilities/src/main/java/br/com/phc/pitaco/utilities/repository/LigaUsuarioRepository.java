package br.com.phc.pitaco.utilities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.phc.pitaco.utilities.dto.LigaUsuarioDTO;
import br.com.phc.pitaco.utilities.model.Liga;
import br.com.phc.pitaco.utilities.model.LigaUsuario;
import br.com.phc.pitaco.utilities.model.Usuario;

@Repository
public interface LigaUsuarioRepository extends JpaRepository<LigaUsuario, Long> {

	public LigaUsuario findByLigaAndUsuario(Liga liga, Usuario usuario);

	@Query(value = "select lu from LigaUsuario lu where lu.liga=:#{#ligaUsuario.liga} and lu.usuario=:#{#ligaUsuario.usuario} ")
	public LigaUsuario buscarLigaUsuario(@Param("ligaUsuario") LigaUsuario ligaUsuario);

	@Query(value = "select lu from LigaUsuario lu where lu.liga.id=:#{#ligaUsuario.idLiga} and lu.usuario.id=:#{#ligaUsuario.idUsuario} ")
	public LigaUsuario buscarLigaUsuario(@Param("ligaUsuario") LigaUsuarioDTO ligaUsuarioDTO);
}
