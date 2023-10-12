package ru.nfm.calendar.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nfm.calendar.dto.CalendarDto;
import ru.nfm.calendar.exception.UserNotFoundException;
import ru.nfm.calendar.mapper.CalendarMapper;
import ru.nfm.calendar.model.*;
import ru.nfm.calendar.payload.request.CalendarRequest;
import ru.nfm.calendar.payload.response.CalendarToggleActiveResponse;
import ru.nfm.calendar.repository.CalendarRepository;
import ru.nfm.calendar.repository.CalendarUserRepository;
import ru.nfm.calendar.repository.UserProfileRepository;
import ru.nfm.calendar.service.CalendarService;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CalendarServiceImpl implements CalendarService {

    private final CalendarRepository calendarRepository;
    private final UserProfileRepository userProfileRepository;
    private final CalendarUserRepository calendarUserRepository;
    private final CalendarMapper calendarMapper;

    @Override
    @Transactional
    public Calendar createCalendar(User user, CalendarRequest calendarRequest) {
        UserProfile userProfile = userProfileRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(user.getId(), "not found"));

        var calendar = Calendar.builder()
                .color(calendarRequest.color())
                .title(calendarRequest.title())
                .creator(userProfile)
                .build();

        calendar = calendarRepository.save(calendar);

        var calendarUserKey = new CalendarUserKey();
        calendarUserKey.setUserId(userProfile.getId());
        calendarUserKey.setCalendarId(calendar.getId());
        var calendarUser = new CalendarUser();
        calendarUser.setId(calendarUserKey);
        calendarUser.setUserProfile(userProfile);
        calendarUser.setCalendar(calendar);
        calendarUser.setCalendarRole(CalendarRole.CREATOR);
        calendarUser.setIsCalendarActive(true);

        calendarUserRepository.save(calendarUser);

        return calendar;
    }

    @Override
    public List<CalendarDto> getUserCalendars(User user) {
        var userProfile = userProfileRepository.findByIdWithCalendarUsers(user.getId())
                .orElseThrow(() -> new UserNotFoundException(user.getId(), "not found"));

        return userProfile.getCalendarUserList().stream()
                .map(u -> calendarMapper.toDtoWithIsActive(u.getCalendar(), u.getIsCalendarActive()))
                .toList();
    }

    @Override
    @Transactional
    public CalendarToggleActiveResponse toggleIsActive(User user, int calendarId) {
        var calendarUser = getCalendarUser(user, calendarId);
        calendarUser.setIsCalendarActive(!calendarUser.getIsCalendarActive());
        calendarUserRepository.save(calendarUser);
        return new CalendarToggleActiveResponse(calendarId, calendarUser.getIsCalendarActive());
    }

    @Override
    public void deleteCalendar(User user, int id) {
        var calendarUser = getCalendarUser(user, id);
        if (calendarUser.getCalendarRole() != CalendarRole.CREATOR) {
            throw new AccessDeniedException("Can't delete calendar ");
        }
        calendarRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CalendarDto updateCalendar(User user, int id, CalendarDto calendarDto) {
        var calendarUser = getCalendarUser(user, id);
        if (calendarUser.getCalendarRole() != CalendarRole.CREATOR) {
            throw new AccessDeniedException("Can't update calendar");
        }
        var calendar = calendarRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Calendar with id : " + id + " not found"));
        calendarMapper.updateCalendarFromDto(calendarDto, calendar);
        return calendarMapper.toDto(calendarRepository.save(calendar));
    }

    private CalendarUser getCalendarUser(User user, int calendarId) {
        return calendarUserRepository.findCalendarUserByUserIdAndCalendarId(user.getId(), calendarId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Calendar user with userId: " + user.getId() + " and calendarId: " + calendarId + " not found"));
    }
}
