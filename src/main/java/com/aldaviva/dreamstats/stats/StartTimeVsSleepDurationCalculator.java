package com.aldaviva.dreamstats.stats;

import com.aldaviva.dreamstats.data.enums.EventName;
import com.aldaviva.dreamstats.data.model.CalendarEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.Duration;
import org.joda.time.LocalTime;
import org.springframework.stereotype.Component;

import com.google.common.base.Predicate;

@Component
public class StartTimeVsSleepDurationCalculator extends BaseStatsCalculator<LocalTime, Duration> {

	/* Round to nearest hour */
	@Override
	protected LocalTime getIndependentBucket(final LocalTime exact){
		final int hour = exact.getHourOfDay() + ((exact.getMinuteOfHour()) >= 30 ? 1 : 0);
		return new LocalTime(hour % 24, 0);
	}

	@Override
	protected Duration getDependentBucket(final Duration exact) {
		return bucketizeDuration(exact);
	}

	@Override
	protected Map<LocalTime, Map<Duration, Integer>> calculateStats() {
		final List<CalendarEvent> events = calendarService.findEvents(new Predicate<CalendarEvent>() {

			@Override
			public boolean apply(final CalendarEvent input) {
				return EventName.Sleep.equals(input.getName());
			}
		});

		final Map<LocalTime, Map<Duration, Integer>> result = new HashMap<>();

		for (final CalendarEvent event : events) {
			incrementTableBucket(result, event.getStart().toLocalTime(), event.getDuration());
		}

		return result;
	}

	@Override
	protected Duration getIndependentInterval() {
		return Duration.standardHours(1);
	}

	@Override
	protected Duration getDependentInterval() {
		return DEFAULT_DURATION_INTERVAL;
	}

}
