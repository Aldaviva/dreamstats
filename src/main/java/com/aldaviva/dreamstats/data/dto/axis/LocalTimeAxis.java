package com.aldaviva.dreamstats.data.dto.axis;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalTime;
import org.joda.time.Period;

public class LocalTimeAxis extends Axis<LocalTime> {

	private final Period interval;

	public LocalTimeAxis(final String id, final String label, final Period interval) {
		super(id, label);
		this.interval = interval;
	}

	@Override
	public List<LocalTime> getRange() {
		final List<LocalTime> result = new ArrayList<>();
		final LocalTime max = getMax();
		final LocalTime min = getMin();
		//this loops forever if the interval is big enough to roll over the localtime when added to max
		for(
			LocalTime curr = min,
				prev = null;
			(curr.isBefore(max) || curr.isEqual(max)) && (prev == null || prev.isBefore(curr));
			prev = curr,
				curr = curr.plus(interval)
		){
			result.add(curr);
		}
		return result;
	}

}
