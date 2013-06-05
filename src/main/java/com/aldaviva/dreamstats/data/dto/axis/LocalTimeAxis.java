package com.aldaviva.dreamstats.data.dto.axis;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalTime;
import org.joda.time.Period;

public class LocalTimeAxis extends Axis<LocalTime> {

	private final Period interval;

	public LocalTimeAxis(final String id, final Period interval) {
		super(id);
		this.interval = interval;
	}

	@Override
	public List<LocalTime> getRange() {
		final List<LocalTime> result = new ArrayList<>();
		final LocalTime max = getMax();
		for(LocalTime curr = getMin(); curr.isBefore(max); curr = curr.plus(interval)){
			result.add(curr);
		}
		return result;
	}

}
