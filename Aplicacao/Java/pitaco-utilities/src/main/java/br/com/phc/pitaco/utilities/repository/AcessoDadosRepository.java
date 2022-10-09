package br.com.phc.pitaco.utilities.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.phc.pitaco.utilities.model.Acesso;
import br.com.phc.pitaco.utilities.model.AcessoDados;

@Repository
public interface AcessoDadosRepository extends JpaRepository<AcessoDados, Long> {

	public List<AcessoDados> findByAcesso(Acesso acesso);
}
