package br.com.phc.pitaco.utilities.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NotificacaoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idNotificacao;
	private String titulo;
	private String mensagem;
	private Long idUsuario;
	private Integer toqueNotificacao;
}
