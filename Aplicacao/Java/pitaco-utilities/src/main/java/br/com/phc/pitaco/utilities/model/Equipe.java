package br.com.phc.pitaco.utilities.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.phc.pitaco.utilities.JsonDateDeserializer;
import br.com.phc.pitaco.utilities.JsonDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "tb_equipe")
public class Equipe implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_equipe")
	private Long idEquipe;

	@Column(name = "nome")
	private String nome;

	@Column(name = "sigla_equipe")
	private String siglaEquipe;

	@Column(name = "pais")
	private String pais;

	@Column(name = "sigla_pais")
	private String siglaPais;

	@Column(name = "url_imagem_equipe")
	private String urlImagemEquipe;

	@Column(name = "url_imagem_pais")
	private String urlImagemPais;

	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "data_cadastro")
	private Date dataCadastro;

	@Column(name = "id_externo_equipe")
	private Integer idExternoEquipe;

	@Transient
	private String nomeCompleto;
	@Transient
	private String nomeEquipeApi;

	public Equipe(Long idEquipe) {
		super();
		this.idEquipe = idEquipe;
	}

	public Equipe(Long idEquipe, String nome, String siglaEquipe, String urlImagemEquipe) {
		super();
		this.idEquipe = idEquipe;
		this.nome = nome;
		this.siglaEquipe = siglaEquipe;
		this.urlImagemEquipe = urlImagemEquipe;
	}

}
