package br.com.phc.pitaco.utilities.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_plano_fundo")
public class PlanoFundo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_plano_fundo")
	private Long idPlanoFundo;

	@Column(name = "nome")
	private String nome;

	@Column(name = "url_imagem")
	private String urlImagem;

}
