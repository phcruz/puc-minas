package br.com.phc.pitaco.details.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.phc.pitaco.utilities.Constantes;
import br.com.phc.pitaco.utilities.dto.AcessoDTO;
import br.com.phc.pitaco.utilities.model.Acesso;
import br.com.phc.pitaco.utilities.model.AcessoDados;
import br.com.phc.pitaco.utilities.model.Usuario;
import br.com.phc.pitaco.utilities.repository.AcessoDadosRepository;
import br.com.phc.pitaco.utilities.repository.AcessoRepository;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AcessoService {

	private static final String ACESSO_INICIAL_PLATAFORMA = "Acesso a plataforma realizado com sucesso.";

	@Autowired
	private AcessoRepository acessoRepository;
	
	@Autowired
	private AcessoDadosRepository acessoDadosRepository;

	public String contagemAcessoPlataforma(AcessoDTO acessoDTO, HttpServletRequest request) {
		log.info("Acessando contadorAcessoUsuario()");
		Acesso acessoExistente = buscarAcessoPlataforma(acessoDTO);
		Acesso acesso = null;
		if (Objects.nonNull(acessoExistente)) {
			acessoExistente.setQuantidade(acessoExistente.getQuantidade() + Constantes.QUANTIDADE_INICIAL_ACESSO);
			acessoExistente.setDataUltimoAcesso(new Date());
			acesso = acessoRepository.save(acessoExistente);
		} else {
			acesso = new Acesso();
			acesso.setUsuario(new Usuario(acessoDTO.getIdUsuario()));
			acesso.setPlataforma(acessoDTO.getPlataforma());
			acesso.setQuantidade(Constantes.QUANTIDADE_INICIAL_ACESSO);
			acesso.setDataUltimoAcesso(new Date());
			acesso = acessoRepository.save(acesso);
		}

		this.insereAcessoDados(acesso, request, acessoDTO);
		return ACESSO_INICIAL_PLATAFORMA;
	}

	public Acesso buscarAcessoPlataforma(AcessoDTO acessoDTO) {
		log.info("buscarAcessoPlataforma(Acesso acesso)");
		return acessoRepository.findByPlataformaAndUsuarioIdUsuario(acessoDTO.getPlataforma(), acessoDTO.getIdUsuario());
	}
	
	private void insereAcessoDados(Acesso acesso, HttpServletRequest request, AcessoDTO acessoDTO) {
		AcessoDados acessoDados = new AcessoDados();
		acessoDados.setUserIp(this.obtemIpRequest(request));
		acessoDados.setUserAgent(acessoDTO.getDados().getDescricao());
		acessoDados.setDescricao(this.replaceConteudo(acessoDTO.getDados().getDescricao()));
		acessoDados.setDispositivo(acessoDTO.getDados().getDispositivo());
		acessoDados.setSistema(acessoDTO.getDados().getSistema());
		acessoDados.setDetalhe(acessoDTO.getDados().getDetalhe());
		acessoDados.setDataAcesso(new Date());
		acessoDados.setAcesso(acesso);
		
		List<AcessoDados> listaAcesso = acessoDadosRepository.findByAcesso(acesso);
		
		Optional<AcessoDados> acessoExistente = listaAcesso.stream().filter(item -> item.equals(acessoDados)).findFirst();
		if (!acessoExistente.isPresent()) {
			acessoDadosRepository.save(acessoDados);
		} else {
			acessoDados.setIdAcessoDados(acessoExistente.get().getIdAcessoDados());
			acessoDadosRepository.save(acessoDados);
		}
	}
	
	public String obtemIpRequest(HttpServletRequest request) {
		String ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null) {
			ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
			    ipAddress = request.getHeader("X_FORWARDED_FOR");
			    if (ipAddress == null){
			        ipAddress = request.getRemoteAddr();
			    }
			}
		}
		return ipAddress;
	}

	public String getUserAgente(HttpServletRequest request) {
	    UserAgent ua = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
	    ua.getBrowser().getName();
	    ua.getOperatingSystem().getName();
		return request.getHeader("User-Agent");
	}

	private String replaceConteudo(String descricao) {
		try {
			String parcial = descricao.replace("Mozilla/5.0 (", "");
			Integer posicao = parcial.indexOf(") AppleWebKit");
			return parcial.substring(0, posicao);
		}catch (Exception e) {
			return descricao;
		}
	}
}
