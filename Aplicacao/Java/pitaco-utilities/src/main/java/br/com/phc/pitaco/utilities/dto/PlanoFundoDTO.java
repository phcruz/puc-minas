package br.com.phc.pitaco.utilities.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PlanoFundoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nome;
	private String urlImagem;
}
