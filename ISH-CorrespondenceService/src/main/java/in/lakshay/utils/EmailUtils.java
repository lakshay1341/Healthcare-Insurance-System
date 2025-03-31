package in.lakshay.utils;

import java.io.File;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtils {
    @Autowired
    private JavaMailSender sender;

    public void sendMail(String email, String subject, String body, File file) throws Exception {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setSentDate(new Date());
        helper.setSubject(subject);
        helper.setText(body, true);
        helper.addAttachment(file.getName(), file);
        sender.send(message);
    }

}
