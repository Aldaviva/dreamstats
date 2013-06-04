this.StatsBundle = (function(){
	
	var StatsBundle = my.Class(null, MicroEvent, {
		
		STATIC: {
			CONSTRUCTOR_OPTS: ['url'],
			DATA_OPTS: ["stats", "independentInterval", "dependentInterval", "independentType", "dependentType"]
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
					this._ranges = this._buckets = null;
					this.trigger('afterFetch', this);
				}, this));
		},
		
		/**
		 * @returns object where the key is the axis, and the value is an array containing the min and max (in order) of the axis' range
		 * @example { "independent": [0, 82800], "dependent": [7200, 52200] }
		 */
		getRanges: function(){
			if(!this._ranges){
				var ranges = this._ranges = {
					independent: _(this.stats).keys(),
					dependent:   _(this.stats).map(_.keys).flatten()
				};
				
				_.each(ranges, function(keys, axis){
					var sortedKeys = keys.map(parseInt10).sortBy().value()
					ranges[axis] = [_.first(sortedKeys), _.last(sortedKeys)];
				});
			}
			
			return this._ranges;
		},

		/**
		 * @returns an object where the key is the axis, and the value is an array of the buckets along that axis
		 * @example: { "independent": [0, 1, 2, 3], "dependent": [3000, 3200, 3400, 3600] }
		 */
		getBuckets: function(){
			var ranges = this.getRanges();

			if(!this._buckets){
				var buckets = this._buckets = {};

				var intervals = {
					independent: this.independentInterval,
					dependent: this.dependentInterval
				};

				_.each(ranges, function(range, axis){
					buckets[axis] = _.range(range[0], range[1] + intervals[axis], intervals[axis]);
				});
			}

			return this._buckets;
		}
	});
	
	function parseInt10(i){
		return parseInt(i, 10);
	}
	
	return StatsBundle;
	
})();