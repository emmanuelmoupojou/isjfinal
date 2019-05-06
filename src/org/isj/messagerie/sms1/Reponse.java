package org.isj.messagerie.sms1;;

import java.io.Serializable;

public class Reponse
    implements Serializable
{

    private String emetteur;
    private String UE[];
    private float note[];
    int nbUE;

    public Reponse()
    {
        UE = new String[20];
        note = new float[20];
        nbUE = 0;
        emetteur = null;
    }

    public Reponse(String emetteur)
    {
        UE = new String[20];
        note = new float[20];
        nbUE = 0;
        this.emetteur = new String(emetteur);
    }

    public String getEmetteur()
    {
        return emetteur;
    }

    public void setEmetteur(String emetteur)
    {
        this.emetteur = emetteur;
    }

    public String[] getUE()
    {
        return UE;
    }

    public void setUE(String uE[])
    {
        UE = uE;
    }

    public float[] getNote()
    {
        return note;
    }

    public void setNote(float note[])
    {
        this.note = note;
    }

    public void addNote(String ue, float note)
    {
        try
        {
            UE[nbUE] = new String(ue);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        this.note[nbUE] = note;
        nbUE++;
    }

    public int getNbUE()
    {
        return nbUE;
    }

    public void increment()
    {
        nbUE++;
    }
}
