package ru.nfm.calendar.dto;

public record UserProfileDto(
        String email,
        String firstName,
        String lastName,
        String surName,
        String companyName,
        String position
) {
}
