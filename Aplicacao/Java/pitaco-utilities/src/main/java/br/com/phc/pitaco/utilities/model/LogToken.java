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
@Table(name = "tb_log_token")
public class LogToken implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_log_token")
	private Long idLogToken;

	@Column(name = "token")
	private String token;

	@Column(name = "email")
	private String email;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "data_criacao")
	private Date dataCriacao;

	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "data_expiracao")
	private Date dataExpiracao;
}
