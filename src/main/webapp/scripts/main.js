(function(){
	var statsBundles = {
		startTimeVsSleepDuration:           new StatsBundle({ url: 'start-time-vs-sleep-duration' }),
		dayOfWeekVsSleepDuration:           new StatsBundle({ url: 'day-of-week-vs-sleep-duration' }),
		dateVsSleepDuration:                new StatsBundle({ url: 'date-vs-sleep-duration' }),
		dateVsAwakeDuration:                new StatsBundle({ url: 'date-vs-awake-duration' }),
		durationSinceEatingVsSleepDuration: new StatsBundle({ url: 'duration-since-eating-vs-sleep-duration' }),
		previousEventVsSleepDuration:       new StatsBundle({ url: 'previous-event-vs-sleep-duration'})
	};
	
	var statsGraphs = [
		new StatsGraph({ bundle: statsBundles.startTimeVsSleepDuration           }),
		new StatsGraph({ bundle: statsBundles.durationSinceEatingVsSleepDuration }),
		new StatsGraph({ bundle: statsBundles.dateVsSleepDuration                }),
		new StatsGraph({ bundle: statsBundles.dateVsAwakeDuration                }),
		new StatsGraph({ bundle: statsBundles.dayOfWeekVsSleepDuration           }),
		new StatsGraph({ bundle: statsBundles.previousEventVsSleepDuration       })
	];
	
	_.each(statsGraphs, function(statsGraph){
		$('#tables').append(statsGraph.$el);
	});

	_.invoke(statsBundles, 'fetch');
	
})();