package com.streamingflix.serraflixgrupo5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmailVerificacao(String destino, String token) {

        String link =
            "http://localhost:8080/auth/verificar?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(destino);
        message.setSubject("Verifique seu email");
        message.setText(
            "Clique no link para verificar sua conta:\n" + link
        );

        mailSender.send(message);
    }
}
