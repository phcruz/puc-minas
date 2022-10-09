package br.com.phc.pitaco.utilities.dto.crud;

import java.io.Serializable;

import lombok.Data;

@Data
public class AvatarDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String descricao;
	private String urlImagem;
	private boolean ativo;
}
