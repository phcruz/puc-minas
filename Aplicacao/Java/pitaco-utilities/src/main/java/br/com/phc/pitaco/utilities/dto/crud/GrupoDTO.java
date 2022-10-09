package br.com.phc.pitaco.utilities.dto.crud;

import java.io.Serializable;

import lombok.Data;

@Data
public class GrupoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nomeGrupo;
	private String descricaoGrupo;
	private String descricaoPremioGrupo;
	private String urlImagem;
	private boolean fechado;
}
