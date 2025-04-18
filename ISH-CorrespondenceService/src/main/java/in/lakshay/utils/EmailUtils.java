package in.lakshay.utils;

import java.io.File;
import java.util.Date;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtils {
    private static final Logger logger = Logger.getLogger(EmailUtils.class.getName());

    @Autowired
    private JavaMailSender sender;

    public void sendMail(String email, String subject, String body, File file) throws Exception {
        try {
            logger.info("Preparing to send email to: " + email);

            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSentDate(new Date());
            helper.setSubject(subject);
            helper.setText(body, true);

            if (file != null && file.exists()) {
                logger.info("Adding attachment: " + file.getName() + " (" + file.length() + " bytes)");
                helper.addAttachment(file.getName(), file);
            } else {
                logger.warning("Attachment file is null or does not exist");
            }

            logger.info("Sending email to: " + email);
            sender.send(message);
            logger.info("Email sent successfully to: " + email);
        } catch (MailException | MessagingException e) {
            logger.severe("Failed to send email: " + e.getMessage());
            throw new Exception("Failed to send email: " + e.getMessage(), e);
        }
    }
}
