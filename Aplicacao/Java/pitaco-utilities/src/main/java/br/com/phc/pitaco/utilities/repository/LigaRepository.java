package br.com.phc.pitaco.utilities.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.phc.pitaco.utilities.Constantes;
import br.com.phc.pitaco.utilities.model.Liga;

@Repository
public interface LigaRepository extends JpaRepository<Liga, Long> {

	public Liga findByIdLiga(Long idLiga);

	public List<Liga> findByAtivaTrue();

	public Optional<Liga> findByNomeLigaAndAtivaTrue(String name);

	public void deleteByIdLiga(Long idLiga);
	
	@Query(value = "select * from tb_liga as l "
			+ "where l.ativa = 1 "
			+ "and l.id_liga in "
			+ " ( select lu.id_liga from tb_liga_usuario as lu where lu.id_usuario = :idUsuario and lu.ativo=1 ) "
			+ "order by data_fim desc ", nativeQuery = true)
	public List<Liga> listarLigasUsuarioId(Long idUsuario);

	@Query(value = "select * from tb_liga as l "
				+ "where l.ativa = 1 "
				+ "and l.data_fim > sysdate() "
				+ "and l.id_liga in "
				+ "( select lu.id_liga from tb_liga_usuario as lu where lu.id_usuario = :idUsuario and lu.ativo = 1 ) ", nativeQuery = true)
	public List<Liga> listarLigasAtivasUsuario(@Param("idUsuario") Long idUsuario);

	@Query(value = "select * from tb_liga as l "
			+ "where l.ativa = 1 "
			+ "and l.data_fim > sysdate() "
			+ "and l.id_liga not in "
			+ " ( select lu.id_liga from tb_liga_usuario as lu where lu.id_usuario = :idUsuario and lu.ativo=1 ) ", nativeQuery = true)
	public List<Liga> listarLigasAtivasParaImportar(@Param("idUsuario") Long idUsuario);
	
	@Query(value = "select * from tb_liga as l "
			+ "where l.ativa = 1 "
			+ "and l.id_liga not in "
			+ " ( select lu.id_liga from tb_liga_usuario as lu where lu.id_usuario = :idUsuario and lu.ativo=1 ) ", nativeQuery = true)
	public List<Liga> listarLigasParaImportar(@Param("idUsuario") Long idUsuario);

	@Query(value = "select * from tb_liga as l "
			+ "where l.ativa = 1 "
			+ "and l.id_liga in "
			+ " ( select lu.id_liga from tb_liga_usuario as lu where lu.id_usuario = :idUsuario and lu.ativo=1 ) "
			+ "order by data_fim desc "
			+ "limit :inicio, " + Constantes.LIMITE_CONSULTA_BANCO, nativeQuery = true)
	public List<Liga> listarLigasUsuarioIdPaginado(@Param("idUsuario") Long idUsuario, @Param("inicio") Integer inicio);

	@Query(value = "select * from tb_liga as l "
			+ "where l.ativa = 1 "
			+ "and l.id_liga not in "
			+ " ( select lu.id_liga from tb_liga_usuario as lu where lu.id_usuario = :idUsuario and lu.ativo=1 ) "
			+ "limit :inicio, " + Constantes.LIMITE_CONSULTA_BANCO, nativeQuery = true)
	public List<Liga> listarLigasParaImportarPaginado(@Param("idUsuario") Long idUsuario, @Param("inicio") Integer inicio);

	public List<Liga> findAllByAtivaTrue();
}
