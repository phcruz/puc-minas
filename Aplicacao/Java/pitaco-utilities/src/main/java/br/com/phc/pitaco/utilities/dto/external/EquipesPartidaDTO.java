package br.com.phc.pitaco.utilities.dto.external;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class EquipesPartidaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("mandante")
	private EquipeDTO mandante;

	@JsonProperty("visitante")
	private EquipeDTO visitante;
}
