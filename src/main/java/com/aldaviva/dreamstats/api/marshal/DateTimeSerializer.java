package com.aldaviva.dreamstats.api.marshal;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.SerializerBase;
import org.joda.time.DateTime;

public class DateTimeSerializer extends SerializerBase<DateTime> {

	public DateTimeSerializer() {
		super(DateTime.class);
	}

	@Override
	public void serialize(final DateTime value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
		jgen.writeNumber(value.getMillis()/1000);
	}


}
