package br.com.phc.pitaco.utilities.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PalpiteDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idPalpite;
	private Long idPartida;
	private Long idUsuario;
	private Long idLiga;
	private Integer placarEquipeCasa;
	private Integer placarEquipeVisitante;
	private Integer pontos;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", locale = "pt-BR", timezone = "America/Sao_Paulo")
	private Date dataCadastro;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", locale = "pt-BR", timezone = "America/Sao_Paulo")
	private Date dataAlteracao;
}
