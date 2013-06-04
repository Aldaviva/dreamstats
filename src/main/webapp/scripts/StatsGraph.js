this.StatsGraph = (function(){

	var StatsGraph = my.Class({
		constructor: function(){
			_.bindAll(this);
			this.$el = $('<div>');
			this.el = $el[0];
		},

		render: function(){
			return this.el;
		}
	});

	return StatsGraph;

})();