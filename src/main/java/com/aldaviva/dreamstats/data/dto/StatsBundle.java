package com.aldaviva.dreamstats.data.dto;

import com.aldaviva.dreamstats.api.ApiConfig;

import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Typing;
import org.joda.time.Duration;

public class StatsBundle<IndependentType, DependentType> {

	private Map<IndependentType, Map<DependentType, Integer>> stats;
	private Duration independentInterval;
	private Duration dependentInterval;
	private Class<IndependentType> independentClass;
	private Class<DependentType> dependentClass;

	public StatsBundle(){
		super();
	}

	public StatsBundle(final Map<IndependentType, Map<DependentType, Integer>> stats, final Duration independentInterval, final Duration dependentInterval) {
		super();
		setStats(stats);
		setIndependentInterval(independentInterval);
		setDependentInterval(dependentInterval);
	}

	/**
	 * Typing.STATIC forces Jackson to use the custom key/value serializers defined in {@link ApiConfig}.
	 */
	@JsonSerialize(typing=Typing.STATIC)
	public Map<IndependentType, Map<DependentType, Integer>> getStats(){
		return this.stats;
	}

	public Duration getIndependentInterval(){
		return this.independentInterval;
	}

	public Duration getDependentInterval(){
		return this.dependentInterval;
	}

	@SuppressWarnings("unchecked")
	public void setStats(final Map<IndependentType, Map<DependentType, Integer>> stats) {
		final Entry<IndependentType, Map<DependentType, Integer>> firstEntry = stats.entrySet().iterator().next();
		this.independentClass = (Class<IndependentType>) firstEntry.getKey().getClass();
		this.dependentClass = (Class<DependentType>) firstEntry.getValue().keySet().iterator().next().getClass();
		this.stats = stats;
	}

	public void setIndependentInterval(final Duration independentInterval) {
		this.independentInterval = independentInterval;
	}

	public void setDependentInterval(final Duration dependentInterval) {
		this.dependentInterval = dependentInterval;
	}

	public Class<IndependentType> getIndependentType(){
		return independentClass;
	}

	public Class<DependentType> getDependentType(){
		return dependentClass;
	}


}
