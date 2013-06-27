package com.aldaviva.dreamstats.data.dto.axis;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DurationAxis extends Axis<Duration> {

	private static final Logger LOGGER = LoggerFactory.getLogger(DurationAxis.class);

	private final Duration interval;

	public DurationAxis(final String id, final String label, final Duration interval) {
		super(id, label);
		this.interval = interval;
	}

	@Override
	public List<Duration> getRange() {
		final List<Duration> range = new ArrayList<>();
		final Duration max = getMax();
		final Duration min = getMin();
		LOGGER.debug("Generating range of values for duration axis {} based on bounds [{}, {}]", getLabel(), min.getMillis()/1000, max.getMillis()/1000);
		for(Duration curr = min; curr.isShorterThan(max) || curr.isEqual(max); curr = curr.plus(interval)){
			range.add(curr);
		}
		return range;
	}
}