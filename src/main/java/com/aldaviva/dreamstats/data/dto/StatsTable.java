package com.aldaviva.dreamstats.data.dto;

import com.aldaviva.dreamstats.data.model.StatsBundle;

public interface StatsTable<IndependentType, DependentType> {

	void increment(IndependentType independent, DependentType dependent);

	/*
	 * I could use a generic type, but that would require changing types
	 * everywhere every time the implementation changed.
	 */
	Object getTable();

	StatsBundle<IndependentType, DependentType> getBundle();
}
