package ru.nfm.calendar.web.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.nfm.calendar.dto.CalendarDto;
import ru.nfm.calendar.dto.CalendarEventDto;
import ru.nfm.calendar.model.Calendar;
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

    @PostMapping
    public ResponseEntity<CalendarCreateResponse> createCalendar(@Valid @RequestBody CalendarRequest calendarRequest) {
        Calendar calendar = calendarService.createCalendar(calendarRequest);

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
    public ResponseEntity<List<CalendarDto>> getUserCalendarList() {
        List<CalendarDto> calendarList = calendarService.getUserCalendars();
        return ResponseEntity.ok( calendarList);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Boolean> toggleIsActive(@PathVariable int id) {
        return ResponseEntity.ok(calendarService.toggleIsActive(id));
    }

    @PostMapping("/{id}/events")
    public ResponseEntity<CalendarEventDto> createCalendarEvent(@PathVariable int id,
                                                             @Valid @RequestBody CalendarEventDto calendarEvent) {
        var event = calendarEventService.createCalendarEvent(id, calendarEvent);
        return ResponseEntity.ok(event);
    }
}
