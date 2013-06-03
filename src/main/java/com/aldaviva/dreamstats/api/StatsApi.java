package com.aldaviva.dreamstats.api;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.joda.time.DateTimeConstants;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aldaviva.dreamstats.stats.DateVsAwakeDurationCalculator;
import com.aldaviva.dreamstats.stats.DateVsSleepDurationCalculator;
import com.aldaviva.dreamstats.stats.DayOfWeekVsSleepDurationCalculator;
import com.aldaviva.dreamstats.stats.DurationSinceEatingVsSleepDuration;
import com.aldaviva.dreamstats.stats.PreviousEventVsSleepDurationCalculator;
import com.aldaviva.dreamstats.stats.StartTimeVsSleepDurationCalculator;

@Component
@Path("/stats")
@Produces({ MediaType.APPLICATION_JSON })
public class StatsApi {

	@Autowired private StartTimeVsSleepDurationCalculator startTimeVsSleepDurationCalculator;
	@Autowired private DurationSinceEatingVsSleepDuration durationSinceEatingVsSleepDuration;
	@Autowired private PreviousEventVsSleepDurationCalculator previousEventVsSleepDurationCalculator;
	@Autowired private DayOfWeekVsSleepDurationCalculator dayOfWeekVsSleepDurationCalculator;
	@Autowired private DateVsSleepDurationCalculator dateVsSleepDurationCalculator;
	@Autowired private DateVsAwakeDurationCalculator dateVsAwakeDurationCalculator;

	@GET
	@Path("start-time-vs-sleep-duration")
	public Map<LocalTime, Map<Duration, Integer>> getStartTimeVsSleepDuration() {
		return startTimeVsSleepDurationCalculator.calculateStats();
	}

	@GET
	@Path("duration-since-eating-vs-sleep-duration")
	public Map<Duration, Map<Duration, Integer>> getDurationSinceEatingVsSleepDuration() {
		return durationSinceEatingVsSleepDuration.calculateStats();
	}

	@GET
	@Path("previous-event-vs-sleep-duration")
	public Map<String, Map<Duration, Integer>> getPreviousEventVsSleepDuration() {
		return previousEventVsSleepDurationCalculator.calculateStats();
	}

	/**
	 * See {@link DateTimeConstants}
	 * 1 = Monday
	 * 2 = Tuesday
	 * ...
	 * 7 = Sunday
	 */
	@GET
	@Path("day-of-week-vs-sleep-duration")
	public Map<Integer, Map<Duration, Integer>> getDayOfWeekVsSleepDuration() {
		return dayOfWeekVsSleepDurationCalculator.calculateStats();
	}

	@GET
	@Path("date-vs-sleep-duration")
	public Map<LocalDate, Map<Duration, Integer>> getDateVsSleepDuration(){
		return dateVsSleepDurationCalculator.calculateStats();
	}

	@GET
	@Path("date-vs-awake-duration")
	public Map<LocalDate, Map<Duration, Integer>> getDateVsAwakeDuration(){
		return dateVsAwakeDurationCalculator.calculateStats();
	}

}
