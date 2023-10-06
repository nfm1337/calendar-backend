package ru.nfm.calendar.service;

import ru.nfm.calendar.dto.CalendarUserDto;
import ru.nfm.calendar.payload.response.CalendarUserInviteResponse;

import java.util.List;

public interface CalendarUserService {
    List<CalendarUserDto> getCalendarUsers(int userId, int calendarId);
    CalendarUserInviteResponse inviteUserByEmail(int userId, int calendarId, String email);
    void deleteCalendarUser(int userId, int calendarId, int calendarUserId);
}
