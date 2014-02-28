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
		}
	});
	
	return StatsBundle;
	
})();