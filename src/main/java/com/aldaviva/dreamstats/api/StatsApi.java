package com.aldaviva.dreamstats.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.joda.time.DateTimeConstants;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aldaviva.dreamstats.data.dto.StatsBundle;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(StatsApi.class);

	@Autowired private StartTimeVsSleepDurationCalculator startTimeVsSleepDurationCalculator;
	@Autowired private DurationSinceEatingVsSleepDuration durationSinceEatingVsSleepDuration;
	@Autowired private PreviousEventVsSleepDurationCalculator previousEventVsSleepDurationCalculator;
	@Autowired private DayOfWeekVsSleepDurationCalculator dayOfWeekVsSleepDurationCalculator;
	@Autowired private DateVsSleepDurationCalculator dateVsSleepDurationCalculator;
	@Autowired private DateVsAwakeDurationCalculator dateVsAwakeDurationCalculator;

	public StatsApi(){
		LOGGER.info("/api/stats ready");
	}

	@GET
	@Path("start-time-vs-sleep-duration")
	public StatsBundle<LocalTime, Duration> getStartTimeVsSleepDuration() {
		return startTimeVsSleepDurationCalculator.getStatsBundle();
	}

	@GET
	@Path("duration-since-eating-vs-sleep-duration")
	public StatsBundle<Duration, Duration> getDurationSinceEatingVsSleepDuration() {
		return durationSinceEatingVsSleepDuration.getStatsBundle();
	}

	@GET
	@Path("previous-event-vs-sleep-duration")
	public StatsBundle<String, Duration> getPreviousEventVsSleepDuration() {
		return previousEventVsSleepDurationCalculator.getStatsBundle();
	}

	/**
	 * Day of week: see {@link DateTimeConstants}
	 * 1 = Monday
	 * 2 = Tuesday
	 * ...
	 * 7 = Sunday
	 */
	@GET
	@Path("day-of-week-vs-sleep-duration")
	public StatsBundle<Integer, Duration> getDayOfWeekVsSleepDuration() {
		return dayOfWeekVsSleepDurationCalculator.getStatsBundle();
	}

	@GET
	@Path("date-vs-sleep-duration")
	public StatsBundle<LocalDate, Duration> getDateVsSleepDuration(){
		return dateVsSleepDurationCalculator.getStatsBundle();
	}

	@GET
	@Path("date-vs-awake-duration")
	public StatsBundle<LocalDate, Duration> getDateVsAwakeDuration(){
		return dateVsAwakeDurationCalculator.getStatsBundle();
	}

}
