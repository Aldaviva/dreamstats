package com.aldaviva.dreamstats.stats;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;

import com.aldaviva.dreamstats.data.dto.Axis;
import com.aldaviva.dreamstats.data.dto.StatsBundle;
import com.aldaviva.dreamstats.remote.calendar.CalendarService;

public abstract class BaseStatsCalculator<IndependentType, DependentType> implements StatsCalculator<IndependentType, DependentType> {

	public static final Duration DEFAULT_DURATION_INTERVAL = Duration.standardMinutes(60);

	@Autowired protected CalendarService calendarService;

	public BaseStatsCalculator() {
		super();
	}

	@Override
	public StatsBundle<IndependentType, DependentType> getStatsBundle(){
		final StatsBundle<IndependentType, DependentType> statsBundle = new StatsBundle<>();
		statsBundle.setStats(calculateStats());
		statsBundle.getAxes().independentAxis = getIndependentAxis();
		statsBundle.getAxes().dependentAxis = getDependentAxis();
		statsBundle.updateAxesFromStats();

		return statsBundle;
	}

	protected abstract Map<IndependentType, Map<DependentType, Integer>> calculateStats();

	protected abstract IndependentType getIndependentBucket(IndependentType exact);
	protected abstract DependentType getDependentBucket(DependentType exact);

	protected abstract Axis<IndependentType> getIndependentAxis();
	protected abstract Axis<DependentType> getDependentAxis();

	/**
	 * Put the given @param exact Duration into 30-minute buckets
	 */
	protected Duration bucketizeDuration(final Duration exact) {
		final int bucketSizeMinutes = (int) DEFAULT_DURATION_INTERVAL.getStandardMinutes();
		final Period exactPeriod = exact.toPeriod();
		final int exactHours = exactPeriod.getHours();
		final int exactMinutes = exactPeriod.getMinutes();
		final Period result = new Period().withHours(exactHours).withMinutes(bucketSizeMinutes * Math.round(exactMinutes/(float)bucketSizeMinutes));
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