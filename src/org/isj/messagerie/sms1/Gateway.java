package org.isj.messagerie.sms1;

//import Queue;
import org.isj.messagerie.sms1.Queue;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

import org.smslib.*;
import org.smslib.InboundMessage.MessageClasses;
import org.smslib.crypto.AESKey;
import org.smslib.modem.SerialModemGateway;
import org.isj.messagerie.sms1.Sms;

public class Gateway {
    private int sms_max_chars=160;

    public Gateway() {
    }
    /*
     * public class InboundNotification implements IInboundMessageNotification {
     *
     * @Override public void process(AGateway gateway,
     * org.smslib.Message.MessageTypes msgType, InboundMessage msg) {
     *
     * // if (!msg.getOriginator().equals(lastNumber) ||
     * System.currentTimeMillis() - tps > 4000L) { // tps =
     * System.currentTimeMillis(); // lastNumber = msg.getOriginator(); if
     * (msg.getText() != null) { MyMessage m = new MyMessage(msg.getDate()); if
     * (!contient(listeMessagesLus, m)) { listeMessagesLus.add(m);
     * System.out.println("\n\nENTRANT : " + msg.getOriginator());
     * System.out.println("\nMESSAGE : " + msg.getText()); System.out.println("\nDATE : "
     * + msg.getDate());
     * output.setCaretPosition(output.getDocument().getLength()); }
     *
     * }
     * // } } }
     *
     * private class OutboundNotification implements
     * IOutboundMessageNotification {
     *
     * public void process(AGateway gateway, OutboundMessage msg) {
     *
     * // if (!msg.getFrom().equals(lastNumber2) || System.currentTimeMillis()
     * - tps2 > 2000L) { if (msg.getText() != null) { MyMessage m = new
     * MyMessage(msg.getDate()); if (!contient(listeMessagesLus, m)) {
     * listeMessagesLus.add(m); tps2 = System.currentTimeMillis(); lastNumber2 =
     * msg.getFrom(); MessageStatuses status = msg.getMessageStatus();
     *
     * System.out.println("\n\nSORTANT : " + msg.getFrom()); System.out.println("\nMESSAGE
     * : " + msg.getText()); System.out.println("\nDATE : " + msg.getDate());
     *
     * if (MessageStatuses.SENT.compareTo(status) == 0) {
     * System.out.println("\nSTATUT : SUCCES"); IS_SENDING = false; } else if
     * (MessageStatuses.FAILED.equals(status)) { System.out.println("\nSTATUT :
     * ECHEC"); } else { System.out.println("\nSTATUT : NON ENVOYE"); }
     * output.setCaretPosition(output.getDocument().getLength()); } } // } } }
     */

    public Gateway(String comPort, int rate, Queue f, String fabricant, String modele)
            throws GatewayException, TimeoutException, IOException, InterruptedException {
        IS_SENDING = false;
        tps = System.currentTimeMillis();
        lastNumber = "";
        this.f = f;
        port = comPort;
        baudrate = rate;
        gateway = new SerialModemGateway("My_GSM_modem", port, baudrate, fabricant, modele);
        gateway.setProtocol(org.smslib.AGateway.Protocols.PDU);
        gateway.setInbound(true);
        gateway.setOutbound(true);
        gateway.setSimPin("0000");

//        Service.getInstance().setInboundMessageNotification(inboundNotification);
//        Service.getInstance().setOutboundMessageNotification(outboundNotification);

        if (Service.getInstance().getGateways() != null && Service.getInstance().getGateways().size() > 0) {
            Service.getInstance().removeGateway(Service.getInstance().getGateways().iterator().next());
        }

        Service.getInstance().addGateway(gateway);

//        System.out.println("\nNouveau gateway ajoute !");
        try {
//            System.out.println("\nDemarrage du service...");
//            System.out.print("\nDemarrage du service...");
            Service.getInstance().startService();
//            System.out.println("\nService demarre !");
//            System.out.print("\nService demarre !");
        } catch (TimeoutException e1) {
            e1.printStackTrace();
        } catch (SMSLibException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        Service.getInstance().getKeyManager().registerKey("+306948494037", new AESKey(new SecretKeySpec("0011223344556677".getBytes(), "AES")));
        System.out.println("\n\nINFORMATIONS SUR LE MOBILE GSM\n");
        try {
            System.out.println((new StringBuilder("\n\tFABRICANT        : ")).append(gateway.getManufacturer()).toString());
            System.out.println((new StringBuilder("\n\tMODELE           : ")).append(gateway.getModel()).toString());
            System.out.println((new StringBuilder("\n\tNo DE SERIE      : ")).append(gateway.getSerialNo()).toString());
            numero_telephone = gateway.getFrom();
//            System.out.println((new StringBuilder("\n\tNo DE TELEPHONE  : ").append(numero_telephone)).toString());
//            System.out.println((new StringBuilder("\n\tIMSI             : ")).append(gateway.getImsi()).toString());
            System.out.println((new StringBuilder("\n\tS/W Version      : ")).append(gateway.getSwVersion()).toString());
            System.out.println((new StringBuilder("\n\tNIVEAU BATTERIE  : ")).append(gateway.getBatteryLevel()).append("%").toString());
            System.out.println((new StringBuilder("\n\tFORCE DU SIGNAL  : ")).append(-gateway.getSignalLevel()).append("%\n").toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Telephone incompatible !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    public void startGateway()
            throws TimeoutException, GatewayException, IOException, InterruptedException {
        try {
            Service.getInstance().startService();
            Service.getInstance().getKeyManager().registerKey("+306948494037", new AESKey(new SecretKeySpec("0011223344556677".getBytes(), "AES")));
        } catch (SMSLibException e) {
            e.printStackTrace();
        }
    }

    public void stopGateway()
            throws TimeoutException, GatewayException, IOException, InterruptedException, SMSLibException {

        Service.getInstance().stopService();
        if (gateway != null) {
            gateway.stopGateway();
        }
    }

    public int sendMessage() throws TimeoutException, GatewayException, IOException, InterruptedException {
        IS_SENDING = true;
        sms = (Sms) f.get("OUT_SMS");
//        System.out.println((new StringBuilder("\nDestinataire = ")).append(sms.getEmetteur()).toString());
//        System.out.println((new StringBuilder("\nMessage = ")).append(sms.getMessage()).toString());
        return sendMessage(sms.getEmetteur(), sms.getMessage());
    }

    public int sendMessage(String tel, String text) throws TimeoutException, GatewayException, IOException, InterruptedException {
        IS_SENDING = true;
//        System.out.println((new StringBuilder("\nEnvoi du mot de passe au numero : ")).append(tel).toString());
        /*
         * if(tel.substring(0, 3).equalsIgnoreCase("237")) { tel =
         * tel.substring(3, tel.length()); }
         */

        System.out.println("\n\nSORTANT : " + tel);
        System.out.println("\nMESSAGE : " + text);
        System.out.println("\nDATE    : " + new Date().toString());

        boolean result=false;
        try {
            String message_part = "";
            int i = 0;
            while (i < text.length()) {
                int j = 0;
                message_part = "";
                while (j < sms_max_chars && i < text.length()) {
                    message_part = message_part + text.charAt(i);
                    i++;
                    j++;
                }
                result = gateway.sendMessage(new OutboundMessage(tel, message_part));
            }
            if (result) {
                System.out.println("\nSTATUT  : SUCCES");
                return 1;
            } else {
                System.out.println("\nSTATUT  : ECHEC");
                return 0;
            }
        } catch (Exception ex) {
//            System.out.println("\nECHEC D'ENVOI DU SMS.\n");
//            output.setCaretPosition(output.getDocument().getLength());
            return 0;
        }


    }

    public int sendMessageGroup(String[] destinataires, String message) throws TimeoutException, GatewayException, IOException, InterruptedException {
        OutboundMessage[] OM = new OutboundMessage[destinataires.length];

        for (int i = 0; i < destinataires.length; i++) {
            OM[i] = new OutboundMessage(destinataires[i], message);
        }

        IS_SENDING = true;
        if (Service.getInstance().sendMessages(OM) != -1) {
//            new JOptionPane().showMessageDialog(null, "SMS envoyes  (=-1)!", "Agence", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("\nSMS ENVOYES AVEC SUCCES.\n");
            return 1;
        } else {
//            new JOptionPane().showMessageDialog(null, "SMS envoyes  (#-1)!", "Agence", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("\nUn ou plusieurs SMS n'ont pas pu etre envoyes.\n");
        }
        return 0;


    }

    public void readMessages() {
        new ReadMessages().start();
    }

    class ReadMessages extends Thread implements Runnable {

        public void run() {
            while (true) {
                List msgList = new ArrayList();
                try {
                    Service.getInstance().readMessages(msgList, MessageClasses.ALL);
                } catch (TimeoutException ex) {
                    Logger.getLogger(Gateway.class.getName()).log(Level.SEVERE, null, ex);
                } catch (GatewayException ex) {
                    Logger.getLogger(Gateway.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Gateway.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Gateway.class.getName()).log(Level.SEVERE, null, ex);
                }
                InboundMessage msg;
                Iterator iterator = msgList.iterator();
                while (iterator.hasNext()) {
                    msg = (InboundMessage) iterator.next();
                    if (msg.getText() != null) {
                        MyMessage m = new MyMessage(msg.getDate());
                        if (!contient(listeMessagesLus, m)) {
                            listeMessagesLus.add(m);
                            System.out.println("\n\nENTRANT : " + msg.getOriginator());
                            System.out.println("\nMESSAGE : " + msg.getText());
                            System.out.println("\nDATE    : " + msg.getDate());
                        }
                    }
                }
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Gateway.class.getName()).log(Level.SEVERE, null, ex);
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    class MyMessage {

        Date date;
        String hextext = "";
        int messageencoding = 0;
        String originator = "";
        String text = "";
        int type = 0;

        public MyMessage(Date date, String hextext, int messageencoding, String originator, String text, int type) {
            this.date = date;
            this.hextext = hextext;
            this.messageencoding = messageencoding;
            this.originator = originator;
            this.text = text;
            this.type = type;
        }

        private MyMessage(Date date) {
            this.date = date;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getHextext() {
            return hextext;
        }

        public void setHextext(String hextext) {
            this.hextext = hextext;
        }

        public int getMessageencoding() {
            return messageencoding;
        }

        public void setMessageencoding(int messageencoding) {
            this.messageencoding = messageencoding;
        }

        public String getOriginator() {
            return originator;
        }

        public void setOriginator(String originator) {
            this.originator = originator;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    private Boolean contient(Set<MyMessage> messages, MyMessage message) {

        for (MyMessage msg : messages) {
            if (msg.getDate().toString().equals(message.getDate().toString())) {
                return true;
            }
        }
//        System.out.println("La liste ne contient pas l'element !");
        return false;
    }
    Set<MyMessage> listeMessagesLus = new HashSet();
    private SerialModemGateway gateway;
    private Sms sms;
    private Queue f;
    private String port;
    private int baudrate;
    private boolean IS_SENDING;
    private long tps;
    private String lastNumber2;
    private long tps2;
    private String lastNumber;
    public String numero_telephone;
//    InboundNotification inboundNotification = new InboundNotification();
//    OutboundNotification outboundNotification = new OutboundNotification();

    public SerialModemGateway getGateway() {
        return gateway;
    }
}
