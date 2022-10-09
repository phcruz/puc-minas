package br.com.phc.pitaco.utilities.dto.crud;

import java.io.Serializable;

import lombok.Data;

@Data
public class PlanoFundoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nome;
	private String urlImagem;
}
