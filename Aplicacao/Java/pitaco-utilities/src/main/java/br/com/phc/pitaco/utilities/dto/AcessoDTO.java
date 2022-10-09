package br.com.phc.pitaco.utilities.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AcessoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String plataforma;
	private Long idUsuario;
	private AcessoDadosDTO dados;
}
