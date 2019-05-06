package org.isj.messagerie.sms1;

import java.io.Serializable;
import java.util.LinkedList;
import org.isj.messagerie.sms1.DeserialiserFile;
import org.isj.messagerie.sms1.Reponse;
import  org.isj.messagerie.sms1.Requete;
import  org.isj.messagerie.sms1.SerialiserFile;
import  org.isj.messagerie.sms1.Sms;
//import utils.*;

// Referenced classes of package dataManeger:
//            DeserialiserFile, SerialiserFile

public class Queue
    implements Serializable
{

    private static final long serialVersionUID = 1L;
    private LinkedList inSms;
    private LinkedList outSms;
    private LinkedList requete;
    private LinkedList reponse;

    public Queue()
    {
        inSms = new LinkedList();
        outSms = new LinkedList();
        requete = new LinkedList();
        reponse = new LinkedList();
        Queue f;
        try
        {
            if((f = DeserialiserFile.doIt()) == null)
            {
                inSms = new LinkedList();
                outSms = new LinkedList();
                requete = new LinkedList();
                reponse = new LinkedList();
                System.out.println("stack 1");
            } else
            {
                inSms = f.getInSms();
                outSms = f.getOutSms();
                requete = f.getRequete();
                reponse = f.getReponse();
                System.out.println("stack 2");
            }
        }
        catch(Exception e)
        {
            inSms = new LinkedList();
            outSms = new LinkedList();
            requete = new LinkedList();
            reponse = new LinkedList();
            System.out.println("stack 3");
        }
    }

    public LinkedList getInSms()
    {
        return inSms;
    }

    public void setInSms(LinkedList inSms)
    {
        this.inSms = inSms;
    }

    public LinkedList getOutSms()
    {
        return outSms;
    }

    public void setOutSms(LinkedList outSms)
    {
        this.outSms = outSms;
    }

    public LinkedList getRequete()
    {
        return requete;
    }

    public void setRequete(LinkedList requete)
    {
        this.requete = requete;
    }

    public LinkedList getReponse()
    {
        return reponse;
    }

    public void setReponse(LinkedList reponse)
    {
        this.reponse = reponse;
    }

    public void put(Object v, String type)
    {
        if(type.compareToIgnoreCase("IN_SMS") == 0)
        {
            System.out.println("\n UN SMS ENTRANT");
            inSms.addFirst((Sms)v);
        } else
        if(type.compareToIgnoreCase("OUT_SMS") == 0)
        {
            outSms.addFirst((Sms)v);
        } else
        if(type.compareToIgnoreCase("REQUETE") == 0)
        {
            requete.addFirst((Requete)v);
        } else
        if(type.compareToIgnoreCase("REPONSE") == 0)
        {
            reponse.addFirst((Reponse)v);
        }
        SerialiserFile.doIt(this);
        System.out.println("enreg->");
    }

    public Object get(String type)
    {
        Object o;
        if(type.compareToIgnoreCase("IN_SMS") == 0)
        {
            System.out.println("cas sms entrant");
            o = (Sms)inSms.removeLast();
        } else
        if(type.compareToIgnoreCase("OUT_SMS") == 0)
        {
            o = (Sms)outSms.removeLast();
            System.out.println("cas sms sortant");
        } else
        if(type.compareToIgnoreCase("REQUETE") == 0)
        {
            o = (Requete)requete.removeLast();
            System.out.println("cas req");
        } else
        {
            o = (Reponse)reponse.removeLast();
            System.out.println("cas autre");
        }
        SerialiserFile.doIt(this);
        return o;
    }

    public boolean isEmpty(String type)
    {
        if(type.compareToIgnoreCase("IN_SMS") == 0)
        {
            return inSms.isEmpty();
        }
        if(type.compareToIgnoreCase("OUT_SMS") == 0)
        {
            return outSms.isEmpty();
        }
        if(type.compareToIgnoreCase("REQUETE") == 0)
        {
            return requete.isEmpty();
        } else
        {
            return reponse.isEmpty();
        }
    }

    public static void main(String args[])
    {
        Queue queue = new Queue();
        System.out.println("fin enregistrement donn\351es");
        int i = 0;
        for(; !queue.isEmpty("IN_SMS"); System.out.println((new StringBuilder("fin affich elem ")).append(i++).toString()))
        {
            Sms sin = (Sms)queue.get("in_sms");
            Sms sout = (Sms)queue.get("out_sms");
            Requete req = (Requete)queue.get("requete");
            Reponse rep = (Reponse)queue.get("reponse");
            System.out.println((new StringBuilder(String.valueOf(sin.getEmetteur()))).append("  ").append(sin.getEmetteur()).append("  ").append(sin.getMessage()).toString());
            System.out.println((new StringBuilder(String.valueOf(sout.getEmetteur()))).append("  ").append(sout.getEmetteur()).append("  ").append(sout.getMessage()).toString());
            System.out.println((new StringBuilder("Requete ")).append(req.getEmetteur()).append("  ").append(req.getFiliere()).append("  ").append(req.getMatricule()).append(" ").append(req.getNiveau()).toString());
            System.out.println((new StringBuilder("Reponse ")).append(rep.getEmetteur()).append("  ").append(rep.getUE()[0]).append("  ").append(rep.getUE()[1]).append("  ").append(rep.getUE()[2]).append(rep.getNote().toString()).toString());
        }

    }
}
