package com.esprit.utils;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class AutoMail {

    public static void sendAutomatedEmail(String destination, String fullName) {
        String from = "abdelkader.abdelkhalek@esprit.tn";
        String password = "K!463665257416uv"; // Your regular email password

        String host = "smtp.office365.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destination));

            message.setSubject("Automated Email for " + fullName);
            message.setText("Hello " + fullName + ",\n\nThis is an automated email for you.");

            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
