package com.streamingflix.serraflixgrupo5.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class MailConfig {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(remetente);
        message.setTo(to);
        message.setSubject(subject);
        message.setText("Cadastro realizado com sucesso! Bem-vindo ao Serraflix! \n" + text
                        + "\n\nAtenciosamente,\nEquipe Serraflix - Grupo 5");

        mailSender.send(message);
    }
}