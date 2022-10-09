package br.com.phc.pitaco.utilities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.phc.pitaco.utilities.model.SolicitacaoGrupoUsuario;

@Repository
public interface SolicitacaoGrupoUsuarioRepository extends JpaRepository<SolicitacaoGrupoUsuario, Long> {

	@Modifying
	@Query(value = "update SolicitacaoGrupoUsuario sgu "
			+ "set sgu.dataResposta=:#{#solicitacao.dataResposta}, sgu.permiteParticipar=:#{#solicitacao.permiteParticipar}, sgu.ativo=:#{#solicitacao.ativo} "
			+ "where sgu.idSolicitacaoGrupoUsuario=:#{#solicitacao.idSolicitacaoGrupoUsuario} ")
	public void alterarSolicitacaoGrupoUsuario(@Param("solicitacao") SolicitacaoGrupoUsuario solicitacao);

}
