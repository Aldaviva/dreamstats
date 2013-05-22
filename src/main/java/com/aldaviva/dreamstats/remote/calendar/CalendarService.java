package com.aldaviva.dreamstats.remote.calendar;

import java.util.List;

import com.aldaviva.dreamstats.data.model.CalendarEvent;
import com.google.common.base.Predicate;

public interface CalendarService {

	List<CalendarEvent> findEvents(Predicate<CalendarEvent> predicate);


}
