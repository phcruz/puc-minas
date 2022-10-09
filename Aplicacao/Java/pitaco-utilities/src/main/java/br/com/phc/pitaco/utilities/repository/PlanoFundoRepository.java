package br.com.phc.pitaco.utilities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.phc.pitaco.utilities.model.PlanoFundo;

@Repository
public interface PlanoFundoRepository extends JpaRepository<PlanoFundo, Long> {

	@Query(value = "SELECT * FROM tb_plano_fundo ORDER BY RAND() LIMIT 1", nativeQuery = true)
	public PlanoFundo findRandomImage();

	public PlanoFundo findByIdPlanoFundo(Long id);

	public void deleteByIdPlanoFundo(Long id);
}
