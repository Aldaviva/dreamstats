package com.aldaviva.dreamstats.stats;

import java.util.List;

import org.joda.time.Duration;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.springframework.stereotype.Component;

import com.aldaviva.dreamstats.data.dto.axis.Axis;
import com.aldaviva.dreamstats.data.dto.axis.LocalTimeAxis;
import com.aldaviva.dreamstats.data.dto.axis.SleepDurationAxis;
import com.aldaviva.dreamstats.data.dto.table.StatsTable;
import com.aldaviva.dreamstats.data.enums.EventName;
import com.aldaviva.dreamstats.data.model.CalendarEvent;
import com.google.common.base.Predicate;

@Component
public class StartTimeVsSleepDurationCalculator extends BaseStatsCalculator<LocalTime, Duration> {

	@Override
	protected void calculateStats(final StatsTable<LocalTime, Duration> results) {
		final List<CalendarEvent> events = calendarService.findEvents(new Predicate<CalendarEvent>() {

			@Override
			public boolean apply(final CalendarEvent input) {
				return EventName.Sleep.equals(input.getName());
			}
		});

		for (final CalendarEvent event : events) {
			incrementTableBucket(results, event.getStart().toLocalTime(), event.getDuration());
		}
	}

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
	protected Axis<LocalTime> getIndependentAxis() {
		return new LocalTimeAxis("start-time", Period.hours(1));
	}

	@Override
	protected Axis<Duration> getDependentAxis() {
		return new SleepDurationAxis();
	}

}
