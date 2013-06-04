package com.aldaviva.dreamstats.stats;

import com.aldaviva.dreamstats.data.enums.EventName;
import com.aldaviva.dreamstats.data.model.CalendarEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import com.google.common.base.Predicate;

@Component
public class DateVsSleepDurationCalculator extends BaseStatsCalculator<LocalDate, Duration> {

	@Override
	protected Map<LocalDate, Map<Duration, Integer>> calculateStats() {
		final List<CalendarEvent> events = calendarService.findEvents(new Predicate<CalendarEvent>() {
			@Override
			public boolean apply(final CalendarEvent input) {
				return EventName.Sleep.equals(input.getName());
			}
		});

		final Map<LocalDate, Map<Duration, Integer>> results = new HashMap<>();

		for (final CalendarEvent event : events) {
			incrementTableBucket(results, new LocalDate(event.getEnd()), event.getDuration());
		}

		return results ;
	}

	@Override
	protected LocalDate getIndependentBucket(final LocalDate exact) {
		return exact;
	}

	@Override
	protected Duration getDependentBucket(final Duration exact) {
		return bucketizeDuration(exact);
	}

	@Override
	protected Duration getIndependentInterval() {
		return Duration.standardDays(1);
	}

	@Override
	protected Duration getDependentInterval() {
		return DEFAULT_DURATION_INTERVAL;
	}

}
