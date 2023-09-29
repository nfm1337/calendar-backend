package ru.nfm.calendar.web.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.nfm.calendar.dto.CalendarEventDto;
import ru.nfm.calendar.model.User;
import ru.nfm.calendar.service.CalendarEventService;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import static ru.nfm.calendar.web.controller.CalendarEventController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class CalendarEventController {
    static final String REST_URL = CalendarController.REST_URL + "/{calendarId}/events";
    private final CalendarEventService calendarEventService;

    @PostMapping
    public ResponseEntity<CalendarEventDto> createCalendarEvent(@AuthenticationPrincipal User user,
                                                                @PathVariable int calendarId,
                                                                @Valid @RequestBody CalendarEventDto calendarEvent) {
        var event = calendarEventService.createCalendarEvent(user, calendarId, calendarEvent);
        URI uriComponentsBuilder = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(event.id())
                .toUri();

        return ResponseEntity.created(uriComponentsBuilder).body(event);
    }

    @GetMapping
    public ResponseEntity<List<CalendarEventDto>> getEventsByDateTimeRange(
            @AuthenticationPrincipal User user,
            @PathVariable int calendarId,
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dtFrom,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dtTo) {

        var events = calendarEventService.getCalendarEventsByDateTimeRange(user, calendarId, dtFrom, dtTo);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<CalendarEventDto> getCalendarEvent(@AuthenticationPrincipal User user,
                                                             @PathVariable int calendarId,
                                                             @PathVariable int eventId) {
        var event = calendarEventService.getCalendarEvent(user, calendarId, eventId);
        return ResponseEntity.ok(event);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<CalendarEventDto> updateCalendarEvent(@AuthenticationPrincipal User user,
                                                                @PathVariable int calendarId,
                                                                @PathVariable int eventId,
                                                                @Valid @RequestBody CalendarEventDto calendarEventDto) {
        var event = calendarEventService.updateCalendarEvent(user, calendarId, eventId, calendarEventDto);
        return ResponseEntity.ok(event);
    }

    @GetMapping("/attached-events")
    public ResponseEntity<List<CalendarEventDto>> getUserAttachedEventsByDateTimeRange(
            @AuthenticationPrincipal User user,
            @PathVariable int calendarId,
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dtFrom,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dtTo) {

        var events = calendarEventService.getUserAttachedCalendarEventsByDateTimeRange(user, calendarId, dtFrom, dtTo);
        return ResponseEntity.ok(events);
    }
}
