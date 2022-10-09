package br.com.phc.pitaco.utilities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.phc.pitaco.utilities.model.Acesso;

@Repository
public interface AcessoRepository extends JpaRepository<Acesso, Long> {

	public Acesso findByPlataformaAndUsuarioIdUsuario(String plataforma, Long idUsuario);
}
