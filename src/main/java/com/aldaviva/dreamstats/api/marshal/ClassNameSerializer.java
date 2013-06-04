package com.aldaviva.dreamstats.api.marshal;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.SerializerBase;

@SuppressWarnings("rawtypes")
public class ClassNameSerializer extends SerializerBase<Class> {

	public ClassNameSerializer(){
		super(Class.class);
	}

	@Override
	public void serialize(final Class value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
		jgen.writeString(value.getSimpleName());
	}

}
