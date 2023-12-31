package ru.nfm.calendar.web.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.nfm.calendar.dto.CalendarDto;
import ru.nfm.calendar.model.User;
import ru.nfm.calendar.payload.request.CalendarRequest;
import ru.nfm.calendar.payload.response.CalendarCreateResponse;
import ru.nfm.calendar.payload.response.CalendarToggleActiveResponse;
import ru.nfm.calendar.service.CalendarService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = CalendarController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CalendarController {
    static final String REST_URL = "/calendars";
    private final CalendarService calendarService;

    @PostMapping
    public ResponseEntity<CalendarCreateResponse> createCalendar(@AuthenticationPrincipal User user,
                                                                 @Valid @RequestBody CalendarRequest calendarRequest) {
        var calendar = calendarService.createCalendar(user, calendarRequest);

        CalendarCreateResponse response = new CalendarCreateResponse(
                calendar.getId(),
                calendar.getCreator().getId(),
                calendar.getTitle(),
                "Календарь создан успешно");

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(calendar.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CalendarDto>> getUserCalendarList(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(calendarService.getUserCalendars(user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CalendarToggleActiveResponse> toggleIsActive(@AuthenticationPrincipal User user,
                                                                       @PathVariable int id) {
        return ResponseEntity.ok(calendarService.toggleIsActive(user, id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CalendarDto> updateCalendar(@AuthenticationPrincipal User user,
                                                      @PathVariable int id,
                                                      @RequestBody CalendarDto calendarDto) {
        return ResponseEntity.ok(calendarService.updateCalendar(user, id, calendarDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCalendar(@AuthenticationPrincipal User user, @PathVariable int id) {
        calendarService.deleteCalendar(user, id);
        return ResponseEntity.noContent().build();
    }

}
