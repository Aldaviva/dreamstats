this.StatsBundle = (function(){
	
	var StatsBundle = my.Class(null, MicroEvent, {
		
		STATIC: {
			CONSTRUCTOR_OPTS: ['url'],
			DATA_OPTS: ["independent", "dependent", "counts"]
		},
		
		constructor: function(opts){
			_.extend(this, _.pick(opts, StatsBundle.CONSTRUCTOR_OPTS));
			_.bindAll(this);
		},
		
		/**
		 * Retrieve data from server, trigger afterFetch event (asynchronously)
		 * @returns promise that resolves when the request is done
		 */
		fetch: function(){
			return $.get('api/stats/'+this.url, 'json')
				.done(_.bind(function(data){
					_.extend(this, _.pick(data, StatsBundle.DATA_OPTS));
					this.trigger('afterFetch', this);
				}, this));
		},

		/**
		 * Iterate over the data. Callback looks like
		 *  function(independentBucket, independentIndex, dependentBucket, dependentIndex, count, rank) {}
		 */
		/*forEach: function(callback, context){
			var boundCallback = _.bind(callback, context);

			if(this.counts){
				var numEntries = this.counts.values.length;
				var dependentLength = this.dependent.size;
				for(int i=0; i < numEntries; ++i){
					var independentBucket = this.independent.values[i];
					var independentIndex = ~~(i/dependentLength);

					var dependentBucket = this.dependent.values[i];
					var dependentIndex = i % dependentLength;

					var count = this.counts.values[i];
					var rank = this.counts.ranks[i]

					boundCallback(independentBucket, independentIndex, dependentBucket, dependentIndex, count, rank);
				}
			}
		}*/
	});
	
	return StatsBundle;
	
})();