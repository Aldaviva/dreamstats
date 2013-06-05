package com.aldaviva.dreamstats.data.dto;

import java.util.Comparator;

public class StatsCoordinate<IndependentType, DependentType> implements Comparable<StatsCoordinate<IndependentType, DependentType>> {

	@SuppressWarnings("rawtypes")
	private static final Comparator COMPARATOR = new CastingComparator<>();

	public IndependentType independentCoord;
	public DependentType dependentCoord;

	public StatsCoordinate() {
	}

	public StatsCoordinate(final IndependentType independentCoord, final DependentType dependentCoord) {
		this();
		this.independentCoord = independentCoord;
		this.dependentCoord = dependentCoord;
	}

	@Override
	public int compareTo(final StatsCoordinate<IndependentType, DependentType> o) {
		final int independentComparison = COMPARATOR.compare(independentCoord, o.independentCoord);
		if(independentComparison != 0){
			return independentComparison;
		} else {
			return COMPARATOR.compare(dependentCoord, o.dependentCoord);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dependentCoord == null) ? 0 : dependentCoord.hashCode());
		result = prime * result + ((independentCoord == null) ? 0 : independentCoord.hashCode());
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final StatsCoordinate other = (StatsCoordinate) obj;
		if (dependentCoord == null) {
			if (other.dependentCoord != null) {
				return false;
			}
		} else if (!dependentCoord.equals(other.dependentCoord)) {
			return false;
		}
		if (independentCoord == null) {
			if (other.independentCoord != null) {
				return false;
			}
		} else if (!independentCoord.equals(other.independentCoord)) {
			return false;
		}
		return true;
	}

	public static <I, D> StatsCoordinate<I, D> create(final I i, final D d){
		return new StatsCoordinate<>(i, d);
	}

}