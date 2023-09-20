package ru.nfm.calendar.service;

import ru.nfm.calendar.dto.EmailDetails;

public interface EmailService {

    String sendSimpleMail(EmailDetails emailDetails);

    String sendMailWithAttachment(EmailDetails emailDetails);
}
