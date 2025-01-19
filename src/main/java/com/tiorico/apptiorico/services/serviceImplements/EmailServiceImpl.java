package com.tiorico.apptiorico.services.serviceImplements;

import com.tiorico.apptiorico.services.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService
{
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String subject, String token) {
        try {
            // Construir la URL dinámica para el front-end
            String resetPasswordUrl = "http://localhost:4200/reset-password/" + URLEncoder.encode(token, StandardCharsets.UTF_8);

            // Crear el cuerpo HTML del correo
            String emailBody = "<h1>Solicitud de Restablecimiento de Contraseña</h1>"
                    + "<p>Has solicitado restablecer tu contraseña. Haz clic en el siguiente enlace para proceder:</p>"
                    + "<a href='" + resetPasswordUrl + "' style='color: #1d72b8;'>Restablecer contraseña</a>"
                    + "<p>Si no has solicitado esta acción, simplemente ignora este correo.</p>"
                    + "<p>Gracias,<br>El equipo de soporte de Tiorico.</p>";

            // Crear el mensaje con cuerpo HTML
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(emailBody, true);
            helper.setFrom("tu-email@gmail.com");

            // Enviar el correo
            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Error al enviar el correo electrónico", e);
        }
    }
}