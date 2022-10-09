package br.com.phc.pitaco.utilities.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ClassificacaoGeralDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer posicao;
	private Long idEquipe;
	private String urlImagemEquipe;
	private String nomeEquipe;
	private Integer quantidadePontos;
	private Integer quantidadeJogos;
	private Integer quantidadeVitorias;
	private Integer quantidadeEmpates;
	private Integer quantidadeDerrotas;
	private Integer saldoGols;

	public ClassificacaoGeralDTO() {
	}

	public ClassificacaoGeralDTO(Integer posicao, Long idEquipe, String urlImagemEquipe, String nomeEquipe,
			Integer quantidadePontos, Integer quantidadeJogos, Integer quantidadeVitorias, Integer quantidadeEmpates,
			Integer quantidadeDerrotas, Integer saldoGols) {
		super();
		this.posicao = posicao;
		this.idEquipe = idEquipe;
		this.urlImagemEquipe = urlImagemEquipe;
		this.nomeEquipe = nomeEquipe;
		this.quantidadePontos = quantidadePontos;
		this.quantidadeJogos = quantidadeJogos;
		this.quantidadeVitorias = quantidadeVitorias;
		this.quantidadeEmpates = quantidadeEmpates;
		this.quantidadeDerrotas = quantidadeDerrotas;
		this.saldoGols = saldoGols;
	}

}
