package br.com.phc.pitaco.utilities.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import br.com.phc.pitaco.utilities.Constantes;
import br.com.phc.pitaco.utilities.dto.ArtilheiroDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ConditionalOnProperty(name = Constantes.ENABLE_ARTILHARIA_UTILITIES, havingValue = "true", matchIfMissing = false)
@Component
public class ArtilhariaJsoupUtil {

	private static ArtilhariaJsoupUtil artilhariaJsoupUtil;

	public static ArtilhariaJsoupUtil getInstance() {
		if (Objects.isNull(artilhariaJsoupUtil)) {
			artilhariaJsoupUtil = new ArtilhariaJsoupUtil();
		}
		return artilhariaJsoupUtil;
	}

	public List<ArtilheiroDTO> getArtilhariaLiga(String urlConsulta) {
		List<ArtilheiroDTO> artilheiros = new ArrayList<>();

		Document doc;
		try {
			doc = Jsoup.connect(urlConsulta).get();

			String title = doc.title();
			log.info(title);

			Elements infos = doc.select("div[class=ranking-content]").select("div[class=ranking-item-wrapper]");

			String rankingAtual = "";

			for (Element s : infos) {

				rankingAtual = s.select("div[class=ranking-item]").text().isEmpty() ? rankingAtual : s.select("div[class=ranking-item]").text();

				ArtilheiroDTO artilheiro = new ArtilheiroDTO();
				artilheiro.setNome(s.select("div[class=jogador]").select("div[class=jogador-info]").select("div[class=jogador-nome]").text());
				artilheiro.setPosicao(s.select("div[class=jogador]").select("div[class=jogador-info]").select("div[class=jogador-posicao]").text());
				artilheiro.setGols(s.select("div[class=jogador]").select("div[class=jogador-gols]").text());
				artilheiro.setTime(s.select("div[class=jogador]").select("div[class=jogador-escudo]").select("img").attr("alt"));
				artilheiro.setUrlJogador(s.select("div[class=jogador]").select("div[class=jogador-foto]").select("img").attr("src"));
				artilheiro.setUrlEscudoTime(s.select("div[class=jogador]").select("div[class=jogador-escudo]").select("img").attr("src"));
				artilheiro.setRanking(rankingAtual);

				artilheiros.add(artilheiro);
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return artilheiros;
	}
}
