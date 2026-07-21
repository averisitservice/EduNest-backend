package com.edunest.service;

import com.edunest.error.CustomException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendPasswordResetEmail(String toEmail, String teacherName, String newPassword) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("EduNest - Your Password Has Been Reset");

            String displayName = (teacherName != null && !teacherName.isBlank()) ? teacherName : "Teacher";
            String html =
                    "<p>Dear <b>" + displayName + "</b>,</p>" +
                    "<p>Your password has been reset as requested.</p>" +
                    "<p>Your new temporary password is: <b>" + newPassword + "</b></p>" +
                    "<p>Please log in using this password and change it as soon as possible for your security.</p>" +
                    "<p>If you did not request this change, please contact your administrator immediately.</p>" +
                    "<p>Regards,<br>EduNest Team</p>";

            helper.setText(html, true);

            mailSender.send(message);
            log.info("Password reset email sent to {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send password reset email to {}", toEmail, e);
            throw new CustomException("Email", "Failed to send password reset email. Please try again later.");
        }
    }
}
