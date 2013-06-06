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
		new StatsGraph({ bundle: statsBundles.startTimeVsSleepDuration,           independentLabel: "Start Time",            dependentLabel: "Sleep Duration" }),
		new StatsGraph({ bundle: statsBundles.durationSinceEatingVsSleepDuration, independentLabel: "Duration Since Eating", dependentLabel: "Sleep Duration" }),
		new StatsGraph({ bundle: statsBundles.dayOfWeekVsSleepDuration,           independentLabel: "Day of Week",           dependentLabel: "Sleep Duration" }),
		new StatsGraph({ bundle: statsBundles.previousEventVsSleepDuration,       independentLabel: "Previous Event",        dependentLabel: "Sleep Duration" }),
		new StatsGraph({ bundle: statsBundles.dateVsSleepDuration,                independentLabel: "Date",                  dependentLabel: "Sleep Duration" }),
		new StatsGraph({ bundle: statsBundles.dateVsAwakeDuration,                independentLabel: "Date",                  dependentLabel: "Awake Duration" }),
	];
	
	_.each(statsGraphs, function(statsGraph){
		$('#tables').append(statsGraph.$el);
	});

	_.invoke(statsBundles, 'fetch');
	
})();