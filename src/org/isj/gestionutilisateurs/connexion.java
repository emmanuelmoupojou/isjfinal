package org.isj.gestionutilisateurs;

import org.isj.traitementmetier.entites.Droit;
import org.isj.traitementmetier.entites.Role;
import org.isj.traitementmetier.entites.Utilisateur;
import org.isj.traitementmetier.facade.UtilisateurFacade;
import org.isj.traitementmetier.Isj;
import java.security.MessageDigest;
import java.util.List;

public class connexion {

    static String toHexString(byte[] array) {
        StringBuilder sb = new StringBuilder(array.length * 2);

        for (byte b : array) {

            int value = 0xFF & b;
            String toAppend = Integer.toHexString(value);
            sb.append(toAppend).append("-");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString().toUpperCase();
    }

    public String hachage(String a) {
        String msgHash = null;
        try {

            MessageDigest md = MessageDigest.getInstance("sha-512");

            byte[] hash = md.digest(a.getBytes());
            //System.out.println("message:");
            msgHash = toHexString(hash);

            //System.out.println("message hash:" +msgHash);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return msgHash;

    }
    /*public Utilisateur authentification(String login, String password){
        String query = "SELECT * FROM utilisateur WHERE login like '"+login+"';";
        UtilisateurFacade uf = new UtilisateurFacade();
        List <Utilisateur> utilisateurs = uf.findAllNative(query);
        if(utilisateurs.get(0).getMotDePasse().equals(password)){
            return utilisateurs.get(0);
        }else {
            return null;
        }
    }*/

    Isj i = new Isj();
    public Utilisateur connect(String login, String mdp) {
        String hashmdp = hachage(mdp);
        Utilisateur d = i.authentification(login, hashmdp);
        if (d != null) {
            //d.getRoles();
            UtilisateurFacade uf = new UtilisateurFacade();
            Utilisateur utilisateur = uf.find(new Long(1));
            List<Role> roles = utilisateur.getRoles();
            for (int i = 0; i < roles.size(); i++) {
                System.out.println(roles.get(i).getLibelle());
                List<Droit> droits = roles.get(i).getDroits();
                for (int j = 0; j < droits.size(); j++) {
                    System.out.println(droits.get(j).toString());
                }
            }

        }
        return d;
    }
}

