package com.aldaviva.dreamstats.api.marshal;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.SerializerBase;
import org.joda.time.Duration;

public class DurationSerializer extends SerializerBase<Duration> {

	private final boolean isKey;

	public DurationSerializer(final boolean isKey) {
		super(Duration.class);
		this.isKey = isKey;
	}

	@Override
	public void serialize(final Duration value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
		final long result = value.getMillis()/1000;
		if(isKey){
			jgen.writeFieldName(String.valueOf(result));
		} else {
			jgen.writeNumber(result);
		}
	}


}
