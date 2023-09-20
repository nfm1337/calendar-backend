package ru.nfm.calendar.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.nfm.calendar.dto.EmailDetails;
import ru.nfm.calendar.service.EmailService;

@AllArgsConstructor
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private JavaMailSender mailSender;

    @Override
    public String sendSimpleMail(EmailDetails emailDetails) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@calendar.pro");
            message.setTo(emailDetails.recipient());
            message.setText(emailDetails.msgBody());
            message.setSubject(emailDetails.subject());

            mailSender.send(message);
            return "Email sent successfully";
        } catch (Exception e) {
            log.error("Error when sending email : " + e.getMessage());
            return "Error when sending email.";
        }
    }

    @Override
    public String sendMailWithAttachment(EmailDetails emailDetails) {
        return null;
    }
}
