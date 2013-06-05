package com.aldaviva.dreamstats.data.dto.axis;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.aldaviva.dreamstats.data.dto.CastingComparator;


public abstract class Axis<Type> {

	private static final CastingComparator<Object> CASTING_COMPARATOR = new CastingComparator<>();

	private String id;
	protected final Set<Type> values;

	public Axis(final String id) {
		this.id = id;
		values = new TreeSet<>();
	}

	public String getId() {
		return id;
	}
	public void setId(final String id) {
		this.id = id;
	}

	public void addValue(final Type value){
		values.add(value);
	}

	public abstract List<Type> getRange();

	public Type getMin(){
		return Collections.min(values, CASTING_COMPARATOR);
	}
	public Type getMax(){
		return Collections.max(values, CASTING_COMPARATOR);
	}

	public Class<Type> getType(){
		if(values != null && !values.isEmpty()){
			return (Class<Type>) values.iterator().next().getClass();
		} else {
			throw new RuntimeException("Cannot get type until values is populated");
		}
	}
}
