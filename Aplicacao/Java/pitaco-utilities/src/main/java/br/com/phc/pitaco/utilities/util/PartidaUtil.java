package br.com.phc.pitaco.utilities.util;

import java.text.Normalizer;
import java.util.List;
import java.util.Objects;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import br.com.phc.pitaco.utilities.Constantes;
import br.com.phc.pitaco.utilities.dto.PalpiteDTO;
import br.com.phc.pitaco.utilities.model.Partida;
import br.com.phc.pitaco.utilities.repository.jdbc.EquipeJdbcRepository;
import br.com.phc.pitaco.utilities.repository.jdbc.PalpiteJdbcRepository;
import br.com.phc.pitaco.utilities.repository.jdbc.PartidaJdbcRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ConditionalOnProperty(name = Constantes.ENABLE_PARTIDA_UTILITIES, havingValue = "true", matchIfMissing = false)
@Component
public class PartidaUtil {

	private static final String ERRO_MSG = "ERRO: %s";
	private static final String INFO_MSG = "INFO: %s";

	@Autowired
	private PartidaJdbcRepository partidaRepository;
	
	@Autowired
	private EquipeJdbcRepository equipeRepository;
	
	@Autowired
	private PalpiteJdbcRepository palpiteRepository;
	
	public void buscaResultadoPartidaGoogle(Partida partida) {
		Document doc;
		String url = "";
		try {
			// need http protocol
			url = montaUrlPartida(partida.getMandante().getIdEquipe(), partida.getVisitante().getIdEquipe());
			doc = Jsoup.connect(url + "&hl=pt-BR").get();

			// get page title
			String title = doc.title();
			this.gravaLogInfo(String.format("title : %s", title));

			Integer periodoJogo = 0;
			String mensagemTempoJogo = null;
			
			String msgValidaDia = null;
			try {
				msgValidaDia = doc.select("div[class=imso-hide-overflow]").first().text();
				if (Objects.nonNull(msgValidaDia) && !(msgValidaDia.toLowerCase().contains("hoje"))) {
					this.gravaLogInfo(String.format("O jogo encontrado no google nao é de hoje: %s", msgValidaDia));
					return;
				}
			}catch (Exception e) { }

			try {
				//antes do jogo
				mensagemTempoJogo = doc.select("div[class=imso_mh__vs-at-sep imso_mh__team-names-have-regular-font]").first().text();
				periodoJogo = 0;
			} catch (Exception e) { /*vazio*/ }
			try {
				//bola rolando ou intervalo
				mensagemTempoJogo = doc.select("div[class=imso_mh__lv-m-stts-cont]").first().text();
				periodoJogo = 1;
				if (mensagemTempoJogo.contains("Pênaltis")) {
					periodoJogo = 3;	
				}
			} catch (Exception e) { /*vazio*/ }
			try {
				//encerrado
				mensagemTempoJogo = doc.select("span[class=imso_mh__ft-mtch imso-medium-font imso_mh__ft-mtchc]").first().text();
				periodoJogo = 2;
			} catch (Exception e) { /*vazio*/ }

			mensagemTempoJogo = corrigeTempoPartida(mensagemTempoJogo);
			this.gravaLogInfo(mensagemTempoJogo);

			if (periodoJogo > 0) {
				String placarTimeCasa = doc.select("div[class=imso_mh__l-tm-sc imso_mh__scr-it imso-light-font]").first().text();
				String placarTimeVisitante = doc.select("div[class=imso_mh__r-tm-sc imso_mh__scr-it imso-light-font]").first().text();

				this.gravaLogInfo(String.format("%s x %s", placarTimeCasa, placarTimeVisitante));
				
				Integer placarCasa = formataStringInteger(placarTimeCasa);
				Integer placarVisitante = formataStringInteger(placarTimeVisitante);
				
				switch (periodoJogo) {
				case 1:
					this.gravaLogInfo(String.format("O jogo esta com: %s", mensagemTempoJogo));
					// primeira inserção
					if (partida.getTempoPartida() == null) {
						partidaRepository.alterarTempoPartida(partida.getIdPartida(), mensagemTempoJogo);

						//insere o placar de inicio do jogo para recalcular a pontuacao
						atualizaPontosPalpite(partida.getIdPartida(), 0, 0);
					}
					// verifica se houve alteracao do tempo pra nao ficar salvando o mesmo valor
					if (partida.getTempoPartida() != null && !partida.getTempoPartida().equalsIgnoreCase(mensagemTempoJogo)) {
						partidaRepository.alterarTempoPartida(partida.getIdPartida(), mensagemTempoJogo);
					}
					break;
				case 2:
					this.gravaLogInfo(String.format("O jogo encontra-se: %s", mensagemTempoJogo));
					if (partida.getTempoPartida() != null && !partida.getTempoPartida().equalsIgnoreCase(mensagemTempoJogo)) {
						partidaRepository.alterarTempoPartida(partida.getIdPartida(), mensagemTempoJogo);
						
						//busca penaltes no encerramento da partida
						buscaPenalidades(doc, partida);
					}
					break;
				case 3:
						//busca penaltes no encerramento da partida
						buscaPenalidades(doc, partida);
					break;
				default:
					this.gravaLogInfo("O jogo ainda não se iniciou");
					break;
				}

				String gols = buscaGols(doc);
				//verifica se sao diferentes para nao ficar salvando desnecessariamente
				if (partida.getPlacarEquipeCasa() != placarCasa || partida.getPlacarEquipeVisitante() != placarVisitante) {
					alteraPlacarPartida(partida, placarCasa, placarVisitante);
					if (!gols.equals("SEM_GOLS")) {
						verificaGolsPartida(gols, partida);
					}
				}
				
				/* Verificar os jogadores que fizeram gols na partida  - inicio */
				if (!gols.equals("SEM_GOLS")) {
					verificaGolsPartida(gols, partida);
				}
				/* Verificar os jogadores que fizeram gols na partida  - fim */
			} else {
				this.gravaLogInfo("O jogo ainda não se iniciou");
			}
		} catch (Exception e) {
			this.gravaLogInfo(String.format(ERRO_MSG, e.getMessage()));
			this.gravaLogInfo(String.format(ERRO_MSG, url));
		}
	}
	
	private void atualizaPontosPalpite(Long idPartida, Integer placarCasa, Integer placarVisitante) {
		this.gravaLogInfo("Inserindo o placar de inicio: 0 x 0");
		List<PalpiteDTO> lista = palpiteRepository.listarPalpitesIdPartida(idPartida);
		Integer pontos = 0;
		for (PalpiteDTO p : lista) {
			if(p.getPlacarEquipeCasa() == placarCasa && p.getPlacarEquipeVisitante() == placarVisitante) {
				//vale 5 pontos
				pontos = 5;
			} else {
				if(p.getPlacarEquipeCasa() > p.getPlacarEquipeVisitante() && placarCasa > placarVisitante) {
					//time da casa venceu
					if((placarCasa - placarVisitante) == (p.getPlacarEquipeCasa() - p.getPlacarEquipeVisitante())) {
						//vale 3 pontos
						pontos = 3;
					} else {
						//vale 1 ponto
						pontos = 1;
					}
				} else {
					if(p.getPlacarEquipeCasa() < p.getPlacarEquipeVisitante() && placarCasa < placarVisitante) {
						//time visitante venceu
						if((placarVisitante - placarCasa) == (p.getPlacarEquipeVisitante() - p.getPlacarEquipeCasa())) {
							//vale 3 pontos
							pontos = 3;
						} else {
							//vale 1 ponto
							pontos = 1;
						}
					} else {
						//deu empate
						if((p.getPlacarEquipeCasa() == p.getPlacarEquipeVisitante()) && (placarCasa == placarVisitante)) {
							//vale 3 pontos
							pontos = 3;
						} else {
							//vale 0 ponto
							pontos = 0;
						}
					}
				}
			}
			this.gravaLogInfo(String.format("Pontos inicio: %s", pontos));

			p.setPontos(pontos);
			palpiteRepository.alterarPontosPalpite(p);
		}
	}
	
	private  String montaUrlPartida(Long idEquipeCasa, Long idEquipeVisitante) {
		String equipeCasa = buscaNomeEquipe(idEquipeCasa);
		String equipeVisitante = buscaNomeEquipe(idEquipeVisitante);

		try {
			/*
			String[] casa = equipeCasa.split("-");
			String[] visitante = equipeVisitante.split("-");
			
			return Constantes.BASE_URL_GOOGLE + casa[0].replace(" ", "+") + "+x+"+ visitante[0].replace(" ", "+");
			*/
			return Constantes.BASE_URL_GOOGLE + equipeCasa.replace("-", "+") + "+x+"+ equipeVisitante.replace("-", "+");
		} catch (Exception e) {
			this.gravaLogInfo(String.format(ERRO_MSG, e.getMessage()));
		}	
		return Constantes.BASE_URL_GOOGLE;
	}
	
	private String buscaNomeEquipe(Long idEquipe) {
		return removerAcentos(equipeRepository.buscarNomeEquipeId(idEquipe));
	}
	
	public static String removerAcentos(String str) {
	    return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}
	
	private static String corrigeTempoPartida(String tempo) {
		String mensagemTempoJogo = "";
		if(tempo.contains("'")) {
			mensagemTempoJogo = tempo.replace(" ", "");
			mensagemTempoJogo = mensagemTempoJogo.replace("'", "").concat(" min");
		} else {
			if(tempo.contains("+")) {
				mensagemTempoJogo = tempo.replace(" ", "").concat(" min");
			} else {
				return tempo;
			}
		}
		return mensagemTempoJogo;
	}
	
	private void buscaPenalidades(Document doc, Partida partida) {
		try {
			String penalidades = doc.select("div[class=imso_mh_s__psn-sc]").text();			
			String completo = penalidades.substring(0, 5).replace(" ", "");
			String[] divisao = completo.split("-");
			
			String equipeCasa = divisao[0];
			String equipeVisitante = divisao[1];
			
			this.gravaLogInfo(String.format("(%s)penaltis(%s)", equipeCasa, equipeVisitante));
			
			partida.setPlacarExtendidoEquipeCasa(equipeCasa);
			partida.setPlacarExtendidoEquipeVisitante(equipeVisitante);
			partidaRepository.alterarPartidaPlacarExtendido(partida);
			
		} catch (Exception e) {
			this.gravaLogInfo(String.format(ERRO_MSG, "Não há penalidades."));
		}
	}
	
	private Integer formataStringInteger(String numero) {
		Integer valor;
		try {
			valor = Integer.parseInt(numero);
		} catch (Exception e) {
			valor = 0;
		}

		return valor;
	}
	
	private void alteraPlacarPartida(Partida par, Integer placarEquipeCasa, Integer placarEquipeVisitante) {
		if (par == null) {
			return;
		} else {
			// verifica se os placares sao diferentes, caso true faz a alteração no banco
			if (par.getPlacarEquipeCasa() != placarEquipeCasa || par.getPlacarEquipeVisitante() != placarEquipeVisitante) {
				Partida partida = new Partida();
				partida.setIdPartida(par.getIdPartida());
				partida.setPlacarEquipeCasa(placarEquipeCasa);
				partida.setPlacarEquipeVisitante(placarEquipeVisitante);

				partidaRepository.alterarPlacarPartida(partida);
				this.gravaLogInfo("Alterando o placar no banco de dados");
			}
		}
	}
	
	private String buscaGols(Document doc) {
		StringBuilder golsTimeCasa = new StringBuilder();
        StringBuilder golsTimeVisitante = new StringBuilder();
		try {
			//busca gols da equipe da casa
			Elements timeCasa = doc.select("div[class=imso_gs__tgs imso_gs__left-team]").select("div[class=imso_gs__gs-r]");			
			Integer quantidadeTotal = timeCasa.size();
			Integer contador = 0;
			for (Element e: timeCasa) {
				contador++;
				String valor = e.select("div[class=imso_gs__gs-r]").text();
				if(quantidadeTotal == contador) {
					golsTimeCasa.append(valor);
				} else {
					golsTimeCasa.append(valor + "\n");
				}
				this.gravaLogInfo(valor);
			}
			
			//busca gols da equipe visitante
			Elements timeVisitante = doc.select("div[class=imso_gs__tgs imso_gs__right-team]").select("div[class=imso_gs__gs-r]");			
			quantidadeTotal = timeVisitante.size();
			contador = 0;
			for (Element e: timeVisitante) {
				contador++;
				String valor = e.select("div[class=imso_gs__gs-r]").text();
				if(quantidadeTotal == contador) {
					golsTimeVisitante.append(valor);
				} else {
					golsTimeVisitante.append(valor + "\n");
				}
				this.gravaLogInfo(valor);
			}
			
			//verifica se existe gols na partida
			if(timeCasa.isEmpty() && timeVisitante.isEmpty()) {
				this.gravaLogInfo(String.format(INFO_MSG, "Não há gols na partida."));
				return "SEM_GOLS";
			}
			
		} catch (Exception e) {
			this.gravaLogInfo("Erro ao buscar gols na partida");
			this.gravaLogInfo(String.format(ERRO_MSG, e.getMessage()));
		}
		return golsTimeCasa.toString() + "@" + golsTimeVisitante.toString();
	}
	
	private void verificaGolsPartida(String gols, Partida partida) {
		//existe gols na partida
        String[] golsTimes = gols.split("@");
        //realiza a alteração no banco de dados
        String golsTimeCasa = "";
        String golsTimeVisitante = "";
        try {
        	golsTimeCasa = golsTimes[0];
        }catch (Exception e) { /*vazio*/ }
        try {
        	golsTimeVisitante = golsTimes[1];
        }catch (Exception e) { /*vazio*/ }
        
        if((partida.getGolsEquipeCasa() != null || golsTimeCasa != null) || (partida.getGolsEquipeVisitante() != null || golsTimeVisitante != null)) {
        	partida.setGolsEquipeCasa(partida.getGolsEquipeCasa() == null ? "" : partida.getGolsEquipeCasa());
        	partida.setGolsEquipeVisitante(partida.getGolsEquipeVisitante() == null ? "" : partida.getGolsEquipeVisitante());
        	if((!partida.getGolsEquipeCasa().equalsIgnoreCase(golsTimeCasa)) || (!partida.getGolsEquipeVisitante().equalsIgnoreCase(golsTimeVisitante))) {
        		golsTimeCasa = golsTimeCasa.trim().length() == 0 ? null : golsTimeCasa;
        		golsTimeVisitante = golsTimeVisitante.trim().length() == 0 ? null : golsTimeVisitante;
                this.gravaLogInfo(String.format("Equipe visitante: %s", golsTimeVisitante));
                this.gravaLogInfo(String.format("Equipe casa: %s", golsTimeCasa));
            	alteraGolsPartida(partida, golsTimeCasa, golsTimeVisitante);
        	}
        }
	}
	
	private void alteraGolsPartida(Partida par, String golsEquipeCasa, String golsEquipeVisitante) {
		if (par == null) {
			return;
		} else {
			Partida partida = new Partida();
			partida.setIdPartida(par.getIdPartida());
			partida.setGolsEquipeCasa(golsEquipeCasa);
			partida.setGolsEquipeVisitante(golsEquipeVisitante);

			partidaRepository.alterarPlacarPartida(partida);
			this.gravaLogInfo("Alterando gols da partida no banco de dados");
			
		}
	}
	
	private void gravaLogInfo(String mensagem) {
		log.info(mensagem);
	}
}
