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
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "tb_usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Long idUsuario;

	@Column(name = "nome")
	private String nome;

	@Column(name = "email")
	private String email;

	@Column(name = "senha")
	private String senha;

	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "data_nascimento")
	private Date dataNascimento;

	@Column(name = "telefone")
	private String telefone;

	@Column(name = "local_cadastro")
	private String localCadastro;

	@Column(name = "perfil_acesso")
	private Integer perfilAcesso;

	@ManyToOne
	@JoinColumn(name = "id_avatar")
	private Avatar avatar;

	@Column(name = "ativo")
	private boolean ativo;

	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "data_cadastro")
	private Date dataCadastro;

	@Column(name = "time_coracao")
	private String timeCoracao;

	public Usuario(Long idUsuario, String nome, String email, String senha, Date dataNascimento, String telefone,
			String localCadastro, Integer perfilAcesso, Avatar avatar, boolean ativo, Date dataCadastro,
			String timeCoracao) {
		super();
		this.idUsuario = idUsuario;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.dataNascimento = dataNascimento;
		this.telefone = telefone;
		this.localCadastro = localCadastro;
		this.perfilAcesso = perfilAcesso;
		this.avatar = avatar;
		this.ativo = ativo;
		this.dataCadastro = dataCadastro;
		this.timeCoracao = timeCoracao;
	}

	public Usuario(Long idUsuario, String nome, Avatar avatar) {
		super();
		this.idUsuario = idUsuario;
		this.nome = nome;
		this.avatar = avatar;
	}

	public Usuario(Long idUsuario) {
		super();
		this.idUsuario = idUsuario;
	}

}
