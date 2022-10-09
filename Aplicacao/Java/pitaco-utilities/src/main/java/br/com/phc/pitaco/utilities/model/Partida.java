package br.com.phc.pitaco.utilities.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.phc.pitaco.utilities.JsonDateDeserializer;
import br.com.phc.pitaco.utilities.JsonDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tb_partida")
public class Partida implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_partida")
	private Long idPartida;

	@ManyToOne
	@JoinColumn(name = "id_liga")
	private Liga liga;

	@ManyToOne
	@JoinColumn(name = "id_equipe_casa")
	private Equipe mandante;

	@ManyToOne
	@JoinColumn(name = "id_equipe_visitante")
	private Equipe visitante;

	@Column(name = "placar_equipe_casa")
	private Integer placarEquipeCasa;

	@Column(name = "placar_equipe_visitante")
	private Integer placarEquipeVisitante;

	@Column(name = "fase")
	private String fase;

	@Column(name = "tag_grupo")
	private String tagGrupo;

	@Column(name = "local_jogo")
	private String localJogo;

	@Column(name = "placar_extendido_equipe_casa")
	private String placarExtendidoEquipeCasa;

	@Column(name = "placar_extendido_equipe_visitante")
	private String placarExtendidoEquipeVisitante;

	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "data_hora_jogo")
	private Date dataHoraJogo;

	@Column(name = "tempo_partida")
	private String tempoPartida;

	@Column(name = "id_externo_partida")
	private Integer idExternoPartida;

	@Transient
	private String dataHoraJogoTexto;
	@Transient
	private boolean permiteAposta;
	@Transient
	private boolean mostraPontuacao;
	@Transient
	private String urlImagemLogoLiga;
	@Transient
	private String statusPartida;
	@Transient
	private String golsEquipeCasa;
	@Transient
	private String golsEquipeVisitante;

	// adicionais
	// private Palpite palpite;

	public Partida(Long idPartida) {
		super();
		this.idPartida = idPartida;
	}
}
