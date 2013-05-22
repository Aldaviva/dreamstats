package com.aldaviva.dreamstats.stats;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.stereotype.Component;

import com.aldaviva.dreamstats.data.model.CalendarEvent;
import com.google.common.base.Predicate;

@Component
public class DurationSinceEatingVsSleepDuration extends BaseStatsCalculator<Duration, Duration> {

	@Override
	public Map<Duration, Map<Duration, Integer>> calculateStats() {
		final List<CalendarEvent> events = calendarService.findEvents(new Predicate<CalendarEvent>() {
			@Override
			public boolean apply(final CalendarEvent input) {
				final String name = input.getName();
				return "Sleep".equals(name) || "Food".equals(name);
			}
		});

		final Map<Duration, Map<Duration, Integer>> result = new HashMap<>();

		DateTime mostRecentFoodEnd = null;

		for (final CalendarEvent event : events) {
			if("Food".equals(event.getName())){
				mostRecentFoodEnd = event.getEnd();

			} else if("Sleep".equals(event.getName())){
				if(mostRecentFoodEnd != null){
					final Duration timeBetweenFoodEndingAndSleepStarting = new Duration(mostRecentFoodEnd, event.getStart());
					incrementTableBucket(result, timeBetweenFoodEndingAndSleepStarting, event.getDuration());
				}
			}
		}

		return result;
	}

	@Override
	public Duration getIndependentBucket(final Duration exact) {
		return bucketizeDuration(exact);
	}

	@Override
	public Duration getDependentBucket(final Duration exact) {
		return bucketizeDuration(exact);
	}

}
