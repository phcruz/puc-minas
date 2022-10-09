package br.com.phc.pitaco.utilities.indicador;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
	ROLE_USUARIO(1, "Usuario"), ROLE_ADMIN(2, "Administrador");

	public String getAuthority() {
		return name();
	}

	private Integer codigo;
	private String descricao;

	private Role(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public static Role getRoleCodigo(Integer perfil) {
		if (perfil == 2) {
			return Role.ROLE_ADMIN;
		}
		return Role.ROLE_USUARIO;
	}
}
