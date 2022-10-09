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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.phc.pitaco.utilities.JsonDateDeserializer;
import br.com.phc.pitaco.utilities.JsonDateSerializer;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_palpite")
public class Palpite implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_palpite")
	private Long idPalpite;

	@ManyToOne
	@JoinColumn(name = "id_partida")
	private Partida partida;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "id_liga")
	private Liga liga;

	@Column(name = "placar_equipe_casa")
	private Integer placarEquipeCasa;

	@Column(name = "placar_equipe_visitante")
	private Integer placarEquipeVisitante;

	@Column(name = "pontos")
	private Integer pontos;

	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "data_cadastro")
	private Date dataCadastro;

	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "data_alteracao")
	private Date dataAlteracao;
}
