package org.isj.messagerie.mail1;


/**
 *
 * @author cons
 */


import java.io.BufferedOutputStream;
import java.io.BufferedReader;

import java.io.DataOutputStream;
import java.io.File;
import static java.io.File.separator;
import static java.io.File.separator;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.util.Date;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Message.RecipientType;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;

public class ReceiveMail {
    private static Message Message;

    public static void check(String host, String storeType, String user,
                             String password)

    {
        try {

            //create properties field
            Properties properties = new Properties();

            properties.put("mail.pop3.host", host);
            properties.put("mail.pop3.port", "993");
            properties.put("mail.pop3.starttls.enable", "true");
            Session emailSession = Session.getDefaultInstance(properties);

            //create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("pop3s");

            store.connect(host, user, password);

            //create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in));

            // retrieve the messages from the folder in an array and print it

            Message[] messages = emailFolder.getMessages();
            if (messages.length==0){
                System.out.println("Vous avez lu tout vos message");
            }else{
                System.out.println("messages.length---" + messages.length);

            }

            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                System.out.println("---------------------------------");

                System.out.println("Message n° " + (i+1));
                System.out.println("Objet : " + message.getSubject());
                //Objets=(String) message.getSubject();

                System.out.println("Expéditeur : ");
                Address[] addresses = message.getFrom();
                for (Address address : addresses) {
                    System.out.println("\t" + address);
                    //Exp=(String) message.getFrom();
                }

                writePart(message);
                String line = reader.readLine();
                if ("YES".equals(line)) {
                    message.writeTo(System.out);
                } else if ("QUIT".equals(line)) {
                    break;
                }

                // retrieve the messages from the folder in an array and print it

            }
            //close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writePart(Part p) throws Exception {
        if (p instanceof Message)
            //Call methos writeEnvelope
            //writeEnvelope((Message) p);

            System.out.println("----------------------------");
        System.out.println("CONTENT-TYPE: " + p.getContentType());

        //check if the content is plain text
        if (p.isMimeType("text/plain")) {
            System.out.println("This is plain text");
            System.out.println("---------------------------");
            System.out.println((String) p.getContent());
        }
        //check if the content has attachment
        else if (p.isMimeType("multipart/*")) {
            System.out.println("This is a Multipart");
            System.out.println("---------------------------");
            Multipart mp = (Multipart) p.getContent();
            int count = mp.getCount();
            for (int i = 0; i < count; i++)
                writePart(mp.getBodyPart(i));
        }
        //check if the content is a nested message
        else if (p.isMimeType("message/rfc822")) {
            System.out.println("This is a Nested Message");
            System.out.println("---------------------------");
            writePart((Part) p.getContent());
            //Con=(String) p.getContent();
        }

        //check if the content is an inline image
        else {
            Object o = p.getContent();
            if (o instanceof String) {
                System.out.println("This is a string");
                System.out.println("---------------------------");
                System.out.println((String) o);
            }
            else if (o instanceof InputStream) {
                System.out.println("This is just an input stream");
                System.out.println("---------------------------");
                InputStream is = (InputStream) o;
                is = (InputStream) o;
                int c;
                while ((c = is.read()) != -1)
                    System.out.write(c);
            }
            else {
                System.out.println("This is an unknown type");
                System.out.println("---------------------------");
                System.out.println(o.toString());
            }
        }

    }
    //lecture du contenu d'un mail

    //Classe principale
    public static void main(String[] args) {

        String host = "pop.gmail.com";// change accordingly
        String mailStoreType = "pop3";
        String username ="isjtestmail@gmail.com";// change accordingly
        String password = "testisj2019";// change accordingly

        check(host, mailStoreType, username, password);

    }

}
