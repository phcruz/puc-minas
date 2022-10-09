package br.com.phc.pitaco.utilities.util;

import java.io.Serializable;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.com.phc.pitaco.utilities.Constantes;
import br.com.phc.pitaco.utilities.dto.EmailTimeCoracaoDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Paulo Henrique da Cruz
 *
 *         Classe responsável pelo envio do email de recueração de senha. Nele é
 *         gerado uma senha randomica de 6 a 12 caracteres com numeros e letras
 *         (padrão adotado pelo site para aumentar a segurança dos usuarios) e
 *         enviado ao email do usuário, com informações para alterar sua senha
 *         no site.
 */
@Slf4j
public class EnviaEmail implements Serializable {

	private static final long serialVersionUID = 1L;

	public static String assuntoEmailRecuperacaoSenha() {
		return "Recuperamos sua senha. Equipe Pitaco FC.";
	}

	public static String assuntoEmailConvite() {
		return "Convite para participar de grupo. Equipe Pitaco FC.";
	}

	public static String assuntoEmailValidacao() {
		return "Valide seu e-mail cadastrado em nosso App. Equipe Pitaco FC.";
	}
	
	public static String assuntoEmailAdminUsuariosInativos() {
		return "Tokens válidos enviados a usuários inativos. Equipe Pitaco FC.";
	}

	public static String assuntoEmailJogotimeCoracao() {
		return "Hoje tem jogo do seu time. Equipe Pitaco FC.";
	}
	
	public static String conteudoEmailRecuperacaoSenhaHtml(String nome, String senha) {
		String corpo = "Olá " + nome + ".<br/>" + "Recebemos a sua solicitação e estamos lhe enviando uma nova senha."
				+ "<br/>Senha: " + senha
				+ "<br/>Essa senha é provisória, então sugerimos que faça o login em nosso App, "
				+ "vá em seu perfil e altere essa senha." + "<br/><br/>Atenciosamente,<br/><br/><br/>"
				+ "<a href='https://www.pitacofc.com.br/' target='_blank'><b>Pitaco FC</b> ⚽</a>"
				+ "<br/>Nós ❤ futebol!";
		return corpo;
	}

	public static String conteudoConviteEmailHtml(String nome) {
		String corpo = "Olá Pitaqueiro." + "<br/>" + nome
				+ " convidou você para participar do Pitaco FC, o melhor aplicativo de competição " + "esportiva."
				+ "<br/>Clique no botão e baixe o aplicativo."
				+ "<br/>Venha competir com seus amigos e se divertir palpitando sobre os jogos "
				+ "das suas Ligas favoritas." + "<br/>Seja o maior pitaqueiro de todos os tempos."
				+ "<br/><br/>Atenciosamente,<br/><br/><br/>"
				+ "<a href='https://www.pitacofc.com.br/' target='_blank'><b>Pitaco FC</b> ⚽</a>"
				+ "<br/>Nós ❤ futebol!";

		return corpo;
	}

	public static String conteudoValidacaoEmailHtml(String nome, String url) {
		String corpo = "Olá " + nome + ".<br/>"
				+ "Estamos muito felizes em ter você em nossa plataforma de palpites e previsões esportivas."
				+ "<br/>Para sua segurança enviamos este e-mail para confirmar a autenticidade de seu e-mail cadastrado."
				+ "Clique no botão validar cadastro abaixo e liberaremos o seu acesso em nosso App."
				+ "<br/><a href='" + url + "'>"
				+ "<img src='https://drive.google.com/uc?id=1hR7hJZTbWLoJM-ecU89qisFV584L5nNs' alt='Validar cadastro' width='180' height='180' border='0' style='max-width: 600px; height: auto; font-family: sans-serif; font-size: 15px; line-height: 15px; color: #555555; margin: auto; display: block;margin-left: auto; margin-right: auto; padding: 10px' class='g-img'>"
				+ "</a>"
				+ "Caso não consiga visualizar o botão, acesse pelo link: "
				+ "<br/><a href='" + url + "'>"
				+ "<b>Validar cadastro Pitaco FC</b>"
				+ "</a>"
				+ "<br/><br/>Atenciosamente,<br/><br/><br/>"
				+ "<a href='https://www.pitacofc.com.br/' target='_blank'><b>Pitaco FC</b> ⚽</a>"
				+ "<br/>Nós ❤ futebol!";

		return corpo;
	}
	
	public static String conteudoEmailAdminHtml(String nome, Integer quantidade) {
		String corpo = "Olá " + nome + ".<br/>"
				+ "Estamos com alguns usuarios inativos em nossa base de dados."
				+ "<br/>Enviamos a todos estes usuários um email com um token de segurança renovado e válido por 7 dias."
				+ "<br/>Quantidade de usuarios inativos: "+ quantidade + ".<br/>"
				+ "<br/>Atenciosamente,<br/><br/><br/>"
				+ "<a href='https://www.pitacofc.com.br/' target='_blank'><b>Pitaco FC</b> ⚽</a>"
				+ "<br/>Nós ❤ futebol!";

		return corpo;
	}
	
	public static String conteudoEmailJogotimeCoracaoHtml(EmailTimeCoracaoDTO dadosUser) {
		String corpo = "Olá Pitaqueiro "
				+ "Hoje tem jogo do seu time do coração " + dadosUser.getNomeUsuario() + ".<br/>Confira os dados abaixo:"
				+ "<br/><br/>"
				+ "<div style='color: black;'>"
				+ "<b>Partida: </b>" + dadosUser.getNomeEquipeCasa() + " x " + dadosUser.getNomeEquipeVisitante() + "<br/>"
				+ "<b>Local: </b>" + dadosUser.getLocalJogo() +"<br/>" 
				+ "<b>Data: </b>" + dadosUser.getDataHoraJogo() + "<br/>"
				+ "<br/>"
				+ "<div style='display: flex; justify-content: center;'>"
				+ "<img src='" + dadosUser.getUrlImagemEquipeCasa() +"' width='75px' height='75px' alt='" + dadosUser.getNomeEquipeCasa() +"'/>"
				+ "<img src='https://www.pagina1pb.com.br/wp-content/uploads/2018/11/X-png-23.png' width='25px' height='25px' style='margin-top: 30px; vertical-align: top;'/>"
				+ "<img src='" + dadosUser.getUrlImagemEquipeVisitante() + "' width='75px' height='75px' alt='" + dadosUser.getNomeEquipeVisitante() +"'/>"
				+ "</div>"
				+ "</div>"
				+ "<br/>"
				+ "<br/>Já deu o seu palpite na partida? Corre lá, ainda dá tempo."
				+ "<br/>Seja o maior pitaqueiro de todos os tempos."
				+ "<br/><br/>Atenciosamente,<br/><br/><br/>"
				+ "<a href='https://www.pitacofc.com.br/' target='_blank'><b>Pitaco FC</b> ⚽</a>"
				+ "<br/>Nós ❤ futebol!";

		return corpo;
	}

	private static String getHtmlBaseEmail(String conteudoEmail) {
		String html = "";

		html = "<!DOCTYPE html>"
				+ "<html lang='pt-br' xmlns='http://www.w3.org/1999/xhtml' xmlns:v='urn:schemas-microsoft-com:vml' xmlns:o='urn:schemas-microsoft-com:office:office'>"
				+ "<head>" + "<meta charset='UTF-8'> <!-- utf-8 works for most cases -->"
				+ "<meta name='viewport' content='width=device-width'> <!-- Forcing initial-scale shouldn't be necessary -->"
				+ "<meta http-equiv='X-UA-Compatible' content='IE=edge'> <!-- Use the latest (edge) version of IE rendering engine -->"
				+ "<meta name='x-apple-disable-message-reformatting'>  <!-- Disable auto-scale in iOS 10 Mail entirely -->"
				+ "<title></title> <!-- The title tag shows in email notifications, like Android 4.4. -->" + "<style>"
				+ "html,"
				+ "body {margin: 0 auto !important;padding: 0 !important;height: 100% !important;width: 100% !important;}"
				+ "* {-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;}"
				+ "div[style*='margin: 16px 0'] {margin: 0 !important;}" + "table,"
				+ "td {mso-table-lspace: 0pt !important;mso-table-rspace: 0pt !important;}"
				+ "table {border-spacing: 0 !important;border-collapse: collapse !important;table-layout: fixed !important;margin: 0 auto !important;}"
				+ "table table table {table-layout: auto;}" + "img {-ms-interpolation-mode:bicubic;}"
				+ "a {text-decoration: none;}" + "*[x-apple-data-detectors],  /* iOS */"
				+ ".unstyle-auto-detected-links *,"
				+ ".aBn {border-bottom: 0 !important;cursor: default !important;color: inherit !important;text-decoration: none !important;font-size: inherit !important;font-family: inherit !important;font-weight: inherit !important;line-height: inherit !important;}"
				+ ".a6S {display: none !important; opacity: 0.01 !important;}"
				+ "img.g-img + div {display: none !important;}"
				+ "@media only screen and (min-device-width: 320px) and (max-device-width: 374px) {u ~ div .email-container {min-width: 320px !important;}}"
				+ "@media only screen and (min-device-width: 375px) and (max-device-width: 413px) {u ~ div .email-container {min-width: 375px !important;}}"
				+ "@media only screen and (min-device-width: 414px) {u ~ div .email-container {min-width: 414px !important;}}"
				+ "</style>" + "<style>" + ".button-td," + ".button-a {transition: all 100ms ease-in;}"
				+ ".button-td-primary:hover,"
				+ ".button-a-primary:hover {background: #555555 !important;border-color: #555555 !important;}"
				+ "@media screen and (max-width: 600px) {.email-container p {font-size: 17px !important;}}" + "</style>"
				+ "</head>"
				+ "<body width='100%' style='margin: 0; padding: 0 !important; mso-line-height-rule: exactly; background-color: #FFFFFF; margin: 0; padding: 0;'>"
				+ "<center style='width: 100%; background-color: #FFFFFF;'>"
				+ "<div style='max-width: 600px; margin: 0 auto;' class='email-container'>"
				+ "<table align='center' role='presentation' cellspacing='0' cellpadding='0' border='0' width='100%' style='margin: 0 auto;'>"
				+ "<tr>" + "<td style='padding: 10px 0; text-align: center'>" + "</td>" + "</tr>" + "<tr>"
				+ "<td bgcolor='#222'>"
				+ "<img src='https://drive.google.com/uc?id=1PvdZftxjrVD0XTWVUphQohwKQMvKZxWc' alt='Pitaco FC' width='260' height='160' border='0' style='max-width: 600px; height: auto; font-family: sans-serif; font-size: 15px; line-height: 15px; color: #222; margin: auto; display: block;margin-left: auto; margin-right: auto; background-color: #222; padding: 10px' class='g-img'>"
				+ "</td>" + "</tr>" + "<tr>" + "<td style='background-color: #ffffff;'>"
				+ "<table role='presentation' cellspacing='0' cellpadding='0' style='border-left:1px solid #CCC; border-right:1px solid #CCC' width='100%'>"
				+ "<tr>"
				+ "<td style='padding: 20px; font-family: sans-serif; font-size: 15px; line-height: 20px; color: #555555;'>"
				+ conteudoEmail + "</td>" + "</tr>" + "<tr>" + "<td style='padding: 0 20px; align: center;'>"
				// + "<a
				// href='https://docs.google.com/forms/d/e/1FAIpQLSda6S-9ccsPQZR-KX9e7K3xvPOFru1flvydyf1Ap_JgXgu65g/viewform?usp=sf_link'>"
				// + "<img
				// src='http://nespol.com.br/blog/wp-content/uploads/2018/06/nespol-botao-quero-participar.png'
				// alt='Avaliar App' width='180' height='180' border='0' style='max-width:
				// 600px; height: auto; font-family: sans-serif; font-size: 15px; line-height:
				// 15px; color: #555555; margin: auto; display: block;margin-left: auto;
				// margin-right: auto; padding: 10px' class='g-img'>"
				+ "<a href='https://play.google.com/store/apps/details?id=phc.com.br.pitacofc'>"
				+ "<img src='https://drive.google.com/uc?id=1gFN04n-SVU58Y6N9baZVGYtlC8AtIiTo' alt='Google Play' width='180' height='180' border='0' style='max-width: 600px; height: auto; font-family: sans-serif; font-size: 15px; line-height: 15px; color: #555555; margin: auto; display: block;margin-left: auto; margin-right: auto; padding: 10px' class='g-img'>"
				+ "</a>" + "</td>" + "</tr>" + "</table>" + "</td>" + "</tr>" + "</table>"
				+ "<table align='center' role='presentation' cellspacing='0' cellpadding='0' border='0' width='100%' style='margin: 0 auto; background-color: #222; '>"
				+ "<tr>" + "<td style='color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;'>"
				+ "<p style='color: #FFFFFF; padding: 12px;'>" + "&reg; Pitaco FC<br/>"
				+ "&copy; 2020 Todos os direitos reservados<br/>" + "Desenvolvido por Paulo Henrique da Cruz</p>"
				+ "</td>" + "<td align='right'>" + "<table border='0' cellpadding='0' cellspacing='0'>" + "<tr>"
				+ "<td>" + "<a href='https://twitter.com/FcPitaco'>"
				+ "<img src='https://drive.google.com/uc?id=19J-auaVCbhFwRrZn8kwuP-HjSP5Z3n0A' alt='Twitter Pitaco FC' width='38' height='38' style='padding: 10px;' border='0' />"
				+ "</a>" + "</td>" + "<td>" + "<a href='https://www.facebook.com/pitacofc.oficial/'>"
				+ "<img src='https://drive.google.com/uc?id=1vtKvBLrxjR39UXrJCY1IUL18wz-yPK0R' alt='Facebook Pitaco FC' width='38' height='38' style='padding: 10px;' border='0' />"
				+ "</a>" + "</td>" + "<td>" + "<a href='https://www.instagram.com/pitacofc.app/'>"
				+ "<img src='https://drive.google.com/uc?id=1bJiHyoz-TkrgfFGL1ehry8-3hue1F_N8' alt='Instagram Pitaco FC' width='38' height='38' style='padding: 10px;' border='0' />"
				+ "</a>" + "</td>" + "</tr>" + "</table>" + "</td>" + "</tr>" + "</table>" + "</div>" + "</center>"
				+ "</body>" + "</html>";

		return html;
	}

	public static String getEmailHtmlCadastroAtivo() {
		String html = "";
		html = "<!DOCTYPE html>"
				+ "<html lang='pt-br' xmlns='http://www.w3.org/1999/xhtml' xmlns:v='urn:schemas-microsoft-com:vml' xmlns:o='urn:schemas-microsoft-com:office:office'>"
				+ "<head>" + "<meta charset='UTF-8'>" + "<!-- utf-8 works for most cases -->"
				+ "<meta name='viewport' content='width=device-width'>"
				+ "<!-- Forcing initial-scale shouldn't be necessary -->"
				+ "<meta http-equiv='X-UA-Compatible' content='IE=edge'>"
				+ "<!-- Use the latest (edge) version of IE rendering engine -->"
				+ "<meta name='x-apple-disable-message-reformatting'>"
				+ "<!-- Disable auto-scale in iOS 10 Mail entirely -->" + "<title>Conta ativada com sucesso</title>"
				+ "<!-- The title tag shows in email notifications, like Android 4.4. -->" + "<style>"
				+ "html, body { margin: 0 auto !important; padding: 0 !important; height: 100% !important; width: 100% !important; }"
				+ "* { -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; }"
				+ "div[style*='margin: 16px 0'] { margin: 0 !important; }"
				+ "table, td { mso-table-lspace: 0pt !important; mso-table-rspace: 0pt !important; }"
				+ "table { border-spacing: 0 !important; border-collapse: collapse !important; table-layout: fixed !important; margin: 0 auto !important; }"
				+ "table table table { table-layout: auto; }" + "img { -ms-interpolation-mode: bicubic; }"
				+ "a { text-decoration: none; }" + "*[x-apple-data-detectors]," + "/* iOS */"
				+ ".unstyle-auto-detected-links *, .aBn { border-bottom: 0 !important; cursor: default !important; color: inherit !important; text-decoration: none !important; font-size: inherit !important;"
				+ "font-family: inherit !important; font-weight: inherit !important; line-height: inherit !important; }"
				+ ".a6S { display: none !important; opacity: 0.01 !important; }"
				+ "img.g-img + div { display: none !important; }"
				+ "@media only screen and (min-device-width: 320px) and (max-device-width: 374px) { u ~ div .email-container { min-width: 320px !important; } }"
				+ "@media only screen and (min-device-width: 375px) and (max-device-width: 413px) { u ~ div .email-container { min-width: 375px !important; } }"
				+ "@media only screen and (min-device-width: 414px) { u ~ div .email-container { min-width: 414px !important; } }"
				+ "</style>" + "<style>" + ".button-td, .button-a { transition: all 100ms ease-in; }"
				+ ".button-td-primary:hover, .button-a-primary:hover { background: #555555 !important; border-color: #555555 !important; }"
				+ "@media screen and (max-width: 600px) { .email-container p { font-size: 17px !important; } }"
				+ "</style>" + "</head>"

				+ "<body width='100%' style='margin: 0; padding: 0 !important; mso-line-height-rule: exactly; background-color: #FFFFFF; margin: 0; padding: 0;'>"
				+ "<center style='width: 100%; background-color: #FFFFFF;'>"
				+ "<div style='max-width: 600px; margin: 0 auto;' class='email-container'>"
				+ "<table align='center' role='presentation' cellspacing='0' cellpadding='0' border='0' width='100%' style='margin: 0 auto;'>"
				+ "<tr>" + "<td style='padding: 10px 0; text-align: center'></td>" + "</tr>" + "<tr>"
				+ "<td bgcolor='#222'>"
				+ "<img src='https://drive.google.com/uc?id=1PvdZftxjrVD0XTWVUphQohwKQMvKZxWc' alt='Pitaco FC' width='260' height='160' border='0' style='max-width: 600px; height: auto; font-family: sans-serif; font-size: 15px; line-height: 15px; color: #222; margin: auto; display: block;margin-left: auto; margin-right: auto; background-color: #222; padding: 10px' class='g-img'>"
				+ "</td>" + "</tr>" + "<tr>" + "<td style='background-color: #ffffff;'>"
				+ "<table role='presentation' cellspacing='0' cellpadding='0' style='border-left:1px solid #CCC; border-right:1px solid #CCC' width='100%'>"
				+ "<tr>"
				+ "<td style='padding: 20px; font-family: sans-serif; font-size: 15px; line-height: 20px; color: #555555;'>Olá Pitaqueiro."
				+ "<br/>Estamos muito felizes em ter você em nossa plataforma de palpites e previsões esportivas."
				+ "<br/>Seu cadastro está ativo, preparamos tudo para você aproveitar o máximo em nosso App. Informe o e-mail e senha cadastrados em nosso App e divirta-se."
				+ "<br/>" + "<br/>Atenciosamente," + "<br/>" + "<br/>"
				+ "<br/><a href='https://www.pitacofc.com.br/' target='_blank'><b>Pitaco FC</b> ⚽</a>"
				+ "<br/>Nós ❤ futebol!</td>" + "</tr>" + "<tr>" + "<td style='padding: 0 20px; align: center;'>"
				+ "<a href='https://play.google.com/store/apps/details?id=phc.com.br.pitacofc'>"
				+ "<img src='https://drive.google.com/uc?id=1gFN04n-SVU58Y6N9baZVGYtlC8AtIiTo' alt='Google Play' width='180' height='180' border='0' style='max-width: 600px; height: auto; font-family: sans-serif; font-size: 15px; line-height: 15px; color: #555555; margin: auto; display: block;margin-left: auto; margin-right: auto; padding: 10px' class='g-img'>"
				+ "</a>" + "</td>" + "</tr>" + "</table>" + "</td>" + "</tr>" + "</table>"
				+ "<table align='center' role='presentation' cellspacing='0' cellpadding='0' border='0' width='100%' style='margin: 0 auto; background-color: #222; '>"
				+ "<tr>" + "<td style='color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;'>"
				+ "<p style='color: #FFFFFF; padding: 12px;'>&reg; Pitaco FC"
				+ "<br/>&copy; 2020 Todos os direitos reservados" + "<br/>Desenvolvido por Paulo Henrique da Cruz</p>"
				+ "</td>" + "<td align='right'>" + "<table border='0' cellpadding='0' cellspacing='0'>" + "<tr>"
				+ "<td>"
				+ "<a href='https://twitter.com/FcPitaco'><img src='https://drive.google.com/uc?id=19J-auaVCbhFwRrZn8kwuP-HjSP5Z3n0A' alt='Twitter Pitaco FC' width='38' height='38' style='padding: 10px;' border='0' /></a>"
				+ "</td>" + "<td>"
				+ "<a href='https://www.facebook.com/pitacofc.oficial/'><img src='https://drive.google.com/uc?id=1vtKvBLrxjR39UXrJCY1IUL18wz-yPK0R' alt='Facebook Pitaco FC' width='38' height='38' style='padding: 10px;' border='0' /></a>"
				+ "</td>" + "<td>"
				+ "<a href='https://www.instagram.com/pitacofc.app/'><img src='https://drive.google.com/uc?id=1bJiHyoz-TkrgfFGL1ehry8-3hue1F_N8' alt='Instagram Pitaco FC' width='38' height='38' style='padding: 10px;' border='0' /></a>"
				+ "</td>" + "</tr>" + "</table>" + "</td>" + "</tr>" + "</table>" + "</div>" + "</center>" + "</body>"
				+ "</html>";

		return html;
	}
	
	public static void sendMail(String nomeUsuario, String email, String assunto, String conteudo) {
        try {
            Message message = new MimeMessage(getJavaMailSession());

        	message.setFrom(new InternetAddress(Constantes.EMAIL_PITACO_FC_DOMAIN));
			// Configura o Destinatário da mensagem
			Address[] toUser = InternetAddress.parse(email);

			// Configura o Assunto da mensagem
			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject(assunto);
			message.setContent(getHtmlBaseEmail(conteudo), "text/html; charset=UTF-8");
			
			Transport.send(message);
			
        	log.info("Email enviado com sucesso para: " + email);
		} catch (MessagingException e) {
			log.info("ERRO: " + e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
    }
	
	public static Session getJavaMailSession() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "pitacofc.com.br");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587");
		
		return Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(Constantes.EMAIL_PITACO_FC_DOMAIN,
						Constantes.SENHA_PITACO_FC_DOMAIN);
			}
		});
	}

}
