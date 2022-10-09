package br.com.phc.pitaco.utilities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.phc.pitaco.utilities.model.NotificacaoUsuario;
import br.com.phc.pitaco.utilities.model.Usuario;

@Repository
public interface NotificacaoUsuarioRepository extends JpaRepository<NotificacaoUsuario, Long> {

	public NotificacaoUsuario findOneByUsuarioIdUsuario(Long idUsuario);

	public NotificacaoUsuario findOneByUsuario(Usuario usuario);
}
