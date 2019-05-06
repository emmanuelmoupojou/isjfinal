package org.isj.messagerie.sms1;

import org.isj.messagerie.sms1.Queue;
import java.io.IOException;
import org.smslib.GatewayException;
import org.smslib.TimeoutException;
import org.isj.messagerie.sms1.Sms;



public class Mythread extends Thread
{

    Queue f;
    Requete req;
   Reponse rep;
    Sms smsin;
    Sms smsout;
    Gateway gateway;

    public Mythread(Queue f, Gateway gateway)
    {
        req = new Requete("", "05t379", "INF", 4, "exams");
        this.f = f;
        this.gateway = gateway;
        start();
    }

    public void run()
    {
        int index = 0;
        do
        {
//            System.out.println((new StringBuilder("\n>>>>>>>>>>>>>>>>>>>>>>>>>> RONDE N\260 ")).append(index++).append("\n").toString());
            if(!f.isEmpty("IN_SMS"))
            {
                System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>> CONSTRUCTION DE L'OBJET REQUETE \n");
                Sms sms = (Sms)f.get("IN_SMS");
                System.out.println((new StringBuilder("\n>>>>>>>>>>>>SMS =")).append(sms.getEmetteur()).append("   ").append(sms.getMessage()).toString());
                Decodeur.run(sms, null, f, true);
            }
            if(!f.isEmpty("REQUETE"))
            {
                System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>> CONSTRUCTION DE L'OBJET REPONSE \n");
                Requete req = (Requete)f.get("REQUETE");
                Traitement.run(req, f);
            }
            if(!f.isEmpty("REPONSE"))
            {
                System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>> CONSTRUCTION DE L'OBJET SMS out \n");
                Reponse rep = (Reponse)f.get("REPONSE");
                Decodeur.run(null, rep, f, false);
                System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>> SORTIE  CONSTRUCTION DE L'OBJET SMS out \n");
            }
            if(!f.isEmpty("OUT_SMS"))
            {
                System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>> COMMMANDE D'ENVOI DE SMS \n");
                try
                {
                    System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>> COMMMANDE D'ENVOI DE SMS \n");
                    gateway.sendMessage();
                }
                catch(TimeoutException e)
                {
                    e.printStackTrace();
                }
                catch(GatewayException e)
                {
                    e.printStackTrace();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            try
            {
                sleep(3000L);
            }
            catch(InterruptedException e)
            {
                System.err.println("\nInterrupted\n");
            }
        } while(true);
    }
}
