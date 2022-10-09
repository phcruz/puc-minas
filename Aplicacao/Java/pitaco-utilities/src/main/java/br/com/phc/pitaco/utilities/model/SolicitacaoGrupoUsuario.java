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
@Table(name = "tb_solicitacao_grupo_usuario")
public class SolicitacaoGrupoUsuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_solicitacao_grupo_usuario")
	private Long idSolicitacaoGrupoUsuario;

	@ManyToOne
	@JoinColumn(name = "id_grupo")
	private Grupo grupo;

	@ManyToOne
	@JoinColumn(name = "id_liga")
	private Liga liga;

	@ManyToOne
	@JoinColumn(name = "id_usuario_admin")
	private Usuario usuarioAdmin;

	@ManyToOne
	@JoinColumn(name = "id_usuario_solicitante")
	private Usuario usuarioSolicitante;

	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "data_solicitacao")
	private Date dataSolicitacao;

	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "data_resposta")
	private Date dataResposta;

	@Column(name = "permite_participar")
	private boolean permiteParticipar;

	@Column(name = "ativo")
	private boolean ativo;

	// novos
	private String urlImagemAvatarUsuario;

	private String nomeUsuario;
}
