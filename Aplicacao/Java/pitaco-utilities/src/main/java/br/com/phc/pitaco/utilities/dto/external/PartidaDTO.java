package br.com.phc.pitaco.utilities.dto.external;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.phc.pitaco.utilities.Constantes;
import lombok.Data;

@Data
public class PartidaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	private Integer id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", locale = "pt-BR", timezone = Constantes.TIME_ZONE_SP_BR)
	@JsonProperty("data_realizacao")
	private Date dataRealizacao;

	@JsonProperty("hora_realizacao")
	private String horaRealizacao;

	@JsonProperty("placar_oficial_visitante")
	private String placarVisitante;

	@JsonProperty("placar_oficial_mandante")
	private String placarMandante;

	@JsonProperty("placar_penaltis_visitante")
	private String placarPenaltisVisitante;

	@JsonProperty("placar_penaltis_mandante")
	private String placarPenaltisMandante;

	@JsonProperty("equipes")
	private EquipesPartidaDTO equipes;

	@JsonProperty("sede")
	private SedeDTO sede;

	@JsonProperty("transmissao")
	private TransmissaoDTO transmissao;
}
