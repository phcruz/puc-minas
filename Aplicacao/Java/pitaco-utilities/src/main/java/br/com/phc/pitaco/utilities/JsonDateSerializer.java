package br.com.phc.pitaco.utilities;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@Component
public class JsonDateSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider provider) throws IOException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constantes.DD_MM_YYYY_HH_MM_SS);
		dateFormat.setTimeZone(TimeZone.getTimeZone(Constantes.TIME_ZONE_SP_BR));
		
		String formattedDate = dateFormat.format(date);
		gen.writeString(formattedDate);
	}
}
