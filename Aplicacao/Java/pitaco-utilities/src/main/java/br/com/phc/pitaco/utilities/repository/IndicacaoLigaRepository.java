package br.com.phc.pitaco.utilities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.phc.pitaco.utilities.model.IndicacaoLiga;

@Repository
public interface IndicacaoLigaRepository extends JpaRepository<IndicacaoLiga, Long> {

}
