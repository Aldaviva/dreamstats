this.StatsGraph = (function(){

	var StatsGraph = my.Class({

		/**
		 * @param opts: object containing
		 * 		- StatsBundle bundle
		 */
		constructor: function(opts){
			_.bindAll(this);
			_.extend(this, opts);

			this.$el = $('<div>').addClass('statsGraph');

			this.bundle.bind('afterFetch', _.bind(function(bundle){
				this.render();
			}, this));
		},

		render: function(){
			var deps = this.bundle.dependent;
			var indeps = this.bundle.independent;
			var counts = this.bundle.counts;

			var rowCount = deps.size;
			var columnCount = indeps.size;

			this.$el.empty();

			this.$el.append($('<h3>').text(this.bundle.dependent.label + ' as a function of ' + this.bundle.independent.label));

			var tableEl = $('<div>').addClass('table');

			for(var i = rowCount - 1; i >= 0; --i){
				var rowEl = $('<div>')
					.addClass('row');

			    for(var j = 0; j < columnCount; ++j){
			        var index = [j * rowCount + i];
			        var indep = indeps.values[index];
			        var dep = deps.values[index];
			        var value = counts.values[index];
			        var rank = counts.ranks[index];

			        var cell = $('<div>')
			        	.css({
			        		opacity: rank
			        	})
			        	.attr({
			        		title: '('+indeps.id+': '+indep+', '+deps.id+': '+dep+') = '+value
			        	});

			        rowEl.append(cell);
			    }

			    tableEl.append(rowEl);
			}

			this.$el.append(tableEl);
		}
	});

	return StatsGraph;

})();