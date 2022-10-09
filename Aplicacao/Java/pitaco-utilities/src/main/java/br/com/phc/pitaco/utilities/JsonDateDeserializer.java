package br.com.phc.pitaco.utilities;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;



public class JsonDateDeserializer extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser jsonParser, DeserializationContext ctxt) {
		SimpleDateFormat format = new SimpleDateFormat(Constantes.DD_MM_YYYY_HH_MM_SS);
		format.setTimeZone(TimeZone.getTimeZone(Constantes.TIME_ZONE_SP_BR));

        try {
        	String date = jsonParser.getText();
            return format.parse(date);
        } catch (ParseException | IOException e) {
            return null;
        }
	}
}
