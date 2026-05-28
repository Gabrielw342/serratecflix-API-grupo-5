package com.streamingflix.serraflixgrupo5.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.internet.MimeMessage;

@Configuration
public class MailConfig {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    public void sendEmail(String to, String subject, String nomeUsuario) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(remetente);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(buildHtml(nomeUsuario), true);

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar email: " + e.getMessage());
        }
    }

private String buildHtml(String nomeUsuario) {
    return "<!DOCTYPE html>" +
            "<html lang='pt-BR'><head><meta charset='UTF-8'></head>" +
            "<body style='margin:0; padding:0; background-color:#110B1A; font-family: Arial, sans-serif;'>" +
            "<table width='100%' cellpadding='0' cellspacing='0' style='background-color:#110B1A; padding: 40px 0;'>" +
            "<tr><td align='center'>" +
            "<table width='600' cellpadding='0' cellspacing='0' style='background-color:#0B053A; border-radius:12px; overflow:hidden;'>" +
            "<tr><td align='center' style='background-color:#0B053A; padding: 30px; border-bottom: 2px solid #51B9E4;'>" +
            "<h1 style='color:#51B9E4; margin:0; font-size:32px; letter-spacing:4px;'>🎬 SERRAFLIX</h1>" +
            "</td></tr>" +
            "<tr><td style='padding: 40px 50px; background-color:#0B053A;'>" +
            "<h2 style='color:#51B9E4; font-size:22px; margin-top:0;'>Bem-vindo(a), " + nomeUsuario + "!</h2>" +
            "<p style='color:#F5F7FA; font-size:15px; line-height:1.7;'>Seu cadastro foi realizado com sucesso na plataforma <strong style='color:#51B9E4;'>Serraflix</strong>. Agora você tem acesso ao nosso catálogo completo de filmes e séries.</p>" +
            "<p style='color:#F5F7FA; font-size:15px; line-height:1.7;'>Explore, avalie e adicione seus favoritos. Boa diversão!</p>" +
            "<table cellpadding='0' cellspacing='0' style='margin: 30px 0;'>" +
            "<tr><td align='center' style='background-color:#51B9E4; border-radius:6px; padding: 14px 32px;'>" +
            "<a href='http://localhost:8080' style='color:#0B053A; text-decoration:none; font-size:16px; font-weight:bold;'>Acessar a plataforma</a>" +
            "</td></tr></table>" +
            "</td></tr>" +
            "<tr><td align='center' style='background-color:#110B1A; padding: 20px; border-top: 2px solid #51B9E4;'>" +
            "<p style='color:#F5F7FA; font-size:12px; margin:0;'>© 2026 Serraflix - Grupo 5 · Serratec</p>" +
            "</td></tr></table></td></tr></table></body></html>";
    }
}
