package br.com.phc.pitaco.utilities.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.phc.pitaco.utilities.model.Equipe;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {

	public Optional<Equipe> findByIdExternoEquipe(Integer idExternoEquipe);
}
