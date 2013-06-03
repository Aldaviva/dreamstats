package com.aldaviva.dreamstats.stats;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import com.aldaviva.dreamstats.data.model.CalendarEvent;
import com.google.common.base.Predicate;

@Component
public class DateVsAwakeDurationCalculator extends BaseStatsCalculator<LocalDate, Duration> {

	@Override
	public Map<LocalDate, Map<Duration, Integer>> calculateStats() {
		final List<CalendarEvent> events = calendarService.findEvents(new Predicate<CalendarEvent>() {
			@Override
			public boolean apply(final CalendarEvent input) {
				return "Sleep".equals(input.getName());
			}
		});

		final Map<LocalDate, Map<Duration, Integer>> results = new HashMap<>();

		CalendarEvent previousSleepEvent = null;
		for (final CalendarEvent event : events) {
			if(previousSleepEvent != null){
				final LocalDate independentExact = new LocalDate(event.getEnd());
				final Duration dependentExact = new Duration(previousSleepEvent.getEnd(), event.getStart());
				incrementTableBucket(results, independentExact, dependentExact);
			}
			previousSleepEvent = event;
		}

		return results;
	}

	@Override
	public LocalDate getIndependentBucket(final LocalDate exact) {
		return exact;
	}

	@Override
	public Duration getDependentBucket(final Duration exact) {
		return bucketizeDuration(exact);
	}

}
