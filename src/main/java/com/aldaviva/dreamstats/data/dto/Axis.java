package com.aldaviva.dreamstats.data.dto;

import java.util.List;
import java.util.Set;


public abstract class Axis<Type> {

	private String id;
	private List<Type> values;

	public Axis(final String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	public void setId(final String id) {
		this.id = id;
	}
//	public Duration getInterval() {
//		return interval;
//	}
//	public void setInterval(final Duration interval) {
//		this.interval = interval;
//	}
	public List<Type> getValues() {
		return values;
	}
	public void setValues(final List<Type> values) {
		this.values = values;
	}
	public final void setValuesByRange(final Type min, final Type max, final Set<Type> values){
		setValues(getRange(min, max, values));
	}

	protected abstract List<Type> getRange(Type min, Type max, Set<Type>values);

	public Class<Type> getType(){
		if(values != null && !values.isEmpty()){
			return (Class<Type>) values.iterator().next().getClass();
		} else {
			throw new RuntimeException("Cannot get type until values is populated");
		}
	}
}
