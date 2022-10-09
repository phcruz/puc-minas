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
public class EquipeDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idEquipe;
	private String nome;
	private String siglaEquipe;
	private String nomeCompleto;
	private String nomeEquipeApi;
	private String urlImagemEquipe;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", locale = "pt-BR", timezone = "America/Sao_Paulo")
	private Date dataCadastro;

	public EquipeDTO(Long idEquipe) {
		super();
		this.idEquipe = idEquipe;
	}

	public EquipeDTO(Long idEquipe, String nome, String siglaEquipe, String urlImagemEquipe) {
		super();
		this.idEquipe = idEquipe;
		this.nome = nome;
		this.siglaEquipe = siglaEquipe;
		this.urlImagemEquipe = urlImagemEquipe;
	}

	public EquipeDTO(String nome, String siglaEquipe, String urlImagemEquipe) {
		super();
		this.nome = nome;
		this.siglaEquipe = siglaEquipe;
		this.urlImagemEquipe = urlImagemEquipe;
	}
}
