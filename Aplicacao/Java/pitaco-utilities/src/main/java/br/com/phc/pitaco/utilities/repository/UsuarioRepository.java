package br.com.phc.pitaco.utilities.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.phc.pitaco.utilities.Constantes;
import br.com.phc.pitaco.utilities.model.Avatar;
import br.com.phc.pitaco.utilities.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public Usuario findByEmail(String email);
	
	public Usuario findByIdUsuario(Long idUsuario);
	
	public List<Usuario> findByAtivoFalse();

	@Modifying
	@Query("update Usuario u set u.avatar =:avatar where u.idUsuario =:idUsuario")
	public void atualizaAvatarUsuario(@Param("avatar") Avatar avatar, @Param("idUsuario") Long idUsuario);

	@Query(value = "select count(*) from tb_usuario where ativo = 0 ", nativeQuery = true)
	public Integer buscarQtdUsuarioInativo();

	@Query(value = "select u.id_usuario, u.nome, u.url_imagem from tb_usuario as u where LOWER(u.nome) like LOWER(?) "
			+ "and u.id_usuario not in "
			+ "(select gu.id_usuario from tb_grupo_usuario as gu where id_grupo = ? and ativo = 1) "
			+ "order by u.nome asc "
			+ "limit ?, " + Constantes.LIMITE_CONSULTA_BANCO, nativeQuery = true)
	public List<Usuario> listarUsuarioPorNome(String nomeUsuario, Integer inicio, Long idGrupo);
}
