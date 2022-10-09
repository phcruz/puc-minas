package br.com.phc.pitaco.utilities.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class LigaUsuarioDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idLiga;
	private Long idUsuario;

}
