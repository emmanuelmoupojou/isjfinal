package org.isj.messagerie.sms1;;

import org.isj.messagerie.sms1.Queue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Decodeur {


    /**
     * constructeur par defaut
     */
    public Decodeur() {
    }

    /**
     * @param sms sms � decoder et � partir duquel on va generer la requete
     */
    public Decodeur(Sms sms) {
    }

    /**
     * cette fonction verifie si le message de la requete respecte le format
     * requis
     *
     * @return true si message conforme; faux sinon
     */
    public static Boolean verifie_format_sms(String msg) {

        //  compilation de la regex
        //il reste encore � definir la bonne expression reguliere pour le format d'un sms
        //Pattern p =  Pattern.compile("#[0-9a-zA-Z]{6}[[*][1-5]]+?[[*][0-9a-zA-Z]]{2,4}#$");
        Pattern p = Pattern.compile("^#[0-9a-zA-Z]{6}([*][1-5]){0,1}([*][a-zA-Z]{2,4}){0,1}#$"); //bon
        //Pattern p =  Pattern.compile("^[*][1-5]{1}");
        //  cr�ation d�un moteur de recherche
        Matcher m = p.matcher(msg);
        //  lancement de la recherche de toutes les occurrences
        if (!m.matches()) {
            System.out.println(" \n>>>>>>>>>>>>>>>>>  echec verif");
            return false;
        }
        System.out.println(" \n>>>>>>>>>>>>>>>>>   verif ok");
        return true;
    }

    /*
     * consetruit la requette private void build_requete(){}
     *
     *
     *
     */
    public static void run(Sms sms, Reponse reponse, Queue f, boolean decoder) {
        Requete requete = new Requete();
        Sms sms_out = new Sms();
        /*
         * requete.setEmetteur("ici emetteur"); requete.setFiliere("ici
         * filiere"); requete.setMatricule("ici matricule");
         * requete.setNiveau(3);
         */
        if (decoder) {
            if (verifie_format_sms(sms.getMessage())) {
                //segmentation du msg
                int lg;
                String[] tabEltSms = {"", "", ""};//= sms.getMessage().split("*");
                System.out.println("\n le message ici est " + sms.getMessage());
                if (sms.getMessage().indexOf("*") != -1) {
                    tabEltSms = sms.getMessage().split("*");

                    lg = tabEltSms.length;
                    if (tabEltSms[0].indexOf("#") != -1) {
                        tabEltSms[0] = tabEltSms[0].substring(1);
                    }
                    if (tabEltSms[lg - 1].indexOf("#") != -1) {
                        tabEltSms[lg - 1] = tabEltSms[lg - 1].substring(0, tabEltSms[lg - 1].length() - 2);
                    }
                } else {
                    tabEltSms[0] = sms.getMessage();
                    tabEltSms[0] = tabEltSms[0].substring(1, 7);
                    lg = 1;
                }
                System.out.println("\n >>>>>>>>>>>> dans decodeur SMS =" + sms.getEmetteur() + "    " + sms.getMessage());
                requete.setEmetteur(sms.getEmetteur());

                //recuperation du code
                //le code est tabEltSms[0]

                int niveau = 1;

                try {
                    System.out.println("\nConnection au driver JDBC");
                    Class.forName("com.mysql.jdbc.Driver").newInstance();


                    String marequete = "select matricule,nom,prenom,niveau from comptes";
                    marequete += " where code='" + tabEltSms[0] + "' and statut ='1'";

                    try {
                        System.out.println("Connection a la base de donn�es");
                        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/notescomptes?", "root", "");
                        System.out.println("Base de donn�es connect�e\n");
                        Statement instruction = conn.createStatement();                                         //Cr�ation du statement	                                                                    // Entre ta requete ici
                        System.out.println("la requete est " + marequete);
                        ResultSet resultat = instruction.executeQuery(marequete);

                        if (resultat.next()) {
                            // System.out.println("j'entre ici");
                            // System.out.println("la taille est "+resultat.getFetchSize());
                            //System.out.println(resultat.getString(1)+"\t"+resultat.getFloat(2)+"\n");
                            niveau = resultat.getInt(4);
                            requete.setMatricule(resultat.getString(1));
                        } else {
                            return;
                        }
                        System.out.println("\n\nResultat niveau: 	" + niveau);
                    } catch (SQLException ex) {
                        // la connection a la base de donn�es n'a pas pu etre �tabli
                        // voici les codes erreurs retourn�s 
                        System.out.println("SQLException: " + ex.getMessage());
                        System.out.println("SQLState: " + ex.getSQLState());
                        System.out.println("VendorError: " + ex.getErrorCode());
                    }
                } catch (Exception ex) {
                    // Le driver n'a pas pu �tre charg�
                    // v�rifier que la variable CLASSPATH est bien renseign�e
                    System.out.println("Echec de chargement du driver");
                }




                if (lg == 3) {
                    requete.setType(tabEltSms[2].toLowerCase());
                    requete.setFiliere("INFORMATIQUE");
                    requete.setNiveau(Integer.parseInt(tabEltSms[1]));

                } else if (lg == 2) {
                    if (tabEltSms[1].length() == 1) {
                        requete.setNiveau(Integer.parseInt(tabEltSms[1]));
                        requete.setType("exam");
                    } else {
                        requete.setType(tabEltSms[1]);
                        requete.setNiveau(niveau);
                    }
                } else if (lg == 1) {
                    requete.setNiveau(niveau);
                    requete.setType("exam");

                }

                //recuperation 
                System.out.println("\n >>>>>>>>>>>>>>>>>>>>>>>>>>xx La requete est " + requete.getEmetteur() + "  :" + requete.getMatricule() + " :  " + requete.getType() + "\n");
                f.put(requete, "REQUETE");
            } else {
                System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  verification echouee non valide ");
            }

        } else {//ici on encode
            sms_out.setEmetteur(reponse.getEmetteur());
            String mysms = "";

            System.out.println("\n >>>>>>>>>>>>>>>>>>>>>>>>>> L'emetteur estff " + reponse.getEmetteur());

            for (int i = 0; i < reponse.getNbUE(); i++) {
                mysms = mysms.concat(reponse.getUE()[i]).concat(":").concat("" + reponse.getNote()[i]).concat(";");
            }

            if (mysms.equals("")) {
                mysms = "AUCUNE NOTE TROUVEE";
            }
            sms_out.setMessage(mysms);
            //requete.setEmetteur(sms.getEmetteur());
            System.out.println("\n >>>>>>>>>>>>>>>>>>>>>>>>>> Le sms out est " + sms_out.getEmetteur() + "  :" + sms_out.getMessage() + "\n");
            f.put(sms_out, "OUT_SMS");
        }
    }///fin de la methode run 

    public static void main(String[] args) {

        Decodeur dec = new Decodeur();
        System.out.println("fin enregistrement donn�es");
        int i = 0;
        String s;
        BufferedReader stdin = new BufferedReader(
                new InputStreamReader(System.in));

        while (true) {

            System.out.print("Enter a line:");
            try {
                s = stdin.readLine();
                if (Decodeur.verifie_format_sms(s)) {
                    System.out.println("Do match");
                } else {
                    System.out.println("Do not match");
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }



        }
    }
}
