package ru.nfm.calendar.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.nfm.calendar.dto.CalendarUserDto;
import ru.nfm.calendar.model.User;
import ru.nfm.calendar.payload.response.CalendarUserInviteResponse;
import ru.nfm.calendar.service.CalendarUserService;

import java.util.List;

@RestController
@RequestMapping(value = CalendarUserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CalendarUserController {
    static final String REST_URL = CalendarController.REST_URL + "/{calendarId}/users";
    private final CalendarUserService calendarUserService;

    @GetMapping
    public ResponseEntity<List<CalendarUserDto>> getCalendarUsers(@AuthenticationPrincipal User user,
                                                                  @PathVariable int calendarId) {
        return ResponseEntity.ok(calendarUserService.getCalendarUsers(user.getId(), calendarId));
    }

    @PostMapping
    public ResponseEntity<CalendarUserInviteResponse> inviteUserToCalendar(@AuthenticationPrincipal User user,
                                                                           @PathVariable int calendarId,
                                                                           @RequestParam String email) {
        return ResponseEntity.ok(calendarUserService.inviteUserByEmail(user.getId(), calendarId, email));
    }
}
