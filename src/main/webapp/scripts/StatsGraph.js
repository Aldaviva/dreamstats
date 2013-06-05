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
			
		}
	});

	return StatsGraph;

})();