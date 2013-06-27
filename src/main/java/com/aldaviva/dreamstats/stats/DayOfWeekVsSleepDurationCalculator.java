package com.aldaviva.dreamstats.stats;

import com.aldaviva.dreamstats.data.dto.axis.Axis;
import com.aldaviva.dreamstats.data.dto.axis.EnumAxis;
import com.aldaviva.dreamstats.data.dto.axis.SleepDurationAxis;
import com.aldaviva.dreamstats.data.dto.table.StatsTable;
import com.aldaviva.dreamstats.data.enums.DayOfWeek;
import com.aldaviva.dreamstats.data.enums.EventName;
import com.aldaviva.dreamstats.data.model.CalendarEvent;

import java.util.List;

import org.joda.time.Duration;
import org.springframework.stereotype.Component;

import com.google.common.base.Predicate;

@Component
public class DayOfWeekVsSleepDurationCalculator extends BaseStatsCalculator<DayOfWeek, Duration> {

	@Override
	protected void calculateStats(final StatsTable<DayOfWeek, Duration> results) {
		final List<CalendarEvent> events = calendarService.findEvents(new Predicate<CalendarEvent>(){
			@Override
			public boolean apply(final CalendarEvent input) {
				return EventName.Sleep.equals(input.getName());
			}
		});

		for (final CalendarEvent event : events) {
			final DayOfWeek dayOfWeek = DayOfWeek.fromJoda(event.getEnd().getDayOfWeek());
			incrementTableBucket(results, dayOfWeek, event.getDuration());
		}
	}

	@Override
	protected DayOfWeek getIndependentBucket(final DayOfWeek exact) {
		return exact;
	}

	@Override
	protected Duration getDependentBucket(final Duration exact) {
		return bucketizeDuration(exact);
	}

	@Override
	protected Axis<DayOfWeek> getIndependentAxis() {
		return new EnumAxis<>("day-of-week", "Day of Week", DayOfWeek.class);
	}

	@Override
	protected Axis<Duration> getDependentAxis() {
		return new SleepDurationAxis();
	}

}
