package br.com.phc.pitaco.utilities.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitacaoGrupoUsuarioDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idSolicitacaoGrupoUsuario;
	private Long idGrupo;
	private Long idLiga;
	private Long idUsuarioAdmin;
	private Long idUsuarioSolicitante;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", locale = "pt-BR", timezone = "America/Sao_Paulo")
	private Date dataSolicitacao;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", locale = "pt-BR", timezone = "America/Sao_Paulo")
	private Date dataResposta;
	private boolean permiteParticipar;
	private boolean ativo;
	// novos
	private String urlImagemAvatarUsuario;
	private String nomeUsuario;

}
