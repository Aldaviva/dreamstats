package com.aldaviva.dreamstats.stats;

import java.util.List;

import org.joda.time.Duration;
import org.springframework.stereotype.Component;

import com.aldaviva.dreamstats.data.dto.Axis;
import com.aldaviva.dreamstats.data.dto.IntegerAxis;
import com.aldaviva.dreamstats.data.dto.SleepDurationAxis;
import com.aldaviva.dreamstats.data.dto.StatsTable;
import com.aldaviva.dreamstats.data.enums.EventName;
import com.aldaviva.dreamstats.data.model.CalendarEvent;
import com.google.common.base.Predicate;

@Component
public class DayOfWeekVsSleepDurationCalculator extends BaseStatsCalculator<Integer, Duration> {

	@Override
	protected void calculateStats(final StatsTable<Integer, Duration> results) {
		final List<CalendarEvent> events = calendarService.findEvents(new Predicate<CalendarEvent>(){
			@Override
			public boolean apply(final CalendarEvent input) {
				return EventName.Sleep.equals(input.getName());
			}
		});

		for (final CalendarEvent event : events) {
			incrementTableBucket(results, event.getEnd().getDayOfWeek(), event.getDuration());
		}
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
	protected Axis<Integer> getIndependentAxis() {
		return new IntegerAxis("day-of-week"); //each day of the week gets its own column, gaps are not necessary
	}

	@Override
	protected Axis<Duration> getDependentAxis() {
		return new SleepDurationAxis();
	}

}
