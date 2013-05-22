package com.aldaviva.dreamstats.stats;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;

import com.aldaviva.dreamstats.remote.calendar.CalendarService;

public abstract class BaseStatsCalculator<IndependentType, DependentType> implements StatsCalculator<IndependentType, DependentType> {

	@Autowired protected CalendarService calendarService;

	public BaseStatsCalculator() {
		super();
	}

	protected Duration bucketizeDuration(final Duration exact) {
		final Period exactPeriod = exact.toPeriod();
		final int exactHours = exactPeriod.getHours();
		final int exactMinutes = exactPeriod.getMinutes();
		final Period result = new Period().withHours(exactHours).withMinutes(Math.round(exactMinutes/30.0f));
		return result.toStandardDuration();
	}

	protected void incrementTableBucket(final Map<IndependentType, Map<DependentType, Integer>> table, final IndependentType independentExact, final DependentType dependentExact) {
		final IndependentType independentBucket = getIndependentBucket(independentExact);
		final DependentType dependentNBucket = getDependentBucket(dependentExact);

		Map<DependentType, Integer> column = table.get(independentBucket);
		if(column == null){
			column = new HashMap<>();
			table.put(independentBucket, column);
		}

		Integer bucket = column.get(dependentNBucket);
		if(bucket == null){
			bucket = 0;
		}

		column.put(dependentNBucket, bucket + 1);
	}

}