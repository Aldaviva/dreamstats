package com.aldaviva.dreamstats.data.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StringAxis extends Axis<String> {

	public StringAxis(final String id) {
		super(id);
	}

	@Override
	protected List<String> getRange(final String min, final String max, final Set<String> values) {
		return new ArrayList<>(values);
	}

}
