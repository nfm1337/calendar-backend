package ru.nfm.calendar.dto;

public record EmailDetails(
        String recipient,
        String msgBody,
        String subject,
        String attachment
) {
}
