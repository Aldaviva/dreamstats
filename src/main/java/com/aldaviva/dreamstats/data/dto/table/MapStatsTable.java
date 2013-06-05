package com.aldaviva.dreamstats.data.dto.table;

import java.util.HashMap;
import java.util.Map;

import com.aldaviva.dreamstats.data.model.StatsBundle;

public class MapStatsTable<IndependentType, DependentType> implements StatsTable<IndependentType, DependentType> {

	private final Map<IndependentType, Map<DependentType, Integer>> table;
	private final StatsBundle<IndependentType, DependentType> bundle;

	public MapStatsTable(final StatsBundle<IndependentType, DependentType> bundle) {
		this.table = new HashMap<>();
		this.bundle = bundle;
	}

	@Override
	public void increment(final IndependentType independentBucket, final DependentType dependentBucket) {
		Map<DependentType, Integer> column = table.get(independentBucket);
		if(column == null){
			column = new HashMap<>();
			table.put(independentBucket, column);
		}

		Integer bucket = column.get(dependentBucket);
		if(bucket == null){
			bucket = 0;
		}

		column.put(dependentBucket, bucket + 1);
	}

//	@JsonUnwrapped
//	@JsonSerialize(typing=Typing.STATIC, as=HashMap.class, contentAs=HashMap.class)
//	@JsonProperty("stats")
	@Override
	public Map<IndependentType, Map<DependentType, Integer>> getTable() {
		return table;
	}

	@Override
	public StatsBundle<IndependentType, DependentType> getBundle() {
		return bundle;
	}

}
