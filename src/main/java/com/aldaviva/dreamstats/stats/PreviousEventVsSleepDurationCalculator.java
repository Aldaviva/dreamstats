package com.aldaviva.dreamstats.stats;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.Duration;
import org.springframework.stereotype.Component;

import com.aldaviva.dreamstats.data.dto.Axis;
import com.aldaviva.dreamstats.data.dto.SleepDurationAxis;
import com.aldaviva.dreamstats.data.dto.StringAxis;
import com.aldaviva.dreamstats.data.enums.EventName;
import com.aldaviva.dreamstats.data.model.CalendarEvent;
import com.google.common.base.Predicates;

@Component
public class PreviousEventVsSleepDurationCalculator extends BaseStatsCalculator<String, Duration> {

	@Override
	protected Map<String, Map<Duration, Integer>> calculateStats() {
		final List<CalendarEvent> events = calendarService.findEvents(Predicates.<CalendarEvent>alwaysTrue());
		final Map<String, Map<Duration, Integer>> result = new HashMap<>();

		EventName previousEventName = null;
		for (final CalendarEvent event : events) {
			if(EventName.Sleep.equals(event.getName())){
				if(previousEventName != null){
					incrementTableBucket(result, previousEventName.name(), event.getDuration());
					previousEventName = null;
				}

			} else {
				previousEventName = event.getName();
			}
		}

		return result;
	}

	@Override
	protected String getIndependentBucket(final String exact) {
		return exact;
	}

	@Override
	protected Duration getDependentBucket(final Duration exact) {
		return bucketizeDuration(exact);
	}

	@Override
	protected Axis<String> getIndependentAxis() {
		return new StringAxis("previous-event"); //each string gets its own column, gaps don't matter
	}

	@Override
	protected Axis<Duration> getDependentAxis() {
		return new SleepDurationAxis();
	}

}
