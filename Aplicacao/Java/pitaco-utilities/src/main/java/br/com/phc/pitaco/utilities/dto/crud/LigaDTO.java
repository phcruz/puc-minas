package br.com.phc.pitaco.utilities.dto.crud;

import java.io.Serializable;

import lombok.Data;

@Data
public class LigaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nomeLiga;
	private String paisLiga;
	private String descricao;
	private String temporada;
	private String urlImagem;
	private boolean ativa;
}
