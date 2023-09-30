package ru.nfm.calendar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nfm.calendar.dto.UserProfileDto;
import ru.nfm.calendar.model.UserProfile;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    @Mapping(source = "user.email", target = "email")
    UserProfileDto toDto(UserProfile user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "calendarUserList", ignore = true)
    @Mapping(target = "attachedEvents", ignore = true)
    void updateUserProfileFromDto(UserProfileDto userProfileDto, @MappingTarget UserProfile userProfile);
}
