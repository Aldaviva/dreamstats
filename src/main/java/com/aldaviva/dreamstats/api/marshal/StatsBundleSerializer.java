package com.aldaviva.dreamstats.api.marshal;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.SerializerBase;

import com.aldaviva.dreamstats.data.dto.StatsCoordinate;
import com.aldaviva.dreamstats.data.model.StatsBundle;
import com.google.common.collect.Multiset;

@SuppressWarnings("rawtypes")
public class StatsBundleSerializer extends SerializerBase<StatsBundle> {

	private static final String FIELD_COUNTS = "counts";
	private static final String FIELD_DEPENDENT = "dependent";
	private static final String FIELD_VALUES = "values";
	private static final String FIELD_TYPE = "type";
	private static final String FIELD_ID = "id";
	private static final String FIELD_INDEPENDENT = "independent";

	public StatsBundleSerializer(){
		super(StatsBundle.class);
	}

	@Override
	public void serialize(final StatsBundle bundle, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
		jgen.writeStartObject();

		final Multiset statsTable = bundle.getStatsTable();

		final List independentRange = bundle.getIndependentAxis().getRange();
		final List dependentRange = bundle.getDependentAxis().getRange();

		final int independentCount = independentRange.size();
		final int dependentCount = dependentRange.size();


		jgen.writeObjectFieldStart(FIELD_INDEPENDENT);
		jgen.writeStringField(FIELD_ID, bundle.getIndependentAxis().getId());
		jgen.writeStringField(FIELD_TYPE, bundle.getIndependentAxis().getType().getSimpleName());
		jgen.writeArrayFieldStart(FIELD_VALUES);
		for(final Object independentBucket : independentRange){
			for(int i=0; i < dependentCount; i++){
				jgen.writeObject(independentBucket);
			}
		}
		jgen.writeEndArray();
		jgen.writeEndObject();

		jgen.writeObjectFieldStart(FIELD_DEPENDENT);
		jgen.writeStringField(FIELD_ID, bundle.getDependentAxis().getId());
		jgen.writeStringField(FIELD_TYPE, bundle.getDependentAxis().getType().getSimpleName());
		jgen.writeArrayFieldStart(FIELD_VALUES);
		for(int i=0; i < independentCount; i++){
			for(final Object dependentBucket : dependentRange){
				jgen.writeObject(dependentBucket);
			}
		}
		jgen.writeEndArray();
		jgen.writeEndObject();

		jgen.writeObjectFieldStart(FIELD_COUNTS);
		jgen.writeArrayFieldStart(FIELD_VALUES);
		for(final Object independentBucket : independentRange){
			for(final Object dependentBucket : dependentRange){
				final StatsCoordinate coordinate = StatsCoordinate.create(independentBucket, dependentBucket);
				final int value = statsTable.count(coordinate);
				jgen.writeNumber(value);
			}
		}
		jgen.writeEndArray();
		jgen.writeEndObject();


		jgen.writeEndObject();
	}

}
