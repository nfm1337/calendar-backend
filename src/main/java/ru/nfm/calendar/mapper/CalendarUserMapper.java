package ru.nfm.calendar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nfm.calendar.dto.CalendarUserDto;
import ru.nfm.calendar.model.CalendarUser;

@Mapper(componentModel = "spring")
public interface CalendarUserMapper {

    @Mapping(target = "userId", source = "userProfile.id")
    @Mapping(target = "firstName", source = "userProfile.firstName")
    @Mapping(target = "lastName", source = "userProfile.lastName")
    @Mapping(target = "email", source = "userProfile.user.email")
    CalendarUserDto toDto(CalendarUser calendarUser);
}
