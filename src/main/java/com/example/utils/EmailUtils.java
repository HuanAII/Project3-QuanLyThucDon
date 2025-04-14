package com.example.utils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailUtils {
    private static final Logger logger = Logger.getLogger(EmailUtils.class.getName());
    private static final String FROM_EMAIL = "nhatphan.100405@gmail.com";
    private static final String EMAIL_PASSWORD = "0785702335";

    public static boolean sendResetPasswordEmail(String toEmail, String resetLink) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        try {
            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                @Override
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new javax.mail.PasswordAuthentication(FROM_EMAIL, EMAIL_PASSWORD);
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Reset Password");
            message.setText("Click vào link sau để reset password:\n\n" + resetLink);

            Transport.send(message);
            return true;

        } catch (MessagingException e) {
            logger.log(Level.SEVERE, "Error sending email", e);
            return false;
        }
    }
}
