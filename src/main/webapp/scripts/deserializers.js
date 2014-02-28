this.deserializers = (function(){
	
	var CLIENT_TIMEZONE = -moment().zone();
	
	var deserializers = {};
	
	deserializers['default'] = function(raw){
		return raw;
	};
	
	/**
	 * @param raw number of seconds since Unix epoch, referring to the start of a day
	 * @returns Human-readable date in the user's local timezone, such as "June 26, 2013"
	 */
	deserializers['LocalDate'] = function(raw){
		return moment(raw*1000).format('MMMM D, YYYY');
	};
	
	/**
	 * @param raw number of seconds 
	 * @returns Human-readable duration, such as "8 hours"
	 */
	deserializers['Duration'] = function(raw){
		var duration = moment.duration(raw*1000);
		return duration.asHours() + ' hours';
	};
	
	/**
	 * @param raw number of seconds since midnight
	 * @returns Human-readable time of day in the user's local timezone, such as "1:45 pm"
	 */
	deserializers['LocalTime'] = function(raw){
		return moment().hours(0).minutes(CLIENT_TIMEZONE - SERVER_TIMEZONE).seconds(raw).format('h:mm a');
	};
	
	/**
	 * @param raw string such as "MONDAY", "TUESDAY" from an enum
	 * @returns string such as "Monday", "Tuesday"
	 */
	deserializers['DayOfWeek'] = function(raw){
		return raw.charAt(0) + raw.substr(1).toLowerCase();
	};
	
	return {
		getDeserializer: function(type){
			return deserializers[type] || deserializers['default'];
		}
	};

})();