package com.aldaviva.dreamstats.data.dto.axis;

import com.aldaviva.dreamstats.stats.BaseStatsCalculator;

public class SleepDurationAxis extends DurationAxis {

	public SleepDurationAxis(){
		super("sleep-duration", BaseStatsCalculator.DEFAULT_DURATION_INTERVAL);
	}
}