package com.aldaviva.dreamstats.api.marshal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.SerializerBase;

import com.aldaviva.dreamstats.data.dto.table.StatsCoordinate;
import com.aldaviva.dreamstats.data.model.StatsBundle;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;

@SuppressWarnings("rawtypes")
public class StatsBundleSerializer extends SerializerBase<StatsBundle> {

	private static final String FIELD_COUNTS = "counts";
	private static final String FIELD_DEPENDENT = "dependent";
	private static final String FIELD_VALUES = "values";
	private static final String FIELD_TYPE = "type";
	private static final String FIELD_ID = "id";
	private static final String FIELD_INDEPENDENT = "independent";
	private static final String FIELD_MIN = "min";
	private static final String FIELD_MAX = "max";
	private static final String FIELD_LENGTH = "length";
	private static final String FIELD_RANKS = "ranks";

	public StatsBundleSerializer(){
		super(StatsBundle.class);
	}

	@Override
	public void serialize(final StatsBundle bundle, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
		jgen.writeStartObject();

		final Multiset<StatsCoordinate> statsTable = bundle.getStatsTable();

		final List independentRange = bundle.getIndependentAxis().getRange();
		final List dependentRange = bundle.getDependentAxis().getRange();

		final int independentCount = independentRange.size();
		final int dependentCount = dependentRange.size();

		final List<Integer> counts = new ArrayList<>(independentCount * dependentCount);
		final List<Double> ranks = new ArrayList<>(independentCount * dependentCount);
		final Set<Integer> distinctCounts = new TreeSet<>();
		distinctCounts.add(0);

		for(final Entry<StatsCoordinate> entry : statsTable.entrySet()){
			distinctCounts.add(entry.getCount());
		}

		final List<Integer> orderedCounts = new ArrayList<>(distinctCounts);
		final int numRanks = distinctCounts.size();

		for(final Object independentBucket : independentRange){
			for(final Object dependentBucket : dependentRange){
				final StatsCoordinate coordinate = StatsCoordinate.create(independentBucket, dependentBucket);
				final int value = statsTable.count(coordinate);
				counts.add(value);
				final int rank = Collections.binarySearch(orderedCounts, value);
				ranks.add((double)rank/(numRanks-1));
			}
		}

		jgen.writeObjectFieldStart(FIELD_INDEPENDENT);
		jgen.writeStringField(FIELD_ID, bundle.getIndependentAxis().getId());
		jgen.writeStringField(FIELD_TYPE, bundle.getIndependentAxis().getType().getSimpleName());
		jgen.writeObjectField(FIELD_MIN, bundle.getIndependentAxis().getMin());
		jgen.writeObjectField(FIELD_MAX, bundle.getIndependentAxis().getMax());
		jgen.writeNumberField(FIELD_LENGTH, independentCount);
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
		jgen.writeObjectField(FIELD_MIN, bundle.getDependentAxis().getMin());
		jgen.writeObjectField(FIELD_MAX, bundle.getDependentAxis().getMax());
		jgen.writeNumberField(FIELD_LENGTH, dependentCount);
		jgen.writeArrayFieldStart(FIELD_VALUES);
		for(int i=0; i < independentCount; i++){
			for(final Object dependentBucket : dependentRange){
				jgen.writeObject(dependentBucket);
			}
		}
		jgen.writeEndArray();
		jgen.writeEndObject();

		jgen.writeObjectFieldStart(FIELD_COUNTS);
		jgen.writeObjectField(FIELD_VALUES, counts);
		jgen.writeObjectField(FIELD_RANKS, ranks);
		jgen.writeEndObject();

		jgen.writeEndObject();
	}

}
