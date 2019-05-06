package org.isj.messagerie.sms1;;

import java.io.Serializable;

public class Requete
    implements Serializable
{

    private String emetteur;
    private String matricule;
    private String filiere;
    private int niveau;
    private String type;

    public Requete()
    {
        emetteur = null;
        matricule = null;
        filiere = null;
        niveau = 1;
        type = null;
    }

    public Requete(String emetteur, String matricule, String filiere, int niveau, String type)
    {
        this.emetteur = emetteur;
        this.matricule = matricule;
        this.filiere = filiere;
        this.niveau = niveau;
        this.type = type;
    }

    public String getEmetteur()
    {
        return emetteur;
    }

    public void setEmetteur(String emetteur)
    {
        this.emetteur = emetteur;
    }

    public String getMatricule()
    {
        return matricule;
    }

    public void setMatricule(String matricule)
    {
        this.matricule = matricule;
    }

    public String getFiliere()
    {
        return filiere;
    }

    public void setFiliere(String filiere)
    {
        this.filiere = filiere;
    }

    public int getNiveau()
    {
        return niveau;
    }

    public void setNiveau(int niveau)
    {
        this.niveau = niveau;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}
