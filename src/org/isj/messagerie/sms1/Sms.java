package org.isj.messagerie.sms1;;

import java.io.Serializable;

public class Sms
    implements Serializable
{

    private static final long serialVersionUID = 1L;
    private String emetteur;
    private String message;

    public Sms()
    {
        emetteur = "";
        message = "";
    }

    public Sms(String emetteur, String message)
    {
        this.emetteur = emetteur;
        this.message = message;
    }

    public String getEmetteur()
    {
        return emetteur;
    }

    public void setEmetteur(String emetteur)
    {
        this.emetteur = emetteur;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
