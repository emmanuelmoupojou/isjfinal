package org.isj.messagerie.mail1;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEmail {
    private static final String senderEmail = "isjtestmail@gmail.com";//change with your sender email
    private static final String senderPassword = "testisj2019";//change with your sender password

    public static void sendAsHtml(String to, String title, String html) throws MessagingException {
        System.out.println("Envoi d'email à " + to);

        Session session = createSession();

        //create message using session
        MimeMessage message = new MimeMessage(session);
        prepareEmailMessage(message, to, title, html);

        //sending message
        Transport.send(message);
        System.out.println("Envoyé");
    }

    private static void prepareEmailMessage(MimeMessage message, String to, String title, String html)
            throws MessagingException {
        message.setContent(html, "text/html; charset=utf-8");
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(title);
    }

    private static Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");//Outgoing server requires authentication
        props.put("mail.smtp.starttls.enable", "true");//TLS must be activated
        props.put("mail.smtp.host", "smtp.gmail.com"); //Outgoing server (SMTP) - change it to your SMTP server landl
        props.put("mail.smtp.port", "587");//Outgoing port

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });
        return session;
    }

    public static void main(String[] args) throws MessagingException {
        SendEmail.sendAsHtml("touko.doline@gmail.com,keisper99@gmail.com,channeldonkeng@gmail.com,oyanestahnn@gmail.com,divine_elvira@yahoo.com", "Test email", "<h2>Salut</h2><p>Voilà notre premier mail et javax.mail marche.</p>");
    }
}
