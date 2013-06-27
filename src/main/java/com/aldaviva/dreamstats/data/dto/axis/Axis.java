package com.aldaviva.dreamstats.data.dto.axis;

import com.aldaviva.dreamstats.data.dto.CastingComparator;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Axis<Type> {

	private static final Logger LOGGER = LoggerFactory.getLogger(Axis.class);

	private static final CastingComparator<Object> CASTING_COMPARATOR = new CastingComparator<>();

	private String id;
	private String label;
	protected final Set<Type> values;

	public Axis(final String id, final String label) {
		this.id = id;
		this.label = label;
		values = new TreeSet<>();
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	public void addValue(final Type value) {
		values.add(value);
		LOGGER.debug("Axis {} was given value {} {}", label, value.getClass().getSimpleName(), value);
	}

	public abstract List<Type> getRange();

	public Type getMin() {
		return Collections.min(values, CASTING_COMPARATOR);
	}

	public Type getMax() {
		return Collections.max(values, CASTING_COMPARATOR);
	}

	public Class<Type> getType() {
		if (values != null && !values.isEmpty()) {
			return (Class<Type>) values.iterator().next().getClass();
		} else {
			throw new RuntimeException("Cannot get type until values is populated");
		}
	}
}
