package com.aldaviva.dreamstats.stats;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.Duration;
import org.joda.time.LocalTime;
import org.springframework.stereotype.Component;

import com.aldaviva.dreamstats.data.model.CalendarEvent;
import com.google.common.base.Predicate;

@Component
public class StartTimeVsSleepDurationCalculator extends BaseStatsCalculator<LocalTime, Duration> {

	/* Round to nearest hour */
	@Override
	public LocalTime getIndependentBucket(final LocalTime exact){
		final int hour = exact.getHourOfDay() + ((exact.getMinuteOfHour()) >= 30 ? 1 : 0);
		return new LocalTime(hour % 24, 0);
	}

	@Override
	public Duration getDependentBucket(final Duration exact) {
		return bucketizeDuration(exact);
	}

	@Override
	public Map<LocalTime, Map<Duration, Integer>> calculateStats() {
		final List<CalendarEvent> events = calendarService.findEvents(new Predicate<CalendarEvent>() {

			@Override
			public boolean apply(final CalendarEvent input) {
				return "Sleep".equals(input.getName());
			}
		});

		final Map<LocalTime, Map<Duration, Integer>> result = new HashMap<>();

		for (final CalendarEvent event : events) {
			final LocalTime independent = getIndependentBucket(event.getStart().toLocalTime());
			final Duration dependent = getDependentBucket(event.getDuration());

			incrementTableBucket(result, independent, dependent);
		}

		return result;
	}

}
