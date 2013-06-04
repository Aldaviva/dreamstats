package com.aldaviva.dreamstats.data.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;

public class LocalDateAxis extends Axis<LocalDate> {

	public LocalDateAxis(final String id) {
		super(id);
	}

	@Override
	protected List<LocalDate> getRange(final LocalDate min, final LocalDate max, final Set<LocalDate> values) {
		final List<LocalDate> result = new ArrayList<>();
		for(LocalDate curr = min; curr.isBefore(max); curr = curr.plusDays(1)){
			result.add(curr);
		}
		return result;
	}

}
