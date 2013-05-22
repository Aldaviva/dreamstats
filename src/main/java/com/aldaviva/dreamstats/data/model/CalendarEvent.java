package com.aldaviva.dreamstats.data.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import com.google.api.services.calendar.model.Event;

public class CalendarEvent {

	private DateTime start;
	private DateTime end;
	private String name;

	public CalendarEvent(){

	}

	public CalendarEvent(final Event googleCalendarEvent){
		name = googleCalendarEvent.getSummary();
		start = new DateTime(googleCalendarEvent.getStart().getDateTime().getValue());
		end = new DateTime(googleCalendarEvent.getEnd().getDateTime().getValue());
	}

	public DateTime getStart() {
		return start;
	}
	public void setStart(final DateTime start) {
		this.start = start;
	}
	public DateTime getEnd() {
		return end;
	}
	public void setEnd(final DateTime end) {
		this.end = end;
	}
	public String getName() {
		return name;
	}
	public void setName(final String name) {
		this.name = name;
	}

	@JsonIgnore
	public Duration getDuration(){
		return new Duration(start, end);
	}
}
