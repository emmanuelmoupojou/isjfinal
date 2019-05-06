package org.isj.messagerie.sms1;


public class PortSerie
{

    private String nom;
    private int baud;

    public PortSerie()
    {
    }

    public String getNom()
    {
        return nom;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public int getBaud()
    {
        return baud;
    }

    public void setBaud(int baud)
    {
        this.baud = baud;
    }
}
