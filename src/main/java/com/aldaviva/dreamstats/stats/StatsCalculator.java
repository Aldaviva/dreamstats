package com.aldaviva.dreamstats.stats;

import java.util.Map;

public interface StatsCalculator<IndependentType, DependentType> {

	Map<IndependentType, Map<DependentType, Integer>> calculateStats();

	IndependentType getIndependentBucket(IndependentType exact);

	DependentType getDependentBucket(DependentType exact);

}
