package br.com.phc.pitaco.utilities.dto.external;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SedeDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("nome_popular")
	private String nome;

}
