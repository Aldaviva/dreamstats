package com.aldaviva.dreamstats.data.dto;

import java.util.Comparator;

final class CastingComparator<T> implements Comparator<T> {

	@SuppressWarnings("unchecked")
	@Override
	public int compare(final T o1, final T o2) {
		return ((Comparable<T>) o1).compareTo(o2);
	}

}