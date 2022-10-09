package br.com.phc.pitaco.utilities.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AcessoDadosDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String descricao;
	private String detalhe;
	private String dispositivo;
	private String sistema;
}
