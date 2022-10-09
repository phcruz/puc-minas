package br.com.phc.pitaco.utilities.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EstatisticaPartidaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer vitoriaCasa;
	private Integer vitoriaVisitante;
	private Integer empate;
	private Integer totalPalpites;

	private List<EstatisticaPlacarDTO> listaEstatisticaPlacar;

}
