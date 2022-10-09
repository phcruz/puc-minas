package br.com.phc.pitaco.utilities.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EstatisticaPlacarDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String placarPalpite;
	private Integer totalPlacar;

}
