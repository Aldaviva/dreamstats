package com.aldaviva.dreamstats.stats;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import com.aldaviva.dreamstats.data.dto.Axis;
import com.aldaviva.dreamstats.data.dto.DurationAxis;
import com.aldaviva.dreamstats.data.dto.LocalDateAxis;
import com.aldaviva.dreamstats.data.enums.EventName;
import com.aldaviva.dreamstats.data.model.CalendarEvent;
import com.google.common.base.Predicate;

@Component
public class DateVsAwakeDurationCalculator extends BaseStatsCalculator<LocalDate, Duration> {

	@Override
	protected Map<LocalDate, Map<Duration, Integer>> calculateStats() {
		final List<CalendarEvent> events = calendarService.findEvents(new Predicate<CalendarEvent>() {
			@Override
			public boolean apply(final CalendarEvent input) {
				return EventName.Sleep.equals(input.getName());
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
	protected LocalDate getIndependentBucket(final LocalDate exact) {
		return exact;
	}

	@Override
	protected Duration getDependentBucket(final Duration exact) {
		return bucketizeDuration(exact);
	}

	@Override
	protected Axis<LocalDate> getIndependentAxis() {
		return new LocalDateAxis("date");
	}

	@Override
	protected Axis<Duration> getDependentAxis() {
		return new DurationAxis("awake-duration", DEFAULT_DURATION_INTERVAL);
	}

}
