package br.com.phc.pitaco.utilities.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartidaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idPartida;
	private Long idLiga;
	private Integer placarEquipeCasa;
	private Integer placarEquipeVisitante;
	private String fase;
	private String tagGrupo;
	private String localJogo;
	private String placarExtendidoEquipeCasa;
	private String placarExtendidoEquipeVisitante;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", locale = "pt-BR", timezone = "America/Sao_Paulo")
	private Date dataHoraJogo;
	private String dataHoraJogoTexto;
	private boolean permiteAposta;
	private boolean mostraPontuacao;
	// adicionais
	private PalpiteDTO palpite;
	private EquipeDTO mandante;
	private EquipeDTO visitante;
	private String urlImagemLogoLiga;

	private String tempoPartida;
	private String statusPartida;
	private String golsEquipeCasa;
	private String golsEquipeVisitante;
}
