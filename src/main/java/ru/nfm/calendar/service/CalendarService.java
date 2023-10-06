package ru.nfm.calendar.service;

import ru.nfm.calendar.dto.CalendarDto;
import ru.nfm.calendar.model.Calendar;
import ru.nfm.calendar.model.User;
import ru.nfm.calendar.payload.request.CalendarRequest;
import ru.nfm.calendar.payload.response.CalendarToggleActiveResponse;

import java.util.List;

public interface CalendarService {
    Calendar createCalendar(User user, CalendarRequest calendarRequest);
    List<CalendarDto> getUserCalendars(User user);
    CalendarToggleActiveResponse toggleIsActive(User user, int id);
    void deleteCalendar(User user, int id);
    CalendarDto updateCalendar(User user, int id, CalendarDto calendarDto);
}
