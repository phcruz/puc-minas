package br.com.phc.pitaco.utilities.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ArtilheiroDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nome;
	private String gols;
	private String posicao;
	private String time;
	private String urlJogador;
	private String urlEscudoTime;
	private String ranking;

}
