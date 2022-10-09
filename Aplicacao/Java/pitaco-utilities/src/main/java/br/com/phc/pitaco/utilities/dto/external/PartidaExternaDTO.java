package br.com.phc.pitaco.utilities.dto.external;

import java.io.Serializable;

import lombok.Data;

@Data
public class PartidaExternaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String url;
	private String nomeLiga;
	private String fase;
	private String tag;
}
