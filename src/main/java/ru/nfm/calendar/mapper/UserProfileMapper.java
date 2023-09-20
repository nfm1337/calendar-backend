package ru.nfm.calendar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nfm.calendar.dto.UserProfileDto;
import ru.nfm.calendar.model.UserProfile;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    @Mapping(source = "user.email", target = "email")
    UserProfileDto toDto(UserProfile user);
}
