package com.aldaviva.dreamstats.stats;

import com.aldaviva.dreamstats.data.model.StatsBundle;

public interface StatsCalculator<IndependentType, DependentType> {

	StatsBundle<IndependentType, DependentType> getStatsBundle();

}
