package com.aldaviva.dreamstats.api;

import java.io.IOException;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.joda.time.Duration;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

	@GET
	@Path("start-time-vs-sleep-duration")
	public Map<LocalTime, Map<Duration, Integer>> getStartTimeVsSleepDuration() throws JsonGenerationException, JsonMappingException, IOException{
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
}
