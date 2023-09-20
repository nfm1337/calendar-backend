package ru.nfm.calendar.service;

import ru.nfm.calendar.model.Calendar;
import ru.nfm.calendar.payload.request.CalendarRequest;

public interface CalendarService {

    Calendar createCalendar(CalendarRequest calendarRequest);
}
