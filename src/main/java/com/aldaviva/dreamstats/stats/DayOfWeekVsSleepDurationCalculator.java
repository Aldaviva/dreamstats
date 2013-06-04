package com.aldaviva.dreamstats.stats;

import com.aldaviva.dreamstats.data.enums.EventName;
import com.aldaviva.dreamstats.data.model.CalendarEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.Duration;
import org.springframework.stereotype.Component;

import com.google.common.base.Predicate;

@Component
public class DayOfWeekVsSleepDurationCalculator extends BaseStatsCalculator<Integer, Duration> {

	@Override
	protected Map<Integer, Map<Duration, Integer>> calculateStats() {
		final List<CalendarEvent> events = calendarService.findEvents(new Predicate<CalendarEvent>(){
			@Override
			public boolean apply(final CalendarEvent input) {
				return EventName.Sleep.equals(input.getName());
			}
		});

		final Map<Integer, Map<Duration, Integer>> results = new HashMap<>();

		for (final CalendarEvent event : events) {
			incrementTableBucket(results, event.getEnd().getDayOfWeek(), event.getDuration());
		}

		return results ;
	}

	@Override
	protected Integer getIndependentBucket(final Integer exact) {
		return exact;
	}

	@Override
	protected Duration getDependentBucket(final Duration exact) {
		return bucketizeDuration(exact);
	}

	@Override
	protected Duration getIndependentInterval() {
		return null; //each day of the week gets its own column, gaps are not necessary
	}

	@Override
	protected Duration getDependentInterval() {
		return DEFAULT_DURATION_INTERVAL;
	}

}
