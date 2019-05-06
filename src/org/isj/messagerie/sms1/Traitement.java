package org.isj.messagerie.sms1;;

import org.isj.messagerie.sms1.Queue;
import java.sql.*;

public class Traitement
{
    

    private static String coderequete = new String();
    private static String typerequete = new String();
    private static int niveaurequete = 0;
    private static String resultatrequete = new String();
    private static String emetteur;
    private static String matricule;
    private static String filiere;

    public Traitement(Requete R)
    {
        emetteur = R.getEmetteur();
        typerequete = R.getType();
        matricule = R.getMatricule();
        niveaurequete = R.getNiveau();
        filiere = R.getFiliere();
    }

    private static String MakeInterrogation()
    {
        String chaine = new String();
        if(typerequete.equalsIgnoreCase("exam"))
        {
            chaine = "select n.code_ue,n.note_ex from note_examen n,ue u ";
            chaine = (new StringBuilder(String.valueOf(chaine))).append("where n.code_ue =u.code_ue and u.niveau ='").append(niveaurequete).append("' and matricule= '").append(matricule).append("' order by code_ue").toString();
        } else
        if(typerequete.equalsIgnoreCase("cc"))
        {
            chaine = "select n.code_ue,n.note_ccs from note_cc n,ue u ";
            chaine = (new StringBuilder(String.valueOf(chaine))).append("where n.code_ue =u.code_ue and u.niveau ='").append(niveaurequete).append("' and matricule= '").append(matricule).append("' order by code_ue").toString();
        } else
        if(typerequete.equalsIgnoreCase("tp"))
        {
            chaine = "select n.code_ue,n.note_tps from note_tp n,ue u ";
            chaine = (new StringBuilder(String.valueOf(chaine))).append("where n.code_ue =u.code_ue and u.niveau ='").append(niveaurequete).append("' and matricule= '").append(matricule).append("' order by code_ue").toString();
        } else
        {
            System.out.println("\nRequete Vide.");
        }
        return chaine;
    }

    static void consultation(Reponse reponse)
    {
        reponse.setEmetteur(emetteur);
        try
        {
            System.out.println("\nConnection au driver JDBC");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("Driver com.mysql.jdbc.Driver charg\351");
            String marequete = MakeInterrogation();
            try
            {
                System.out.println("Connection a la base de donn\351es");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/NotesComptes?", "root", "");
                System.out.println("Base de donn\351es connect\351e\n");
                Statement instruction = conn.createStatement();
                System.out.println((new StringBuilder("la requete est ")).append(marequete).toString());
                ResultSet resultat = instruction.executeQuery(marequete);
                System.out.println("\nUE\tNOTE\t\n");
                for(; resultat.next(); reponse.addNote(resultat.getString(1), resultat.getFloat(2)))
                {
                    System.out.println((new StringBuilder(String.valueOf(resultat.getString(1)))).append("\t").append(resultat.getFloat(2)).append("\n").toString());
                }

            }
            catch(SQLException ex)
            {
                System.out.println((new StringBuilder("SQLException: ")).append(ex.getMessage()).toString());
                System.out.println((new StringBuilder("SQLState: ")).append(ex.getSQLState()).toString());
                System.out.println((new StringBuilder("VendorError: ")).append(ex.getErrorCode()).toString());
            }
        }
        catch(Exception ex)
        {
            System.out.println("Echec de chargement du driver");
        }
    }

    public static void run(Requete req, Queue f)
    {
        Reponse reponse = new Reponse();
        emetteur = req.getEmetteur();
        typerequete = req.getType();
        matricule = req.getMatricule();
        filiere = req.getFiliere();
        niveaurequete = req.getNiveau();
        consultation(reponse);
        f.put(reponse, "REPONSE");
    }

    public static void main(String args[])
    {
        Requete r = new Requete();
        r.setEmetteur("33445566");
        r.setFiliere("INF");
        r.setMatricule("05T379");
        r.setNiveau(1);
        r.setType("exam");
        Traitement t = new Traitement(r);
        run(r, null);
    }

}
