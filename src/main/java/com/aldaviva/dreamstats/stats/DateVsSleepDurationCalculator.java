package com.aldaviva.dreamstats.stats;

import com.aldaviva.dreamstats.data.dto.axis.Axis;
import com.aldaviva.dreamstats.data.dto.axis.LocalDateAxis;
import com.aldaviva.dreamstats.data.dto.axis.SleepDurationAxis;
import com.aldaviva.dreamstats.data.dto.table.StatsTable;
import com.aldaviva.dreamstats.data.enums.EventName;
import com.aldaviva.dreamstats.data.model.CalendarEvent;

import java.util.List;

import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import com.google.common.base.Predicate;

@Component
public class DateVsSleepDurationCalculator extends BaseStatsCalculator<LocalDate, Duration> {

	@Override
	protected void calculateStats(final StatsTable<LocalDate, Duration> results) {
		final List<CalendarEvent> events = calendarService.findEvents(new Predicate<CalendarEvent>() {
			@Override
			public boolean apply(final CalendarEvent input) {
				return EventName.Sleep.equals(input.getName());
			}
		});

		for (final CalendarEvent event : events) {
			incrementTableBucket(results, new LocalDate(event.getEnd()), event.getDuration());
		}
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
		return new LocalDateAxis("date", "Date");
	}

	@Override
	protected Axis<Duration> getDependentAxis() {
		return new SleepDurationAxis();
	}
}
