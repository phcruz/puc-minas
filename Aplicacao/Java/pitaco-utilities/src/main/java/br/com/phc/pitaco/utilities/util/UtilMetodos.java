package br.com.phc.pitaco.utilities.util;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UtilMetodos implements Serializable {

	private static final long serialVersionUID = 1L;

	public static boolean regexEmail(String email) {
        String regex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})$";

        //classe onde e definido o padrao (Pattern) para validacao
        Pattern pattern = Pattern.compile(regex);
        //classe que verifica se a String esta de acordo com o Pattern
        Matcher matcher = pattern.matcher(email);
        //retorna o resultado da verificação, se estiver ok true, senão false
        return matcher.matches();
    }

    public static boolean validaSenha(String senha) {
        //ValidationExpression="(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,12})$"
        //ErrorMessage="Sua senha deve entre 6 e 16 caracteres. Use letras e números."
        String regex = "(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,18})$";

        //classe onde e definido o padrao (Pattern) para validacao
        Pattern pattern = Pattern.compile(regex);
        //classe que verifica se a String esta de acordo com o Pattern
        Matcher matcher = pattern.matcher(senha);
        //retorna o resultado da verificação, se estiver ok true, senão false
        return matcher.matches();
    }

    public static String criptografaSenha(String senha) {
        try {
            MessageDigest algoritmo = MessageDigest.getInstance("SHA-512");
            byte[] digestMessage = algoritmo.digest(senha.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexSenha = new StringBuilder();
            for (byte aByte : digestMessage) {
                hexSenha.append(String.format("%02X", 0xFF & aByte));
            }
            return hexSenha.toString();
        } catch (NoSuchAlgorithmException e) {
        	String message = String.format("Erro1 criptografia: %s", e.getMessage());
        	log.error(message);
        }
        return "";
    }
    
    /**
	 * Método responsavel por gerar uma senha aleatoria de 6 a 12 caracteres
	 * contendo numeros e letras.
	 */
	public static String geraNovaSenha() {
		String letras = "0123456789abcdefghijklmnopqrstuvwxyz";
		Integer tamanhoSenha =  new Random().nextInt(7) + 5;

		StringBuilder senha = new StringBuilder();
		for (Integer i = 0; i <= tamanhoSenha; i++) {
			senha.append(letras.charAt(new Random().nextInt(36)));
		}

		return senha.toString();
	}
	
	public static String encodeXRegisterpublic() {
		String publicRegister = "register-public:application-web:" + UtilData.formatarDataString(new Date());
		try {
			return  Base64.getEncoder().encodeToString(publicRegister.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
	public static String decodeXRegisterpublic(String publicRegister) {
		return new String(Base64.getDecoder().decode(publicRegister));
	}
}
