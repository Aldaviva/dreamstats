package com.aldaviva.dreamstats.api.marshal;

import com.aldaviva.dreamstats.data.dto.table.StatsCoordinate;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.SerializerBase;
import org.codehaus.jackson.map.type.CollectionType;
import org.codehaus.jackson.map.type.SimpleType;

import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;

@SuppressWarnings("rawtypes")
public class TripleArraySerializer extends SerializerBase<Multiset<StatsCoordinate>> {

	public TripleArraySerializer() {
		super(CollectionType.construct(Multiset.class, SimpleType.construct(StatsCoordinate.class)));
	}

	@Override
	public void serialize(final Multiset<StatsCoordinate> value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
		jgen.writeStartObject();

		jgen.writeArrayFieldStart("independent");
		for(final Entry<StatsCoordinate> entry : value.entrySet()){
			jgen.writeObject(entry.getElement().independentCoord);
		}
		jgen.writeEndArray();


		jgen.writeArrayFieldStart("dependent");
		for(final Entry<StatsCoordinate> entry : value.entrySet()){
			jgen.writeObject(entry.getElement().dependentCoord);
		}
		jgen.writeEndArray();

		jgen.writeArrayFieldStart("value");
		for(final Entry<StatsCoordinate> entry : value.entrySet()){
			jgen.writeNumber(entry.getCount());
		}
		jgen.writeEndArray();

		jgen.writeEndObject();
	}

}