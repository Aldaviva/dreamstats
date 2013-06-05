package com.aldaviva.dreamstats.data.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.aldaviva.dreamstats.api.ApiConfig;
import com.aldaviva.dreamstats.api.marshal.StatsBundleSerializer;
import com.aldaviva.dreamstats.api.marshal.TripleArraySerializer;
import com.aldaviva.dreamstats.data.dto.axis.Axis;
import com.aldaviva.dreamstats.data.dto.table.MultisetStatsTable;
import com.aldaviva.dreamstats.data.dto.table.StatsCoordinate;
import com.aldaviva.dreamstats.data.dto.table.StatsTable;
import com.google.common.collect.Multiset;

@JsonSerialize(using=StatsBundleSerializer.class)
public class StatsBundle<IndependentType, DependentType> {

	private final MultisetStatsTable<IndependentType, DependentType> stats;

	private Axis<IndependentType> independentAxis;
	private Axis<DependentType> dependentAxis;

	public StatsBundle(){
		super();
		stats = new MultisetStatsTable<>(this);
	}

	/**
	 * Typing.STATIC forces Jackson to use the custom key/value serializers defined in {@link ApiConfig}.
	 * If we just let Jackson try to serialize properties of properties, it won't use the configured serializers.
	 * @return
	 */
//	@JsonSerialize(typing=Typing.STATIC)
//	@JsonProperty("stats")
//	public Map<IndependentType, Map<DependentType, Integer>> getStatsTable(){
//		return this.stats.getTable();
//	}

//	@JsonProperty("stats")
//	@JsonSerialize(contentUsing=MultisetEntrySerializer.class)
//	public Set<Entry<StatsCoordinate<IndependentType, DependentType>>> getStatsTable(){
//		return this.stats.getTable();
//	}

	@JsonProperty("stats")
	@JsonSerialize(using=TripleArraySerializer.class)
	public Multiset<StatsCoordinate<IndependentType, DependentType>> getStatsTable(){
		return this.stats.getTable();
	}

	@JsonIgnore
	public StatsTable<IndependentType, DependentType> getStats(){
		return this.stats;
	}

	public Axis<IndependentType> getIndependentAxis() {
		return independentAxis;
	}

	public void setIndependentAxis(final Axis<IndependentType> independentAxis){
		this.independentAxis = independentAxis;
	}

	public Axis<DependentType> getDependentAxis() {
		return dependentAxis;
	}

	public void setDependentAxis(final Axis<DependentType> dependentAxis){
		this.dependentAxis = dependentAxis;
	}



	/*public void updateAxesFromStats() {
		final Comparator<IndependentType> independentComparator = new CastingComparator<>();
		final Comparator<DependentType> dependentComparator = new CastingComparator<>();

		final Set<IndependentType> sparseIndependentValues = stats.keySet();
		final Set<DependentType> sparseDependentValues = new HashSet<>();
		for(final Entry<IndependentType, Map<DependentType, Integer>> entry : stats.entrySet()){
			sparseDependentValues.addAll(entry.getValue().keySet());
		}

		axes.independentAxis.setValuesByRange(Collections.min(sparseIndependentValues, independentComparator), Collections.max(sparseIndependentValues, independentComparator), sparseIndependentValues);
		axes.dependentAxis.setValuesByRange(Collections.min(sparseDependentValues, dependentComparator), Collections.max(sparseDependentValues, dependentComparator), sparseDependentValues);

	}*/

	/*public static final class Axes<I, D> {
		public Axis<I> independentAxis;
		public Axis<D> dependentAxis;
	}*/
}
