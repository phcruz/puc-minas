package br.com.phc.pitaco.utilities;

import java.io.Serializable;

public class Constantes implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String CORRELATION_ID = "correlation-id";

	// Frase segredo do token, Não passar pra niguem!
	public static final String FRASE_SEGREDO = "br.com.phc.pitaco";
	public static final String HEADER_STRING = "Authorization";
	public static final String TOKEN_PREFIXO = "Bearer ";
	public static final String HEADER_REGISTER_PUBLIC = "x-register-public";
	public static final Integer QUANTIDADE_DIAS_EXPIRACAO_TOKEN = 7;
	public static final Integer QUANTIDADE_MINUTOS_ANTES_PARTIDA = -30;
	public static final Long ID_IMAGEM_PADRAO = 1L;
	// informacoes do email
	public static final String EMAIL_PITACO_FC_DOMAIN = "contato@pitaco.com.br";
	public static final String SENHA_PITACO_FC_DOMAIN = "ntnZV6?TE9!W";
	// informacoes email admin
	public static final String EMAIL_ADMIN_PITACO = "henryque_phc@yahoo.com.br";
	public static final String NOME_ADMIN_PITACO = "Paulo Henrique da Cruz";

	public static final String URL_BASE_USUARIO_API = "https://www.pitaco.com.br/pitaco-gateway/";

	// tipo de cadastro
	public static final String APLICATIVO = "Aplicativo";
	public static final String FACEBOOK = "Facebook";
	public static final String GOOGLE = "Google";

	// notificacao para todos os usuarios
	public static final Integer NOTIFICACAO_TODOS_USUARIO = 0;

	// toque notificacao
	public static final Integer GRANDE_JOGADA = 1;
	public static final Integer JOGO_BONITO = 2;
	public static final Integer QUE_BELEZA = 3;
	public static final Integer FORTE_BOMBA = 4;

	// limite consultas
	public static final String LIMITE_CONSULTA_BANCO = "30";
	public static final String LIMITE_TAMANHO_NOME = "20";

	// url base de pesquisa
	public static final String BASE_URL_GOOGLE = "https://www.google.com.br/search?q=";

	// jogos adiado
	public static final String JOGO_ADIADO = "JOGO ADIADO";

	// time zone
	public static final String TIME_ZONE_SP_BR = "America/Sao_Paulo";

	// format time
	public static final String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";
	public static final String DD_MM_YYYY_HH_MM = "dd/MM/yyyy HH:mm";
	
	public static final Integer QUANTIDADE_INICIAL_ACESSO = 1;
	
	public static final String ENABLE_SWAGGER = "swagger.enable";
	public static final String ENABLE_SECURITY = "security.enable";
	public static final String ENABLE_UTIL_JWT = "utilities.jwt.enable";
	public static final String ENABLE_SECURITY_DETAILS = "security.details.enable";
	public static final String ENABLE_PARTIDA_UTILITIES = "partida.utilities.enable";
	public static final String ENABLE_PARTIDA_EXTERNAL_UTILITIES = "partida.external.utilities.enable";
	public static final String ENABLE_ARTILHARIA_UTILITIES = "artilharia.utilities.enable";
	public static final String ENABLE_DATASOURCE = "datasource.enable";
}
