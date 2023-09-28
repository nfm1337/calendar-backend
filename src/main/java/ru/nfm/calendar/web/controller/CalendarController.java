package ru.nfm.calendar.web.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.nfm.calendar.dto.CalendarDto;
import ru.nfm.calendar.dto.CalendarEventDto;
import ru.nfm.calendar.mapper.CalendarEventMapper;
import ru.nfm.calendar.model.User;
import ru.nfm.calendar.payload.request.CalendarRequest;
import ru.nfm.calendar.payload.response.CalendarCreateResponse;
import ru.nfm.calendar.service.CalendarEventService;
import ru.nfm.calendar.service.CalendarService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = CalendarController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class CalendarController {
    static final String REST_URL = "/calendars";
    private final CalendarService calendarService;
    private final CalendarEventService calendarEventService;
    private final CalendarEventMapper calendarEventMapper;

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
        List<CalendarDto> calendarList = calendarService.getUserCalendars(user);
        return ResponseEntity.ok(calendarList);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Boolean> toggleIsActive(@AuthenticationPrincipal User user,
                                                  @PathVariable int id) {
        return ResponseEntity.ok(calendarService.toggleIsActive(user, id));
    }

    @PostMapping("/{calendarId}/events")
    public ResponseEntity<CalendarEventDto> createCalendarEvent(@AuthenticationPrincipal User user,
                                                                @PathVariable int calendarId,
                                                                @Valid @RequestBody CalendarEventDto calendarEvent) {
        var event = calendarEventService.createCalendarEvent(user, calendarId, calendarEvent);
        URI uriComponentsBuilder = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(event.getId())
                .toUri();

        return ResponseEntity.created(uriComponentsBuilder).body(calendarEventMapper.toDto(event));
    }

    @GetMapping("/{calendarId}/events/{eventId}")
    public ResponseEntity<CalendarEventDto> getCalendarEvent(@AuthenticationPrincipal User user,
                                                             @PathVariable int calendarId,
                                                             @PathVariable int eventId) {
        var event = calendarEventService.getCalendarEvent(user, calendarId, eventId);
        return ResponseEntity.ok(calendarEventMapper.toDto(event));
    }

    @PutMapping("/{calendarId}/events/{eventId}")
    public ResponseEntity<CalendarEventDto> updateCalendarEvent(@AuthenticationPrincipal User user,
                                                                @PathVariable int calendarId,
                                                                @PathVariable int eventId,
                                                                @Valid @RequestBody CalendarEventDto calendarEventDto) {
        var event = calendarEventService.updateCalendarEvent(user, calendarId, eventId, calendarEventDto);
        return ResponseEntity.ok(calendarEventMapper.toDto(event));
    }
}
