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

	protected void incrementTableBucket(final Map<IndependentType, Map<DependentType, Integer>> table, final IndependentType independent, final DependentType dependent) {
		Map<DependentType, Integer> column = table.get(independent);
		if(column == null){
			column = new HashMap<>();
			table.put(independent, column);
		}

		Integer bucket = column.get(dependent);
		if(bucket == null){
			bucket = 0;
		}

		column.put(dependent, bucket + 1);
	}

}