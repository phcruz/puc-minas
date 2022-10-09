package br.com.phc.pitaco.utilities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.phc.pitaco.utilities.model.CompraLiga;

@Repository
public interface CompraLigaRepository extends JpaRepository<CompraLiga, Long> {

}
