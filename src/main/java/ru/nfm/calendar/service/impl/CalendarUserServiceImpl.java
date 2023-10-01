package ru.nfm.calendar.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nfm.calendar.dto.CalendarUserDto;
import ru.nfm.calendar.model.CalendarRole;
import ru.nfm.calendar.model.CalendarUser;
import ru.nfm.calendar.model.CalendarUserKey;
import ru.nfm.calendar.payload.response.CalendarUserInviteResponse;
import ru.nfm.calendar.repository.CalendarRepository;
import ru.nfm.calendar.repository.CalendarUserRepository;
import ru.nfm.calendar.repository.UserProfileRepository;
import ru.nfm.calendar.service.CalendarUserService;

import java.util.List;

@Service
@AllArgsConstructor
public class CalendarUserServiceImpl implements CalendarUserService {
    private final CalendarUserRepository calendarUserRepository;
    private final UserProfileRepository userProfileRepository;
    private final CalendarRepository calendarRepository;

    @Override
    @Transactional
    public List<CalendarUserDto> getCalendarUsers(int userId, int calendarId) {
        if (!calendarUserRepository.existsByUserProfileIdAndCalendarId(userId, calendarId)) {
            throw new AccessDeniedException(
                    "User with id: " + userId + " don't have access to calendar with id: " + calendarId);
        }
        return calendarUserRepository.getCalendarUserDtoListByUserIdAndCalendarId(calendarId);
    }

    @Override
    @Transactional
    public CalendarUserInviteResponse inviteUserByEmail(int userId, int calendarId, String email) {
        var calendarUser = calendarUserRepository.findByUserIdAndCalendarId(userId, calendarId)
                .orElseThrow(() -> new AccessDeniedException(
                        "User with id: " + userId + " don't have access to calendar with id: " + calendarId));

        var role = calendarUser.getCalendarRole();
        if (role == CalendarRole.USER || role == CalendarRole.PENDING_INVITE) {
            throw new AccessDeniedException("User don't have access to invite to this calendar");
        }

        var calendar = calendarRepository.findById(calendarId)
                .orElseThrow(() -> new EntityNotFoundException("Calendar with id: " + calendarId + " not found"));
        var invitedUserProfile = userProfileRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email: " + email + " not found"));

        var invitedCalendarUser = new CalendarUser();
        invitedCalendarUser.setId(new CalendarUserKey(invitedUserProfile.getId(), calendarId));
        invitedCalendarUser.setCalendar(calendar);
        invitedCalendarUser.setUserProfile(invitedUserProfile);
        invitedCalendarUser.setCalendarRole(CalendarRole.PENDING_INVITE);
        invitedCalendarUser.setIsCalendarActive(true);
        invitedCalendarUser = calendarUserRepository.save(invitedCalendarUser);

        return new CalendarUserInviteResponse(
                invitedCalendarUser.getId().getUserId(), calendarId,"Invite sent");
    }
}
