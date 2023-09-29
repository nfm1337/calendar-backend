package ru.nfm.calendar.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nfm.calendar.dto.CalendarEventDto;
import ru.nfm.calendar.model.CalendarEvent;
import ru.nfm.calendar.model.CalendarRole;
import ru.nfm.calendar.model.CalendarUser;
import ru.nfm.calendar.model.User;
import ru.nfm.calendar.repository.CalendarEventRepository;
import ru.nfm.calendar.repository.CalendarUserRepository;
import ru.nfm.calendar.service.CalendarEventService;
import ru.nfm.calendar.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CalendarEventServiceImpl implements CalendarEventService {

    private final CalendarUserRepository calendarUserRepository;
    private final CalendarEventRepository calendarEventRepository;

    @Override
    @Transactional
    public CalendarEvent createCalendarEvent(User user, int calendarId, CalendarEventDto calendarEventDto) {
        CalendarUser calendarUser = calendarUserRepository.getExistedByUserIdAndCalendarId(user.getId(), calendarId);

        if (calendarUser.getCalendarRole() == CalendarRole.USER) {
            throw new AccessDeniedException("Нет прав на создание событий");
        }

        CalendarEvent event = CalendarEvent.builder()
                .title(calendarEventDto.title())
                .description(calendarEventDto.description())
                .creator(user.getUserProfile())
                .calendar(calendarUser.getCalendar())
                .timeFrom(calendarEventDto.timeFrom())
                .timeTo(calendarEventDto.timeTo())
                .isBlocking(calendarEventDto.isBlocking())
                .notificationTime(calendarEventDto.notificationTime())
                .build();

        event.addAttachedUser(user.getUserProfile());

        return calendarEventRepository.save(event);
    }

    @Override
    @Transactional
    public CalendarEvent updateCalendarEvent(User user, int calendarId, int eventId, CalendarEventDto calendarEventDto) {
        CalendarUser calendarUser = calendarUserRepository.getExistedByUserIdAndCalendarId(user.getId(), calendarId);

        if (calendarUser.getCalendarRole() == CalendarRole.USER) {
            throw new AccessDeniedException("Нет прав на редактирование событий");
        }

        CalendarEvent event = calendarEventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Событие с id: " + eventId + " не найден"));
        event.setTitle(calendarEventDto.title());
        event.setDescription(calendarEventDto.description());
        event.setTimeFrom(calendarEventDto.timeFrom());
        event.setTimeTo(calendarEventDto.timeTo());
        event.setIsBlocking(calendarEventDto.isBlocking());
        event.setNotificationTime(calendarEventDto.notificationTime());

        return calendarEventRepository.save(event);
    }

    @Override
    @Transactional
    public CalendarEvent getCalendarEvent(User user, int calendarId, int eventId) {
        checkUserBelongsToCalendar(user, calendarId);
        return calendarEventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event with id: " + eventId + " not found"));
    }

    @Override
    @Transactional
    public List<CalendarEvent> getCalendarEventsByDateTimeRange(User user, int calendarId,
                                                                LocalDateTime dtFrom, LocalDateTime dtTo) {
        checkUserBelongsToCalendar(user, calendarId);
        return calendarEventRepository.findCalendarEventsByDateTimeRange(
                calendarId,
                DateTimeUtil.convertLocalDateTimeToInstant(dtFrom),
                DateTimeUtil.convertLocalDateTimeToInstant(dtTo));
    }

    @Override
    @Transactional
    public List<CalendarEvent> getUserAttachedCalendarEventsByDateTimeRange(User user, int calendarId,
                                                                            LocalDateTime dtFrom, LocalDateTime dtTo) {
        checkUserBelongsToCalendar(user, calendarId);
        return calendarEventRepository.findUserAttachedCalendarEventsByDateTimeRange(
                user.getId(),
                calendarId,
                DateTimeUtil.convertLocalDateTimeToInstant(dtFrom),
                DateTimeUtil.convertLocalDateTimeToInstant(dtTo));
    }

    private void checkUserBelongsToCalendar(User user, int calendarId) {
        calendarUserRepository.findByUserIdAndCalendarId(user.getId(), calendarId)
                .orElseThrow(() -> new AccessDeniedException("No access to calendar with id: " + calendarId));
    }
}
