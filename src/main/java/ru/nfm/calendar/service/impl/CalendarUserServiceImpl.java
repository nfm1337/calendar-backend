package ru.nfm.calendar.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nfm.calendar.dto.CalendarUserDto;
import ru.nfm.calendar.mapper.CalendarUserMapper;
import ru.nfm.calendar.model.*;
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
    private final CalendarUserMapper calendarUserMapper;

    private static final String ACCESS_DENIED_MESSAGE = "Access denied: ";
    private static final String CALENDAR_NOT_FOUND_MESSAGE = "Calendar not found with ID: ";
    private static final String USER_NOT_FOUND_MESSAGE = "User not found with ID: ";
    private static final String EMAIL_NOT_FOUND_MESSAGE = "User not found with email: ";

    @Override
    @Transactional
    public List<CalendarUserDto> getCalendarUsers(int userId, int calendarId) {
        if (!calendarUserRepository.existsByUserProfileIdAndCalendarId(userId, calendarId)) {
            throw new AccessDeniedException(ACCESS_DENIED_MESSAGE +
                    "User with ID: " + userId + " doesn't have access to calendar with ID: " + calendarId);
        }
        return calendarUserRepository.getCalendarUserDtoListByUserIdAndCalendarId(calendarId);
    }

    @Override
    @Transactional
    public CalendarUserInviteResponse inviteUserByEmail(int userId, int calendarId, String email) {
        CalendarUser calendarUser = getCalendarUser(userId, calendarId);

        CalendarRole role = calendarUser.getCalendarRole();
        if (role == CalendarRole.USER || role == CalendarRole.PENDING_INVITE) {
            throw new AccessDeniedException(ACCESS_DENIED_MESSAGE + "User doesn't have access to invite to this calendar");
        }

        Calendar calendar = calendarRepository.findById(calendarId)
                .orElseThrow(() -> new EntityNotFoundException(CALENDAR_NOT_FOUND_MESSAGE + calendarId));

        UserProfile invitedUserProfile = userProfileRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(EMAIL_NOT_FOUND_MESSAGE + email));

        CalendarUser invitedCalendarUser = createInvitedCalendarUser(calendarId, invitedUserProfile, calendar);
        calendarUserRepository.save(invitedCalendarUser);

        return new CalendarUserInviteResponse(
                invitedCalendarUser.getId().getUserId(), calendarId, "Invite sent");
    }

    @Override
    @Transactional
    public void deleteCalendarUser(int userId, int calendarId, int calendarUserId) {
        CalendarUser calendarUser = getCalendarUser(userId, calendarId);
        CalendarRole role = calendarUser.getCalendarRole();
        if (role != CalendarRole.CREATOR && role != CalendarRole.EDITOR) {
            throw new AccessDeniedException(ACCESS_DENIED_MESSAGE +
                    "User doesn't have access to remove users from this calendar");
        }

        CalendarUser calendarUserToDelete = getCalendarUser(calendarUserId, calendarId);
        calendarUserRepository.delete(calendarUserToDelete);
    }

    @Override
    @Transactional
    public CalendarUserDto promoteUser(int userId, int calendarId, int calendarUserId) {
        var calendarUser = getCalendarUser(userId, calendarId);
        var role = calendarUser.getCalendarRole();
        if (role != CalendarRole.CREATOR) {
            throw new AccessDeniedException(ACCESS_DENIED_MESSAGE +
                    "User don't have access to promote users in this calendar");
        }
        var promotedUser = getCalendarUser(calendarUserId, calendarId);
        promotedUser.setCalendarRole(CalendarRole.EDITOR);
        return calendarUserMapper.toDto(calendarUserRepository.save(promotedUser));
    }

    @Override
    @Transactional
    public CalendarUserDto demoteUser(int userId, int calendarId, int calendarUserId) {
        var calendarUser = getCalendarUser(userId, calendarId);
        var role = calendarUser.getCalendarRole();
        if (role != CalendarRole.CREATOR) {
            throw new AccessDeniedException(ACCESS_DENIED_MESSAGE +
                    "User don't have access to demote users in this calendar");
        }
        var demotedUser = getCalendarUser(calendarUserId, calendarId);
        demotedUser.setCalendarRole(CalendarRole.USER);
        return calendarUserMapper.toDto(calendarUserRepository.save(demotedUser));
    }

    private CalendarUser getCalendarUser(int userId, int calendarId) {
        return calendarUserRepository.findCalendarUserByUserIdAndCalendarId(userId, calendarId)
                .orElseThrow(() -> new AccessDeniedException(ACCESS_DENIED_MESSAGE +
                        "User with ID: " + userId + " doesn't have access to calendar with ID: " + calendarId));
    }

    private CalendarUser createInvitedCalendarUser(int calendarId, UserProfile invitedUserProfile, Calendar calendar) {
        CalendarUser invitedCalendarUser = new CalendarUser();
        invitedCalendarUser.setId(new CalendarUserKey(invitedUserProfile.getId(), calendarId));
        invitedCalendarUser.setCalendar(calendar);
        invitedCalendarUser.setUserProfile(invitedUserProfile);
        invitedCalendarUser.setCalendarRole(CalendarRole.PENDING_INVITE);
        invitedCalendarUser.setIsCalendarActive(true);
        return invitedCalendarUser;
    }
}
