package br.com.phc.pitaco.utilities.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PontuadorRodadaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idUsuario;
	private String nome;
	private String urlImagem;
	private Integer pontos;

}
