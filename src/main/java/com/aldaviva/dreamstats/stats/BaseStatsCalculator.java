package com.aldaviva.dreamstats.stats;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;

import com.aldaviva.dreamstats.data.dto.Axis;
import com.aldaviva.dreamstats.data.dto.StatsTable;
import com.aldaviva.dreamstats.data.model.StatsBundle;
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
		statsBundle.setIndependentAxis(getIndependentAxis());
		statsBundle.setDependentAxis(getDependentAxis());
		calculateStats(statsBundle.getStats());

		return statsBundle;
	}

	protected abstract void calculateStats(StatsTable<IndependentType, DependentType> statsTable);

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

	protected void incrementTableBucket(final StatsTable<IndependentType, DependentType> table, final IndependentType independentExact, final DependentType dependentExact) {
		final IndependentType independentBucket = getIndependentBucket(independentExact);
		final DependentType dependentBucket = getDependentBucket(dependentExact);

		table.increment(independentBucket, dependentBucket);
		table.getBundle().getIndependentAxis().addValue(independentBucket);
		table.getBundle().getDependentAxis().addValue(dependentBucket);
	}
}