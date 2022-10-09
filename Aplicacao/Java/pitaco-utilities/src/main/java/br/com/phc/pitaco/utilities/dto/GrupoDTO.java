package br.com.phc.pitaco.utilities.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GrupoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idGrupo;
	private Long idLiga;
	private Long idUsuario;
	private String nomeUsuarioAdmin;
	private String nomeGrupo;
	private String nomeLiga;
	private String descricaoGrupo;
	private String descricaoPremioGrupo;
	private String temporada;
	private String urlImagem;
	private float mediaPontos;
	private Integer quantidadeMembros;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", locale = "pt-BR", timezone = "America/Sao_Paulo")
	private Date dataCriacao;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", locale = "pt-BR", timezone = "America/Sao_Paulo")
	private Date dataAlteracao;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", locale = "pt-BR", timezone = "America/Sao_Paulo")
	private Date dataFim;
	private boolean fechado;
	private boolean pago;
	private float valor;

}
