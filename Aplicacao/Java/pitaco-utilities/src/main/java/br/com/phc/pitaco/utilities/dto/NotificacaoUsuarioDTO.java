package br.com.phc.pitaco.utilities.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class NotificacaoUsuarioDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idNotificacaoUsuario;
	private Long idNotificacao;
	private Long idUsuario;

}
