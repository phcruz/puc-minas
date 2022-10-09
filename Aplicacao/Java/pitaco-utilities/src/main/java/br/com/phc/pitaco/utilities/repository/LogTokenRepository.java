package br.com.phc.pitaco.utilities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.phc.pitaco.utilities.model.LogToken;

@Repository
public interface LogTokenRepository extends JpaRepository<LogToken, Long> {

	public LogToken findByEmail(String email);

}
