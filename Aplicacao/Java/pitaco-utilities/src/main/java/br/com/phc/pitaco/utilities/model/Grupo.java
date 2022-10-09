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
@Table(name = "tb_liga_usuario")
public class Grupo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_liga_usuario")
	private Long idGrupo;

	@ManyToOne
	@JoinColumn(name = "id_liga")
	private Liga liga;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	// @Column(name = "ativo")
	private String nomeUsuarioAdmin;

	@Column(name = "nome_grupo")
	private String nomeGrupo;

	// @Column(name = "ativo")
	private String nomeLiga;

	@Column(name = "descricao_grupo")
	private String descricaoGrupo;

	@Column(name = "descricao_premio_grupo")
	private String descricaoPremioGrupo;

	// @Column(name = "ativo")
	private String temporada;

	@Column(name = "url_imagem")
	private String urlImagem;

	// @Column(name = "ativo")
	private float mediaPontos;

	// @Column(name = "ativo")
	private Integer quantidadeMembros;

	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "data_criacao")
	private Date dataCriacao;

	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "data_alteracao")
	private Date dataAlteracao;

	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	// @Column(name = "valor")
	private Date dataFim;

	@Column(name = "fechado")
	private boolean fechado;

	@Column(name = "pago")
	private boolean pago;

	@Column(name = "valor")
	private float valor;

	public Grupo(Long idGrupo) {
		super();
		this.idGrupo = idGrupo;
	}

}
