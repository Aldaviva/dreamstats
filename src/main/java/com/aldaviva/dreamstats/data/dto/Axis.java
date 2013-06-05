package com.aldaviva.dreamstats.data.dto;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


public abstract class Axis<Type> {

	private static final CastingComparator<Object> CASTING_COMPARATOR = new CastingComparator<>();

	private String id;
//	private List<Type> values;
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

//	public Duration getInterval() {
//		return interval;
//	}
//	public void setInterval(final Duration interval) {
//		this.interval = interval;
//	}
//	public List<Type> getValues() {
//		return values;
//	}
//	public void setValues(final List<Type> values) {
//		this.values = values;
//	}
//	public final void setValuesByRange(final Type min, final Type max, final Set<Type> values){
//		setValues(getRange(min, max, values));
//	}

	public abstract List<Type> getRange(/*Type min, Type max, Set<Type> values*/);

	protected Type getMin(){
		return Collections.min(values, CASTING_COMPARATOR);
	}
	protected Type getMax(){
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
