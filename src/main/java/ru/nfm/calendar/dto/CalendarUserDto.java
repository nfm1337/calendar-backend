package ru.nfm.calendar.dto;

public record CalendarUserDto(
        int userId,
        String email,
        String firstName,
        String lastName
) {
}
