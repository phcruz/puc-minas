package br.com.phc.pitaco.utilities.dto.external;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class EquipeDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("nome_popular")
	private String nome;

	@JsonProperty("sigla")
	private String sigla;

	@JsonProperty("escudo")
	private String escudo;
}
