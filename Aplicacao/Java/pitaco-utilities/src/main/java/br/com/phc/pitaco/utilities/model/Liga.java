package br.com.phc.pitaco.utilities.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "tb_liga")
public class Liga implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_liga")
	private Long idLiga;

	@Column(name = "nome_liga")
	private String nomeLiga;

	@Column(name = "pais_liga")
	private String paisLiga;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "temporada")
	private String temporada;

	@Column(name = "url_imagem")
	private String urlImagem;

	@Column(name = "paga")
	private boolean paga;

	@Column(name = "valor")
	private float valor;

	@Column(name = "ativa")
	private boolean ativa;

	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "data_inicio")
	private Date dataInicio;

	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "data_fim")
	private Date dataFim;

	public Liga(Long idLiga) {
		super();
		this.idLiga = idLiga;
	}

}
