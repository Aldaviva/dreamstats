package com.aldaviva.dreamstats.data.dto;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Duration;

public class DurationAxis extends Axis<Duration> {

	private final Duration interval;

	public DurationAxis(final String id, final Duration interval) {
		super(id);
		this.interval = interval;
	}

	@Override
	public List<Duration> getRange() {
		final List<Duration> range = new ArrayList<>();
		final Duration max = getMax();
		for(Duration curr = getMin(); curr.isShorterThan(max); curr = curr.plus(interval)){
			range.add(curr);
		}
		return range;
	}
}