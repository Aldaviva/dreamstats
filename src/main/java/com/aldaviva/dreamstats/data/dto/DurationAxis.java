package com.aldaviva.dreamstats.data.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.joda.time.Duration;

public class DurationAxis extends Axis<Duration> {

	private final Duration interval;

	public DurationAxis(final String id, final Duration interval) {
		super(id);
		this.interval = interval;
	}

	@Override
	protected List<Duration> getRange(final Duration min, final Duration max, final Set<Duration> values) {
		final List<Duration> range = new ArrayList<>();
		for(Duration curr = min; curr.isShorterThan(max); curr = curr.plus(interval)){
			range.add(curr);
		}
		return range;
	}
}