package com.aldaviva.dreamstats.api.marshal;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.SerializerBase;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DurationSerializer extends SerializerBase<Duration> {

	private static final Logger LOGGER = LoggerFactory.getLogger(DurationSerializer.class);

	private final boolean isKey;

	public DurationSerializer(final boolean isKey) {
		super(Duration.class);
		this.isKey = isKey;
	}

	@Override
	public void serialize(final Duration value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
		final long result = value.getMillis()/1000;
		LOGGER.trace("serializing duration {}", result);
		if(isKey){
			jgen.writeFieldName(String.valueOf(result));
		} else {
			jgen.writeNumber(result);
		}
	}


}
