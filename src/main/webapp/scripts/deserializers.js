this.deserializers = (function(){
	
	function noop(raw){
		return raw;
	}
	
	var deserializers = {};
	
	/**
	 * @param raw number of seconds since Unix epoch, referring to the start of a day
	 * @returns Human-readable date in the user's local timezone, such as "June 26, 2013"
	 */
	deserializers['LocalDate'] = function(raw){
		return moment(raw*1000).format('MMMM D, YYYY');
	};
	
	/**
	 * @param raw number of seconds 
	 * @returns Human-readable duration, such as "8h 20m"
	 */
	deserializers['Duration'] = function(raw){
		var duration = moment.duration(raw*1000);
		var totalMinutes = duration.asMinutes();
		var hours = Math.floor(totalMinutes/60);
		var minutes = totalMinutes % 60;
		
		var formatted = '';
		if(hours){
			formatted += hours + "h ";
		}
		formatted += minutes "m";
		return formatted;
	};
	
	/**
	 * @param raw number of seconds since midnight
	 * @returns Human-readable time of day in the user's local timezone, such as "1:45 pm"
	 */
	deserializers['LocalTime'] = function(raw){
		var moment = moment().seconds(raw);
		return moment.format('h:mm a');
	};
	
	deserializers['String'] = noop;
	
	deserializers['Integer'] = noop; //not sure how to handle day-of-week, which should maybe be an enum instead of an integer
	
	return deserializers;

})();