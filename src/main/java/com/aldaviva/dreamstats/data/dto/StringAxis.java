package com.aldaviva.dreamstats.data.dto;

import java.util.ArrayList;
import java.util.List;

public class StringAxis extends Axis<String> {

	public StringAxis(final String id) {
		super(id);
	}

	@Override
	public List<String> getRange() {
		return new ArrayList<>(values);
	}

}
