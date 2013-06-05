package com.aldaviva.dreamstats.data.dto.axis;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;

public class IntegerAxis extends Axis<Integer> {

	public IntegerAxis(final String id) {
		super(id);
	}

	@Override
	public List<Integer> getRange() {
		return new ArrayList<>(ContiguousSet.create(Range.closed(getMin(), getMax()), DiscreteDomain.integers()));
	}

}
