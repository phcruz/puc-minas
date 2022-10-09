package br.com.phc.pitaco.details.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.com.phc.pitaco.utilities.dto.ArtilheiroDTO;
import br.com.phc.pitaco.utilities.exception.BadRequestException;
import br.com.phc.pitaco.utilities.util.ArtilhariaJsoupUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ArtilhariaService {

	@Cacheable(value = "buscarArtilhariaLigaCache", keyGenerator = "customKeyGenerator")
	public List<ArtilheiroDTO> buscarArtilhariaLiga(Integer idLiga) {
		String urlConsulta = verificaUrlLigaSelecionada(idLiga);
		if (urlConsulta == null) {
			log.info("Não ha liga mapeada para obter artilharia");
			throw new BadRequestException("Não há dados disponíveis sobre artilharia no momento.");	
		}

		List<ArtilheiroDTO> artilheiros = ArtilhariaJsoupUtil.getInstance().getArtilhariaLiga(urlConsulta);
		log.info("Total de artilheiros: {}", artilheiros.size());
		return artilheiros;
	}

	private String verificaUrlLigaSelecionada(Integer idLiga) {
		switch (idLiga) {
			case 1:
				return "https://globoesporte.globo.com/futebol/copa-do-brasil/";
			case 2:
				return "https://globoesporte.globo.com/futebol/brasileirao-serie-a/";
		default:
			return null;
		}
	}
}
