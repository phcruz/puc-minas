package br.com.phc.pitaco.notification.service;

import org.springframework.stereotype.Service;

import br.com.phc.pitaco.utilities.Constantes;
import br.com.phc.pitaco.utilities.dto.EmailTimeCoracaoDTO;
import br.com.phc.pitaco.utilities.model.Usuario;
import br.com.phc.pitaco.utilities.util.EnviaEmail;
import br.com.phc.pitaco.utilities.util.UtilMetodos;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailService {

	public void recuparaSenhaEmail(Usuario user, String novaSenha) {
		log.info("Acessando recuparaSenhaEmail(Usuario user, String novaSenha)");
		String assunto = EnviaEmail.assuntoEmailRecuperacaoSenha();
		String conteudo = EnviaEmail.conteudoEmailRecuperacaoSenhaHtml(user.getNome(), novaSenha);
		
		EnviaEmail.sendMail(user.getNome(), user.getEmail(), assunto, conteudo);
	}
	
	public void enviaConviteEmail(String nomeUsuario, String email) {
		log.info("Acessando enviaConviteEmail(String nomeUsuario, String email)");
		String assunto = EnviaEmail.assuntoEmailConvite();
		String conteudo = EnviaEmail.conteudoConviteEmailHtml(nomeUsuario);
		
		EnviaEmail.sendMail(nomeUsuario, email, assunto, conteudo);
	}
	
	public void enviaValidacaoEmail(String nomeUsuario, String email, String token) {
		log.info("Acessando enviaValidacaoEmail(String nomeUsuario, String email, String token)");
		// String url = "http://192.168.25.186:8080/usuario/ativarUsuario/" + token;
		String url = Constantes.URL_BASE_USUARIO_API + "usuario/ativarUsuario?token=" + token + "&transaction=" + UtilMetodos.encodeXRegisterpublic();
		String assunto = EnviaEmail.assuntoEmailValidacao();
		String conteudo = EnviaEmail.conteudoValidacaoEmailHtml(nomeUsuario, url);
		
		EnviaEmail.sendMail(nomeUsuario, email, assunto, conteudo);
	}

	public String getEmailHtmlCadastroAtivo() {
		log.info("Acessando getEmailHtmlCadastroAtivo()");
		return EnviaEmail.getEmailHtmlCadastroAtivo();
	}
	
	public void enviaEmailAdminUsuariosInativos(String nomeUsuario, String email, Integer quantidade) {
		log.info("Acessando enviaAdmin(String nomeUsuario, String email)");
		String assunto = EnviaEmail.assuntoEmailAdminUsuariosInativos();
		String conteudo = EnviaEmail.conteudoEmailAdminHtml(nomeUsuario, quantidade);
		
		EnviaEmail.sendMail(nomeUsuario, email, assunto, conteudo);
	}

	public void enviaEmailJogoTimeCoracao(EmailTimeCoracaoDTO dadosUser) {
		log.info("Acessando enviaEmailJogoTimeCoracao(EmailTimeCoracaoDTO dadosUser)");
		String assunto = EnviaEmail.assuntoEmailJogotimeCoracao();
		String conteudo = EnviaEmail.conteudoEmailJogotimeCoracaoHtml(dadosUser);
		
		EnviaEmail.sendMail(dadosUser.getNomeUsuario(), dadosUser.getEmail(), assunto, conteudo);
	}
}
