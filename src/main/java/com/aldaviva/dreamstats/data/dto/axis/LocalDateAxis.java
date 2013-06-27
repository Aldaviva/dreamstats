package com.aldaviva.dreamstats.data.dto.axis;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

public class LocalDateAxis extends Axis<LocalDate> {

	public LocalDateAxis(final String id, final String label) {
		super(id, label);
	}

	@Override
	public List<LocalDate> getRange() {
		final List<LocalDate> result = new ArrayList<>();
		final LocalDate max = getMax();
		for(LocalDate curr = getMin(); curr.isBefore(max) || curr.isEqual(max); curr = curr.plusDays(1)){
			result.add(curr);
		}
		return result;
	}

}
