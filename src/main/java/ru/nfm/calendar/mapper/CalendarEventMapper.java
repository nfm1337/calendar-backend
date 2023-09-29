package ru.nfm.calendar.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nfm.calendar.dto.CalendarEventDto;
import ru.nfm.calendar.model.CalendarEvent;
import ru.nfm.calendar.util.DateTimeUtil;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CalendarEventMapper {
    CalendarEventDto toDto(CalendarEvent event, @Context String userTimeZone);
    List<CalendarEventDto> toDtoList(List<CalendarEvent> events, @Context String userTimeZone);

    @Mapping(target = "id", ignore = true)
    void updateCalendarEventFromDto(CalendarEventDto calendarEventDto,
                                    @MappingTarget CalendarEvent calendarEvent,
                                    @Context String userTimeZone);

    default LocalDateTime toLocalDateTime(Instant instant, @Context String userTimeZone) {
        return DateTimeUtil.convertUtcInstantToLocalDateTime(userTimeZone, instant);
    }

    default Instant toInstant(LocalDateTime localDateTime, @Context String userTimeZone) {
        return DateTimeUtil.convertLocalDateTimeToUtcInstant(userTimeZone, localDateTime);
    }
}
