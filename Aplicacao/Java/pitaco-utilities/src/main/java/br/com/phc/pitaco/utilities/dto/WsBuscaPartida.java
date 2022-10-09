package br.com.phc.pitaco.utilities.dto;

import java.io.Serializable;

import br.com.phc.pitaco.utilities.indicador.NotificaWebSocket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WsBuscaPartida implements Serializable {

	private static final long serialVersionUID = 1L;

	@Builder.Default
	private Long idUsuario = 1L;
	@Builder.Default
	private Integer inicio = 0;
	@Builder.Default
	private NotificaWebSocket notifica = NotificaWebSocket.INDIVIDUAL;
}
