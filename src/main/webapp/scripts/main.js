(function(){
	var statsBundles = {
		startTimeVsSleepDuration:           new StatsBundle({ url: 'start-time-vs-sleep-duration' }),
		dayOfWeekVsSleepDuration:           new StatsBundle({ url: 'day-of-week-vs-sleep-duration' }),
		dateVsSleepDuration:                new StatsBundle({ url: 'date-vs-sleep-duration' }),
		dateVsAwakeDuration:                new StatsBundle({ url: 'date-vs-awake-duration' }),
		durationSinceEatingVsSleepDuration: new StatsBundle({ url: 'duration-since-eating-vs-sleep-duration' }),
		previousEventVsSleepDuration:       new StatsBundle({ url: 'previous-event-vs-sleep-duration'})
	};
	
	var statsGraphs = {
		startTimeVsSleepDuration:           new StatsGraph({ bundle: statsBundles.startTimeVsSleepDuration,           independentLabel: "Start Time",            dependentLabel: "Sleep Duration" }),
		dayOfWeekVsSleepDuration:           new StatsGraph({ bundle: statsBundles.dayOfWeekVsSleepDuration,           independentLabel: "Day of Week",           dependentLabel: "Sleep Duration" }),
		dateVsSleepDuration:                new StatsGraph({ bundle: statsBundles.dateVsSleepDuration,                independentLabel: "Date",                  dependentLabel: "Sleep Duration" }),
		dateVsAwakeDuration:                new StatsGraph({ bundle: statsBundles.dateVsAwakeDuration,                independentLabel: "Date",                  dependentLabel: "Awake Duration" }),
		durationSinceEatingVsSleepDuration: new StatsGraph({ bundle: statsBundles.durationSinceEatingVsSleepDuration, independentLabel: "Duration Since Eating", dependentLabel: "Sleep Duration" }),
		previousEventVsSleepDuration:       new StatsGraph({ bundle: statsBundles.previousEventVsSleepDuration,       independentLabel: "Previous Event",        dependentLabel: "Sleep Duration" }),
	};
	
	_.each(statsGraph, function(statsGraph){
		$(document.body).append(statsGraph.$el);
	});

	_.invoke(statsBundles, 'fetch');
	
})();