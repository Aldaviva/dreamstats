package com.aldaviva.dreamstats.data.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;

public class IntegerAxis extends Axis<Integer> {

	public IntegerAxis(final String id) {
		super(id);
	}

	@Override
	protected List<Integer> getRange(final Integer min, final Integer max, final Set<Integer> values) {
		return new ArrayList<>(ContiguousSet.create(Range.closed(min, max), DiscreteDomain.integers()));
	}

}
