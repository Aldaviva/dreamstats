package com.aldaviva.dreamstats.data.dto;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Typing;

import com.aldaviva.dreamstats.api.ApiConfig;

public class StatsBundle<IndependentType, DependentType> {

	private Map<IndependentType, Map<DependentType, Integer>> stats;
	private Axes<IndependentType, DependentType> axes;

	public StatsBundle(){
		super();
		axes = new Axes<>();
	}

	public StatsBundle(final Map<IndependentType, Map<DependentType, Integer>> stats, final Axis<IndependentType> independentAxis, final Axis<DependentType> dependentAxis) {
		this();
		this.stats = stats;
		axes.independentAxis = independentAxis;
		axes.dependentAxis = dependentAxis;
	}

	/**
	 * Typing.STATIC forces Jackson to use the custom key/value serializers defined in {@link ApiConfig}.
	 */
	@JsonSerialize(typing=Typing.STATIC)
	public Map<IndependentType, Map<DependentType, Integer>> getStats(){
		return this.stats;
	}

	public void setStats(final Map<IndependentType, Map<DependentType, Integer>> stats) {
		this.stats = stats;
	}

	public Axes<IndependentType, DependentType> getAxes() {
		return axes;
	}

	public void setAxes(final Axes<IndependentType, DependentType> axes) {
		this.axes = axes;
	}

	public void updateAxesFromStats() {
		final Comparator<IndependentType> independentComparator = new CastingComparator<>();
		final Comparator<DependentType> dependentComparator = new CastingComparator<>();

		final Set<IndependentType> sparseIndependentValues = stats.keySet();
		final Set<DependentType> sparseDependentValues = new HashSet<>();
		for(final Entry<IndependentType, Map<DependentType, Integer>> entry : stats.entrySet()){
			sparseDependentValues.addAll(entry.getValue().keySet());
		}

		axes.independentAxis.setValuesByRange(Collections.min(sparseIndependentValues, independentComparator), Collections.max(sparseIndependentValues, independentComparator), sparseIndependentValues);
		axes.dependentAxis.setValuesByRange(Collections.min(sparseDependentValues, dependentComparator), Collections.max(sparseDependentValues, dependentComparator), sparseDependentValues);

//
//		//Get sparse keys from both axes
//		final SortedSet<IndependentType> sparseIndependentValues = new TreeSet<>(stats.keySet());
//		final Set<DependentType> dependentValuesSet = new HashSet<>();
//		for(final Entry<IndependentType, Map<DependentType, Integer>> entry : stats.entrySet()){
//			dependentValuesSet.addAll(entry.getValue().keySet());
//		}
//		final SortedSet<DependentType> sparseDependentValues = new TreeSet<>(dependentValuesSet);
//
//		IndependentType minIndependentValue = sparseIndependentValues.first();
//		IndependentType maxIndependentValue = sparseIndependentValues.last();
//		DependentType minDependentValue = sparseDependentValues.first();
//		DependentType maxDependentValue = sparseDependentValues.last();
//
//		IndependentType independentCursor = minIndependentValue;
//		while(independentComparator.compare(independentCursor, maxIndependentValue) < 0){
//			sparseIndependentValues.add(independentCursor);
//			independentCursor = axes.independentAxis.nextBucket(independentCursor);
//		}

//		//sort sparse keys
//		Collections.sort(sparseIndependentValues, independentComparator);
//		Collections.sort(sparseDependentValues, dependentComparator);
//
//		//fill in gaps between keys
//		PeekingIterator<IndependentType> independentIterator = Iterators.peekingIterator(sparseIndependentValues.iterator());
//		IndependentType prev, next;
//		while(independentIterator.hasNext()){
//			next = independentIterator.peek();
//			independentIterator.
//		}
//
//
//		axes.independentAxis.setValues(sparseIndependentValues);
//		axes.dependentAxis.setValues(sparseDependentValues);
	}

	private static final class CastingComparator<T> implements Comparator<T> {

		@SuppressWarnings("unchecked")
		@Override
		public int compare(final T o1, final T o2) {
			return ((Comparable<T>) o1).compareTo(o2);
		}

	}

	public static final class Axes<I, D> {
		public Axis<I> independentAxis;
		public Axis<D> dependentAxis;
	}
}
