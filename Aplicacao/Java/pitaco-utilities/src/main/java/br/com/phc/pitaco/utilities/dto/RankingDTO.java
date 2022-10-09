package br.com.phc.pitaco.utilities.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RankingDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer posicao;
	private Integer pontos;
	private Long idUsuario;
	private String nomeUsuario;
	private Long idLiga;
	private String nomeLiga;
	private String urlImagem;
	// adicionados para exibir criterios de desempate
	private Integer acertoCincoPontos;
	private Integer acertoTresPontos;
	private Integer acertoUmPonto;
	private Integer acertoPontuou;

	public RankingDTO(Integer posicao, Integer pontos, Long idUsuario, String nomeUsuario, Long idLiga, String nomeLiga,
			String urlImagem) {
		super();
		this.posicao = posicao;
		this.pontos = pontos;
		this.idUsuario = idUsuario;
		this.nomeUsuario = nomeUsuario;
		this.idLiga = idLiga;
		this.nomeLiga = nomeLiga;
		this.urlImagem = urlImagem;
	}
}
