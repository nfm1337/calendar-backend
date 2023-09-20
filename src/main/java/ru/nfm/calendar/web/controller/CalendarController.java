package ru.nfm.calendar.web.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nfm.calendar.model.Calendar;
import ru.nfm.calendar.payload.request.CalendarRequest;
import ru.nfm.calendar.payload.response.CalendarCreateResponse;
import ru.nfm.calendar.service.CalendarService;

@RestController
@RequestMapping(value = CalendarController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class CalendarController {

    static final String REST_URL = "/calendars";
    private final CalendarService calendarService;

    @PostMapping
    public ResponseEntity<CalendarCreateResponse> createCalendar(@Valid @RequestBody CalendarRequest calendarRequest) {
        Calendar calendar = calendarService.createCalendar(calendarRequest);

        CalendarCreateResponse response = new CalendarCreateResponse(
                calendar.getId(),
                calendar.getCreator().getId(),
                calendar.getTitle(),
                "Календарь создан успешно");

        return ResponseEntity.ok(response);
    }
}
