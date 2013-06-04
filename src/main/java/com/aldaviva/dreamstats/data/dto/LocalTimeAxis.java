package com.aldaviva.dreamstats.data.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalTime;
import org.joda.time.Period;

public class LocalTimeAxis extends Axis<LocalTime> {

	private final Period interval;

	public LocalTimeAxis(final String id, final Period interval) {
		super(id);
		this.interval = interval;
	}

	@Override
	protected List<LocalTime> getRange(final LocalTime min, final LocalTime max, final Set<LocalTime> values) {
		final List<LocalTime> result = new ArrayList<>();
		for(LocalTime curr = min; curr.isBefore(max); curr = curr.plus(interval)){
			result.add(curr);
		}
		return result;
	}

}
