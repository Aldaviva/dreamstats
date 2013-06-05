this.StatsGraph = (function(){

	var StatsGraph = my.Class({
		constructor: function(opts){
			_.bindAll(this);
			_.extend(this, opts);

			this.$el = $('<div>');

			this.bundle.bind('afterFetch', _.bind(function(bundle){
				this.render();
			}, this));
		},

		render: function(){
			//iterate through
			var rowCount = this.bundle.dependent.length;
			var columnCount = this.bundle.independent.length;

			this.bundle.forEach(function(indep, indepIdx, dep, depIdx, val, rank){

			}, this);
		}
	});

	return StatsGraph;

})();