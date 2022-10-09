package br.com.phc.pitaco.utilities.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class EmailTimeCoracaoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nomeUsuario;
	private String email;
	private String timeCoracao;
	private String nomeEquipeCasa;
	private String nomeEquipeVisitante;
	private String dataHoraJogo;
	private String localJogo;
	private String urlImagemEquipeCasa;
	private String urlImagemEquipeVisitante;

}
