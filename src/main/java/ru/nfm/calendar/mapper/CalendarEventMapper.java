package ru.nfm.calendar.mapper;

import org.mapstruct.Mapper;
import ru.nfm.calendar.dto.CalendarEventDto;
import ru.nfm.calendar.model.CalendarEvent;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CalendarEventMapper {
    CalendarEventDto toDto(CalendarEvent event);
    List<CalendarEventDto> toDtoList(List<CalendarEvent> events);
}
