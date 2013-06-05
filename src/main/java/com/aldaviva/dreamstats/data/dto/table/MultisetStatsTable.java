package com.aldaviva.dreamstats.data.dto.table;


import com.aldaviva.dreamstats.data.model.StatsBundle;
import com.google.common.collect.Multiset;
import com.google.common.collect.TreeMultiset;

public class MultisetStatsTable<IndependentType, DependentType> implements StatsTable<IndependentType, DependentType> {

	private final Multiset<StatsCoordinate<IndependentType, DependentType>> table;
	private final StatsBundle<IndependentType, DependentType> bundle;

	public MultisetStatsTable(final StatsBundle<IndependentType, DependentType> bundle){
		this.bundle = bundle;
		table = TreeMultiset.create();
	}

	@Override
	public void increment(final IndependentType independentValue, final DependentType dependentValue) {
		table.add(new StatsCoordinate<>(independentValue, dependentValue));
	}

	@Override
	public Multiset<StatsCoordinate<IndependentType, DependentType>> getTable(){
		return table;
	}

	@Override
	public StatsBundle<IndependentType, DependentType> getBundle() {
		return bundle;
	}
}
