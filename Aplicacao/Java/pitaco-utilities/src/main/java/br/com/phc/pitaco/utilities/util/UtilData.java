package br.com.phc.pitaco.utilities.util;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import br.com.phc.pitaco.utilities.Constantes;

public class UtilData implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Long obtemLongMilisegundosData() {
		return Calendar.getInstance().getTimeInMillis();
	}

	public static String obtemStringMilisegundosData() {
		Long millis = Calendar.getInstance().getTimeInMillis();
		return millis.toString();
	}

	public static Date obtemDataMilisegundos(Long millis) {
		return new Date(millis);
	}

	public static String formatarDataString(Date dataHora) {
		DateFormat formater = new SimpleDateFormat(Constantes.DD_MM_YYYY_HH_MM);
		return formater.format(dataHora);
	}
	
	public static String formatarDataStringMask(Date dataHora, String mask) {
		DateFormat formater = new SimpleDateFormat(mask);
		return formater.format(dataHora);
	}

	public static Date formatarStringData(String dataHora) {
		SimpleDateFormat formato = new SimpleDateFormat(Constantes.DD_MM_YYYY_HH_MM);
		Date data = new Date();
		try {
			data = formato.parse(dataHora);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public static boolean verificaDataExpiradaCompareTo(Date dataExpiracao, Date dataAtual) {
		// retorna true para data ja expirada ou
		// retorna false para data valida
		boolean expirou = true;
		// dataAtual < dataExpiracao, retorna um valor menor que 0
		// dataAtual > dataExpiracao, retorna um valor maior que 0
		// dataAtual = dataExpiracao, então um 0 será mostrado no console
		Integer valor = dataAtual.compareTo(dataExpiracao);

		if (valor < 0 || valor == 0) {
			// entao a data ainda é valida
			return false;
		} else {
			if (valor > 0) {
				// entao a data expirou
				return true;
			}
		}
		return expirou;
	}

	public static boolean verificaDataExpirada(Date dataExpiracao, Date dataAtual) {
		// retorna true para data ja expirada ou
		// retorna false para data valida
		boolean expirou = true;

		// dataExpiracao antes de dataAtual || dataExpiracao igual a dataAtual
		if (dataExpiracao.before(dataAtual) || dataExpiracao.equals(dataAtual)) {
			// entao a data ainda é valida
			return false;
		} else {
			if (dataExpiracao.after(dataAtual)) {
				// entao a data expirou
				return true;
			}
		}
		return expirou;
	}
	
	public static String converteDataEmLong(Date data) {
		Long dataLong = data.getTime();
		
		return dataLong.toString();
	}
	
	public static String converteLongEmData(String dataLong) {
		Long data = Long.valueOf(dataLong);
		
		return formatarDataString(new Date(data));
	}
	
	public static Date converteStringMillisEmData(String dataLong) {
		Long data = Long.valueOf(dataLong);
		
		return new Date(data);
	}
	
	public static String converteLongMilisEmDataString(Long millis) {
		Date data = new Date(millis);
		
		return formatarDataString(data);
	}
	
	public static LocalDateTime convertStringInlocalDateTime(String dataHora) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constantes.DD_MM_YYYY_HH_MM);
		return  LocalDateTime.parse(dataHora, formatter);
	}
}
