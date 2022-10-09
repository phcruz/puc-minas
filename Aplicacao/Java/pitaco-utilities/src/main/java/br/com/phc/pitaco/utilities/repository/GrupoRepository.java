package br.com.phc.pitaco.utilities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.phc.pitaco.utilities.model.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {

	public Grupo findByIdGrupo(Long idGrupo);

	public void deleteByIdGrupo(Long idGrupo);
}
