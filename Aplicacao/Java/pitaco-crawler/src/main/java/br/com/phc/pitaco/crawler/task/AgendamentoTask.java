package br.com.phc.pitaco.crawler.task;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.phc.pitaco.crawler.service.GenericService;
import br.com.phc.pitaco.crawler.service.PartidaService;
import br.com.phc.pitaco.crawler.websocket.ClientWebSocket;
import br.com.phc.pitaco.utilities.Constantes;
import br.com.phc.pitaco.utilities.config.EmailProvider;
import br.com.phc.pitaco.utilities.dto.EmailTimeCoracaoDTO;
import br.com.phc.pitaco.utilities.dto.WsBuscaPartida;
import br.com.phc.pitaco.utilities.indicador.NotificaWebSocket;
import br.com.phc.pitaco.utilities.model.Partida;
import br.com.phc.pitaco.utilities.util.PartidaUtil;
import br.com.phc.pitaco.utilities.util.UtilData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableScheduling
public class AgendamentoTask {

	private static final String TIME_ZONE = "America/Sao_Paulo";

	@Autowired
	private PartidaService partidaService;

	@Autowired
	private EmailProvider emailProvider;

	@Autowired
	private PartidaUtil partidaUtil;

	@Autowired
	private GenericService genericService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ClientWebSocket clientWebSocket;

	@Scheduled(cron = "0/30 * 19-23 * * MON", zone = TIME_ZONE)
	public void taskAgendamentoPartidasSegunda() {
		this.inicializaAgendamento("taskAgendamentoPartidasSegunda()");
	}

	@Scheduled(cron = "0/30 * 16-23 * * TUE", zone = TIME_ZONE)
	public void taskAgendamentoPartidasTerca() {
		this.inicializaAgendamento("taskAgendamentoPartidasSegunda()");
	}

	@Scheduled(cron = "0/30 * 16-23 * * WED", zone = TIME_ZONE)
	public void taskAgendamentoPartidasQuarta() {
		this.inicializaAgendamento("taskAgendamentoPartidasQuarta()");
	}

	@Scheduled(cron = "0/30 * 16-23 * * THU", zone = TIME_ZONE)
	public void taskAgendamentoPartidasQuinta() {
		this.inicializaAgendamento("taskAgendamentoPartidasQuinta()");
	}

	@Scheduled(cron = "0/30 * 16-23 * * FRI", zone = TIME_ZONE)
	public void taskAgendamentoPartidasSexta() {
		this.inicializaAgendamento("taskAgendamentoPartidasSexta()");
	}

	@Scheduled(cron = "0/30 * 15-23 * * SAT", zone = TIME_ZONE)
	public void taskAgendamentoPartidasSabado() {
		this.inicializaAgendamento("taskAgendamentoPartidasSabado()");
	}

	@Scheduled(cron = "0/30 * 11-23 * * SUN", zone = TIME_ZONE)
	public void taskAgendamentoPartidasDomingo() {
		this.inicializaAgendamento("taskAgendamentoPartidasDomingo()");
	}

	@Scheduled(cron = "0/30 * 14-16 * * TUE-WED", zone = TIME_ZONE)
	public void taskAgendamentoPartidasChampions() {
		this.inicializaAgendamento("taskAgendamentoPartidasChampions()");
	}

	private void inicializaAgendamento(String diaSemana) {
		this.gravaLogInfo(String.format("%s:  %s", diaSemana, criaDataHora()));

		realizaTarefasAgendadasRobo();
	}

	private void realizaTarefasAgendadasRobo() {
		Integer qtdPartidas = partidaService.buscarPartidasRaspagemHoje();

		if (qtdPartidas != 0) {
			List<Partida> partidas = partidaService.listarTodasPartidasRaspagemHoje();
			for (Partida par : partidas) {
				partidaUtil.buscaResultadoPartidaGoogle(par);
			}
			this.createAndSendMessageWebsocket();
		}
	}

	@Scheduled(cron = "0 00 10 * * *", zone = TIME_ZONE)
	public void enviaEmailUsuarioTimeCoracao() {
		List<EmailTimeCoracaoDTO> result = genericService.listaDadosEmailTimeCoracao();
		realizaEnvioEmailJogoTimeCoracao(result);

		this.gravaLogInfo(String.format("enviaEmailUsuarioTimeCoracao(DIARIO): %s -> enviado para %d usuarios",
				criaDataHora(), result.size()));
	}

	/*
	 * @Scheduled(cron = "0 30 19 * * FRI", zone = TIME_ZONE) public void
	 * enviaEmailUsuarioInativoUmaVezSemana() { this.gravaLogInfo(String.
	 * format("enviaEmailUsuarioInativoUmaVezSemana(SEMANAL): %s", criaDataHora()));
	 * 
	 * realizaTarefasEnvioEmail(); }
	 * 
	 * private void realizaTarefasEnvioEmail() { Integer qtdUsuariosInativos =
	 * usuarioService.buscarQtdUsuarioInativo();
	 * 
	 * if (qtdUsuariosInativos != 0) { List<Usuario> usuarios =
	 * usuarioService.listarUsuariosInativos(); for (Usuario user : usuarios) {
	 * 
	 * String token = jwtTokenProvider.gerarToken(user.getEmail(), new
	 * ArrayList<>(Arrays.asList(Role.ROLE_USUARIO)));
	 * 
	 * emailProvider.enviaValidacaoEmail(user.getNome(), user.getEmail(), token); }
	 * 
	 * emailProvider.enviaEmailAdminUsuariosInativos(Constantes.NOME_ADMIN_PITACO,
	 * Constantes.EMAIL_ADMIN_PITACO, qtdUsuariosInativos); } }
	 */

	private void realizaEnvioEmailJogoTimeCoracao(List<EmailTimeCoracaoDTO> result) {
		for (EmailTimeCoracaoDTO dadosUser : result) {
			emailProvider.enviaEmailJogoTimeCoracao(dadosUser);
		}
	}

	private void gravaLogInfo(String mensagem) {
		log.info(mensagem);
	}

	private String criaDataHora() {
		return UtilData.formatarDataStringMask(new Date(), Constantes.DD_MM_YYYY_HH_MM_SS);
	}

	private void createAndSendMessageWebsocket() {
		try {
			WsBuscaPartida wsBuscaPartida = WsBuscaPartida.builder().notifica(NotificaWebSocket.TODOS).build();
			String message = objectMapper.writeValueAsString(wsBuscaPartida);
			clientWebSocket.sendMessageWebSocket(message);
		} catch (Exception e) {
			log.error("Error ao tentar converter WsBuscaPartida em string");
		}
	}
}
