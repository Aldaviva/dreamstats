package com.aldaviva.dreamstats.data.dto.axis;

import com.aldaviva.dreamstats.stats.BaseStatsCalculator;

public class SleepDurationAxis extends DurationAxis {

	public SleepDurationAxis(){
		super("sleep-duration", "Sleep Duration", BaseStatsCalculator.DEFAULT_DURATION_INTERVAL);
	}
}