package ru.nfm.calendar.mapper;

import org.mapstruct.Mapper;
import ru.nfm.calendar.dto.CalendarDto;
import ru.nfm.calendar.model.Calendar;

@Mapper(componentModel = "spring")
public interface CalendarUserMapper {
    CalendarDto toDto(Calendar calendar, boolean isActive);
}
