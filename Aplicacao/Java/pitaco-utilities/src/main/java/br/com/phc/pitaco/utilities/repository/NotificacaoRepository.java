package br.com.phc.pitaco.utilities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.phc.pitaco.utilities.model.Notificacao;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {

	public Notificacao findTopByOrderByIdNotificacaoDesc();

	public Notificacao findByIdNotificacao(Long id);

	public void deleteByIdNotificacao(Long id);
}
