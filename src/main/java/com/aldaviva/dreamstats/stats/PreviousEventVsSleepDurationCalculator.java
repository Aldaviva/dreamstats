package com.aldaviva.dreamstats.stats;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.Duration;
import org.springframework.stereotype.Component;

import com.aldaviva.dreamstats.data.model.CalendarEvent;
import com.google.common.base.Predicates;

@Component
public class PreviousEventVsSleepDurationCalculator extends BaseStatsCalculator<String, Duration> {

	@Override
	public Map<String, Map<Duration, Integer>> calculateStats() {
		final List<CalendarEvent> events = calendarService.findEvents(Predicates.<CalendarEvent>alwaysTrue());
		final Map<String, Map<Duration, Integer>> result = new HashMap<>();

		String previousEventName = null;
		for (final CalendarEvent event : events) {
			if("Sleep".equals(event.getName())){
				if(previousEventName != null){
					incrementTableBucket(result, previousEventName, event.getDuration());
					previousEventName = null;
				}

			} else {
				previousEventName = event.getName();
			}
		}

		return result ;
	}

	@Override
	public String getIndependentBucket(final String exact) {
		return exact;
	}

	@Override
	public Duration getDependentBucket(final Duration exact) {
		return bucketizeDuration(exact);
	}

}
