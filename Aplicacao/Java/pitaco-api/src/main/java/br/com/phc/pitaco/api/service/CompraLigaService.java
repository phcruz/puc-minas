package br.com.phc.pitaco.api.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.phc.pitaco.utilities.model.CompraLiga;
import br.com.phc.pitaco.utilities.repository.CompraLigaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CompraLigaService {

	@Autowired
	private CompraLigaRepository compraLigaRepository;

	public void inserirCompraLiga(CompraLiga compraLigaUsuario) {
		log.info("Acessando inserirCompraLiga(CompraLiga compraLigaUsuario)");
		compraLigaUsuario.setDataCompra(Calendar.getInstance().getTime());
		compraLigaRepository.save(compraLigaUsuario);
	}

}
