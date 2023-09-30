package ru.nfm.calendar.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nfm.calendar.dto.CalendarEventDto;
import ru.nfm.calendar.mapper.CalendarEventMapper;
import ru.nfm.calendar.model.CalendarEvent;
import ru.nfm.calendar.model.CalendarRole;
import ru.nfm.calendar.model.CalendarUser;
import ru.nfm.calendar.model.User;
import ru.nfm.calendar.repository.CalendarEventRepository;
import ru.nfm.calendar.repository.CalendarUserRepository;
import ru.nfm.calendar.repository.UserProfileRepository;
import ru.nfm.calendar.service.CalendarEventService;
import ru.nfm.calendar.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CalendarEventServiceImpl implements CalendarEventService {

    private final CalendarUserRepository calendarUserRepository;
    private final CalendarEventRepository calendarEventRepository;
    private final UserProfileRepository userProfileRepository;
    private final CalendarEventMapper calendarEventMapper;

    @Override
    @Transactional
    public CalendarEventDto createCalendarEvent(User user, int calendarId, CalendarEventDto calendarEventDto) {
        CalendarUser calendarUser = calendarUserRepository.getExistedByUserIdAndCalendarId(user.getId(), calendarId);
        checkUserEditRights(calendarUser);
        String userTimezone = userProfileRepository.getUserTimezoneByUserId(user.getId());
        CalendarEvent event = CalendarEvent.builder()
                .title(calendarEventDto.title())
                .description(calendarEventDto.description())
                .creator(user.getUserProfile())
                .calendar(calendarUser.getCalendar())
                .timeFrom(DateTimeUtil.convertLocalDateTimeToUtcInstant(userTimezone, calendarEventDto.timeFrom()))
                .timeTo(DateTimeUtil.convertLocalDateTimeToUtcInstant(userTimezone, calendarEventDto.timeTo()))
                .isBlocking(calendarEventDto.isBlocking())
                .notificationTime(DateTimeUtil.convertLocalDateTimeToUtcInstant(
                        userTimezone, calendarEventDto.notificationTime())
                )
                .build();

        event.addAttachedUser(user.getUserProfile());

        return calendarEventMapper.toDto(calendarEventRepository.save(event), userTimezone);
    }

    @Override
    @Transactional
    public CalendarEventDto updateCalendarEvent(User user, int calendarId, int eventId,
                                                CalendarEventDto calendarEventDto) {
        CalendarUser calendarUser = calendarUserRepository.getExistedByUserIdAndCalendarId(user.getId(), calendarId);
        checkUserEditRights(calendarUser);
        String userTimezone = userProfileRepository.getUserTimezoneByUserId(user.getId());
        CalendarEvent event = calendarEventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Событие с id: " + eventId + " не найден"));
        calendarEventMapper.updateCalendarEventFromDto(calendarEventDto, event, userTimezone);

        return calendarEventMapper.toDto(calendarEventRepository.save(event), userTimezone);
    }

    @Override
    @Transactional
    public CalendarEventDto getCalendarEvent(User user, int calendarId, int eventId) {
        checkUserBelongsToCalendar(user, calendarId);
        String userTimezone = userProfileRepository.getUserTimezoneByUserId(user.getId());
        var event = calendarEventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event with id: " + eventId + " not found"));
        return calendarEventMapper.toDto(calendarEventRepository.save(event), userTimezone);
    }

    @Override
    @Transactional
    public List<CalendarEventDto> getCalendarEventsByDateTimeRange(User user, int calendarId,
                                                                   LocalDateTime dtFrom, LocalDateTime dtTo) {
        checkUserBelongsToCalendar(user, calendarId);
        String userTimezone = userProfileRepository.getUserTimezoneByUserId(user.getId());
        var events = calendarEventRepository.findCalendarEventsByDateTimeRange(
                calendarId,
                DateTimeUtil.convertLocalDateTimeToUtcInstant(userTimezone, dtFrom),
                DateTimeUtil.convertLocalDateTimeToUtcInstant(userTimezone, dtTo));

        return calendarEventMapper.toDtoList(events, userTimezone);
    }

    @Override
    @Transactional
    public List<CalendarEventDto> getUserAttachedCalendarEventsByDateTimeRange(User user,
                                                                               int calendarId,
                                                                               LocalDateTime dtFrom,
                                                                               LocalDateTime dtTo) {
        checkUserBelongsToCalendar(user, calendarId);
        String userTimezone = userProfileRepository.getUserTimezoneByUserId(user.getId());
        var events = calendarEventRepository.findUserAttachedCalendarEventsByDateTimeRange(
                user.getId(),
                calendarId,
                DateTimeUtil.convertLocalDateTimeToUtcInstant(userTimezone, dtFrom),
                DateTimeUtil.convertLocalDateTimeToUtcInstant(userTimezone, dtTo));

        return calendarEventMapper.toDtoList(events, userTimezone);
    }

    @Override
    @Transactional
    public void deleteCalendarEvent(User user, int calendarId, int eventId) {
        var calendarUser = calendarUserRepository.getExistedByUserIdAndCalendarId(user.getId(), calendarId);
        checkUserEditRights(calendarUser);
        calendarEventRepository.deleteById(eventId);
    }

    private void checkUserEditRights(CalendarUser calendarUser) {
        if (calendarUser.getCalendarRole() == CalendarRole.USER) {
            throw new AccessDeniedException("Нет прав для редактирования событий");
        }
    }

    private void checkUserBelongsToCalendar(User user, int calendarId) {
        calendarUserRepository.findByUserIdAndCalendarId(user.getId(), calendarId)
                .orElseThrow(() -> new AccessDeniedException("No access to calendar with id: " + calendarId));
    }
}
