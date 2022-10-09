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
@Table(name = "tb_acesso_dados")
public class AcessoDados implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_acesso_dados")
	private Long idAcessoDados;

	@ManyToOne
	@JoinColumn(name = "id_acesso")
	private Acesso acesso;

	@Column(name = "sistema")
	private String sistema;

	@Column(name = "dispositivo")
	private String dispositivo;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "detalhe")
	private String detalhe;

	@Column(name = "user_ip")
	private String userIp;
	
	@Column(name = "user_agent")
	private String userAgent;
	
	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "data_acesso")
	private Date dataAcesso;
}
