package br.com.phc.pitaco.utilities.dto.crud;

import java.io.Serializable;

import lombok.Data;

@Data
public class NotificacaoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String titulo;
	private String mensagem;
}
