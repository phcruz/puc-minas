package br.com.phc.pitaco.utilities.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Credencial implements Serializable {

	private static final long serialVersionUID = 1L;

	private String email;
	private String senha;
}
