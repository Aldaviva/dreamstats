package com.aldaviva.dreamstats.stats;

import java.util.List;

import org.joda.time.Duration;
import org.springframework.stereotype.Component;

import com.aldaviva.dreamstats.data.dto.axis.Axis;
import com.aldaviva.dreamstats.data.dto.axis.SleepDurationAxis;
import com.aldaviva.dreamstats.data.dto.axis.StringAxis;
import com.aldaviva.dreamstats.data.dto.table.StatsTable;
import com.aldaviva.dreamstats.data.enums.EventName;
import com.aldaviva.dreamstats.data.model.CalendarEvent;
import com.google.common.base.Predicates;

@Component
public class PreviousEventVsSleepDurationCalculator extends BaseStatsCalculator<String, Duration> {

	@Override
	protected void calculateStats(final StatsTable<String, Duration> results) {
		final List<CalendarEvent> events = calendarService.findEvents(Predicates.<CalendarEvent>alwaysTrue());

		EventName previousEventName = null;
		for (final CalendarEvent event : events) {
			if(EventName.Sleep.equals(event.getName())){
				if(previousEventName != null){
					incrementTableBucket(results, previousEventName.name(), event.getDuration());
					previousEventName = null;
				}

			} else {
				previousEventName = event.getName();
			}
		}
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
