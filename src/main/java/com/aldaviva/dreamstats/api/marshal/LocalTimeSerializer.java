package com.aldaviva.dreamstats.api.marshal;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.SerializerBase;
import org.joda.time.LocalTime;

public class LocalTimeSerializer extends SerializerBase<LocalTime> {

	private final boolean isKey;

	public LocalTimeSerializer(final boolean isKey) {
		super(LocalTime.class);
		this.isKey = isKey;
	}

	@Override
	public void serialize(final LocalTime value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
		final int result = value.millisOfDay().get()/1000;
		if(isKey){
			jgen.writeFieldName(String.valueOf(result));
		} else {
			jgen.writeNumber(result);
		}
	}


}
