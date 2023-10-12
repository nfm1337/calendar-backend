package ru.nfm.calendar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nfm.calendar.dto.CalendarDto;
import ru.nfm.calendar.model.Calendar;

@Mapper(componentModel = "spring")
public interface CalendarMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "events", ignore = true)
    @Mapping(target = "calendarUsers", ignore = true)
    void updateCalendarFromDto(CalendarDto calendarDto, @MappingTarget Calendar calendar);

    CalendarDto toDto(Calendar calendar);

    CalendarDto toDtoWithIsActive(Calendar calendar, boolean isActive);
}
