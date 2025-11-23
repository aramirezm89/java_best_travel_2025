package com.debuggeando_ideas.best_travel_2025.infrastructure.helpers;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Component
@Slf4j
public class EmailHelper {

    private final JavaMailSender mailSender;
    @Value("${email_sender.address}")
    private String sender;

    public EmailHelper(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String name, String product) {
        MimeMessage message = mailSender.createMimeMessage();
        String htmlContent = this.readHTMLTemplate(name, product);

        try {
            message.setFrom(new InternetAddress(sender.trim()));
            message.setRecipients(MimeMessage.RecipientType.TO, to.trim());
            message.setContent(htmlContent, MediaType.TEXT_HTML_VALUE);
            mailSender.send(message);
            log.info("Email sent successfully to {}", to);
        } catch (MessagingException e) {
            log.error("Error sending email", e);
        }
    }

    /**
     * Lee una plantilla HTML desde el sistema de archivos y reemplaza los placeholders con valores dinámicos.
     * 
     * @param name Nombre del usuario que se insertará en el placeholder {name}
     * @param product Nombre del producto que se insertará en el placeholder {product}
     * @return String con el contenido HTML completo con los valores reemplazados
     * @throws RuntimeException si ocurre un error al leer el archivo de plantilla
     */
    private String readHTMLTemplate(String name, String product) {
        // Try-with-resources: garantiza que el stream de líneas se cierre automáticamente
        try (var lines = Files.lines(TEMPLATE_PATH)) {
            // Collectors.joining() une todas las líneas del archivo en un solo String
            var content = lines.collect(Collectors.joining());
            
            // Reemplaza los placeholders {name} y {product} con los valores reales
            return content.replace("{name}", name).replace("{product}", product);
        } catch (IOException e) {
            // Si falla la lectura del archivo, registra el error y propaga la excepción
            log.error("Cant read HTML template", e);
            throw new RuntimeException("Cant read HTML template", e);
        }
    }

    private final Path TEMPLATE_PATH = Paths.get("src/main/resources/email/email_template.html");

}
