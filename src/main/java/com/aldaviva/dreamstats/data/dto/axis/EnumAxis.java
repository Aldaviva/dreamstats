package com.aldaviva.dreamstats.data.dto.axis;

import java.util.Arrays;
import java.util.List;


public class EnumAxis<T extends Enum<?>> extends Axis<T> {

	private final Class<T> enumClass;

	public EnumAxis(final String id, final String label, final Class<T> enumClass) {
		super(id, label);
		this.enumClass = enumClass;
	}

	@Override
	public List<T> getRange() {
		return Arrays.asList(enumClass.getEnumConstants());
	}

}
