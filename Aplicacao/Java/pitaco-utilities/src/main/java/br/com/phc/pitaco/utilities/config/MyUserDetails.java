package br.com.phc.pitaco.utilities.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.phc.pitaco.utilities.Constantes;
import br.com.phc.pitaco.utilities.indicador.Role;
import br.com.phc.pitaco.utilities.model.Usuario;
import br.com.phc.pitaco.utilities.repository.UsuarioRepository;

@ConditionalOnProperty(name = Constantes.ENABLE_SECURITY_DETAILS, havingValue = "true", matchIfMissing = false)
@Component
public class MyUserDetails implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String email) {
		final Usuario user = usuarioRepository.findByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException("Usuario '" + email + "' não encontrado.");
		}

		return org.springframework.security.core.userdetails.User//
				.withUsername(email)//
				.password(user.getSenha())//
				.authorities(getPerfilAcesso(user.getPerfilAcesso()))//
				.accountExpired(false)//
				.accountLocked(false)//
				.credentialsExpired(false)//
				.disabled(false)//
				.build();
	}

	private Role getPerfilAcesso(Integer value) {
		if (value == 2) {
			return Role.ROLE_ADMIN;
		}
		return Role.ROLE_USUARIO;
	}
}
