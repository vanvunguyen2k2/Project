package com.example.project_job_seeking.Service;

import javax.mail.MessagingException;

public interface IMailSenderService {

    void sendSimpleMessage (String to, String subject, String text);

    void sendSimpleMessageWithHTML(String to, String subject, String text) throws MessagingException;
}
