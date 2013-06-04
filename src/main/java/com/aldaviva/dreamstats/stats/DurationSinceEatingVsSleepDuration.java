package com.aldaviva.dreamstats.stats;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.stereotype.Component;

import com.aldaviva.dreamstats.data.dto.Axis;
import com.aldaviva.dreamstats.data.dto.DurationAxis;
import com.aldaviva.dreamstats.data.dto.SleepDurationAxis;
import com.aldaviva.dreamstats.data.enums.EventName;
import com.aldaviva.dreamstats.data.model.CalendarEvent;
import com.google.common.base.Predicate;

@Component
public class DurationSinceEatingVsSleepDuration extends BaseStatsCalculator<Duration, Duration> {

	@Override
	protected Map<Duration, Map<Duration, Integer>> calculateStats() {
		final List<CalendarEvent> events = calendarService.findEvents(new Predicate<CalendarEvent>() {
			@Override
			public boolean apply(final CalendarEvent input) {
				final EventName name = input.getName();
				return EventName.Sleep.equals(name) || EventName.Food.equals(name);
			}
		});

		final Map<Duration, Map<Duration, Integer>> result = new HashMap<>();

		DateTime mostRecentFoodEnd = null;

		for (final CalendarEvent event : events) {
			if(EventName.Food.equals(event.getName())){
				mostRecentFoodEnd = event.getEnd();

			} else if(EventName.Sleep.equals(event.getName())){
				if(mostRecentFoodEnd != null){
					final Duration timeBetweenFoodEndingAndSleepStarting = new Duration(mostRecentFoodEnd, event.getStart());
					incrementTableBucket(result, timeBetweenFoodEndingAndSleepStarting, event.getDuration());
				}
			}
		}

		return result;
	}

	@Override
	protected Duration getIndependentBucket(final Duration exact) {
		return bucketizeDuration(exact);
	}

	@Override
	protected Duration getDependentBucket(final Duration exact) {
		return bucketizeDuration(exact);
	}

	@Override
	protected Axis<Duration> getIndependentAxis() {
		return new DurationAxis("duration-since-eating", DEFAULT_DURATION_INTERVAL);
	}

	@Override
	protected Axis<Duration> getDependentAxis() {
		return new SleepDurationAxis();
	}

}
