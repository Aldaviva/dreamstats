package com.aldaviva.dreamstats.api.marshal;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.SerializerBase;
import org.codehaus.jackson.map.type.SimpleType;

import com.aldaviva.dreamstats.data.dto.StatsCoordinate;
import com.google.common.collect.Multiset.Entry;
import com.google.common.collect.Multisets;

@SuppressWarnings("rawtypes")
public class MultisetEntrySerializer extends SerializerBase<Entry<StatsCoordinate>> {

	private static final String VALUE_FIELD_KEY = "count";
	private static final String DEPENDENT_FIELD_KEY = "dep";
	private static final String INDEPENDENT_FIELD_KEY = "indep";

	public MultisetEntrySerializer(){
		super(SimpleType.construct(Multisets.immutableEntry(new StatsCoordinate(), 0).getClass()));
	}

	@Override
	public void serialize(final Entry<StatsCoordinate> value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
		jgen.writeStartObject();
		jgen.writeNumberField(VALUE_FIELD_KEY, value.getCount());
		jgen.writeObjectField(INDEPENDENT_FIELD_KEY, value.getElement().independentCoord);
		jgen.writeObjectField(DEPENDENT_FIELD_KEY, value.getElement().dependentCoord);
		jgen.writeEndObject();
	}

}
