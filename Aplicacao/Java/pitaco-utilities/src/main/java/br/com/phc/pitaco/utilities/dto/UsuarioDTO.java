package br.com.phc.pitaco.utilities.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nome;
	private String email;
	private String transaction;
}
