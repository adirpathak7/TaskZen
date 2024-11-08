/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen.services;

import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 *
 * @author Aditya Pathak R
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true); // true indicates multipart message

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // true for HTML content

            javaMailSender.send(message);
//            SimpleMailMessage mail = new SimpleMailMessage();
//            mail.setTo(to);
//            mail.setSubject(subject);
//            mail.setText(body, true);
//            
////            mail.setFrom("adipathak7488@gmail.com");
//            javaMailSender.send(mail);
        } catch (Exception ex) {
            System.err.println("Exception while sending email! " + ex.getMessage());
        }
    }
}
