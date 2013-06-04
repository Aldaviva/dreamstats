package com.aldaviva.dreamstats.api.marshal;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.SerializerBase;
import org.joda.time.LocalDate;

public class LocalDateSerializer extends SerializerBase<LocalDate> {

	private final boolean isKey;

	public LocalDateSerializer(final boolean isKey) {
		super(LocalDate.class);
		this.isKey = isKey;
	}

	@Override
	public void serialize(final LocalDate value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
		final int result = (int) (value.toDateMidnight().getMillis()/1000);
		if(isKey){
			jgen.writeFieldName(String.valueOf(result));
		} else {
			jgen.writeNumber(result);
		}
	}


}
