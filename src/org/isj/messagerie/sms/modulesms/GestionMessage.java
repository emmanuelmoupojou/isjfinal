/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.isj.messagerie.sms.modulesms;


import org.isj.messagerie.sms.entities.Reponse;
import org.isj.messagerie.sms.entities.Requete;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.isj.metier.Isj;
import org.isj.metier.entites.Candidat;
import org.isj.metier.entites.Sms;
import org.isj.metier.facade.SmsFacade;
import org.smslib.InboundMessage;

import javax.persistence.NoResultException;

/**
 *
 * @author Channel
 */
public class GestionMessage {

    private static final String GROUPE_DESIGNER = "NOM_GROUPE";
    public static final String SEPARATEUR = "#";
    public static final String SEPARATE= ";";
    public GestionMessage() {
    }

    public void envoiMessagePersonnel(ArrayList<Reponse> listMsgPersonnel) {

        InteractionModem interactionModem = new InteractionModem();
        for (Reponse msgP : listMsgPersonnel) {
            interactionModem.envoiDuMessage(msgP.getContenu(), msgP.getTelPhon());
        }
    }

     /*public void envoiMessageGroupe(String messageText, ArrayList<String> listeTelephones) {

        Service.getInstance().createGroup(GROUPE_DESIGNER);
        for (String phoneParent : listeTelephones) {
            Service.getInstance().addToGroup(GROUPE_DESIGNER, phoneParent);
        }
        InteractionModem interactionModem = new InteractionModem();
        interactionModem.envoiDuMessage(messageText, GROUPE_DESIGNER);

    }*/

    public ArrayList<Requete> lireSms() {

        ArrayList<Requete> listeMsgRetour = new ArrayList<>();

        InteractionModem interactionModem = new InteractionModem();
        // apres chaque lecture les message sont supprimés
        List<InboundMessage> listNvMessage = interactionModem.lectureSMS();

        for (InboundMessage ibMssgCount : listNvMessage) {
            Requete R = spliteurMessage(ibMssgCount);
            listeMsgRetour.add(R);

            for (int i = 0; i < R.getCodeUE().length; i++) {
                System.out.println("\n ********nouveau message: ******** \n"
                        + "Code Secret: " + R.getCodesecret() + "\n"
                        + "code UE: " + R.getCodeUE()[i] + "\n"
                        + "Type: " + R.gettype() + "\n"
                );
            }
        }

        return listeMsgRetour;
    }

    // interfaces pour but de prendre un message dans son etat brut et le transformer en un objet de la classe  Requete
    /**
     * fonction spliteurMessage permet de découpper le message en bloc
     * @param IbMsg(le message reçu )


     */
    public static Requete spliteurMessage(InboundMessage IbMsg) {
        String msg = IbMsg.getText();


        String matricule = "";
        String codesecret = "";
        String type = "";
        String niveau = "";
        String codeUE = "";
        String filiere = "";
        Date date = IbMsg.getDate();
        String num = "";
        boolean isCorrectMsg = false;
        num = IbMsg.getOriginator();
        String[] listeUe = null;
        Requete R = new Requete(matricule, codesecret, type,  niveau, listeUe, filiere, num, date, isCorrectMsg);
        Isj isj = new Isj();
        try {
            Candidat candidat = isj.retrouverCandidatSms(Integer.valueOf(num.substring(3)));
            Sms sms = new Sms("","",msg,String.valueOf(693222744),num);
            SmsFacade smsFacade = new SmsFacade();
            smsFacade.create(sms);
            isj.sauvegarderSmsSucces(sms,candidat);
        }catch (NoResultException n){
            n.printStackTrace();
        }
        if (msg.contains(SEPARATEUR) && msg.contains(SEPARATE)) {
            Pattern separateur = Pattern.compile(SEPARATEUR);
            String[] items = separateur.split(msg);

            try {
                codesecret = items[0];
                codeUE = items[1];
                type = items[2];
                isCorrectMsg = true;
               Pattern separate = Pattern.compile(SEPARATE);
               listeUe = separateur.split(codeUE);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        if (!codesecret.equalsIgnoreCase("") && !codeUE.equalsIgnoreCase("")
             && !type.equalsIgnoreCase("")) {
            R = new Requete(matricule, codesecret, type, niveau, listeUe, filiere, num, date, isCorrectMsg);
        }

        return R;
    }

    public static void main(String[] args) {

        /**
         * ****** exemple d'utilisation! *******
         */
        // toujour activer le service au debut:
       InteractionModem im = new InteractionModem();
        im.activerService();
//
//        //crer un objet de la classe Gestion message:
        GestionMessage gm = new GestionMessage();
        
        
       // lecture des messages
        ArrayList<Requete> nouveauxMessages = gm.lireSms();
        ArrayList<Reponse> reponseList = new ArrayList<>();
        SmsFacade smsFacade = new SmsFacade();
        Sms sms = new Sms();
        Isj isj = new Isj();
        Candidat candidat = new Candidat();
        // on parcourt tous les messages recupérés de la SIM
        for (Requete rqt : nouveauxMessages) {
            Reponse rep = new Reponse();
            rep.setTitre("Reponse du logiciel \n");
            rep.setContenu("message incorrect respectez le format suivant : codesecret#codeUE(pour plusieurs UE séparées les par des points virgules(;))#type d'évaluation(CC,SN ou TP)");
            rep.setTelPhon(rqt.getNum());
            candidat = isj.retrouverCandidatSms(Integer.valueOf(rqt.getNum().substring(3)));
            sms = new Sms(rep.getTitre(),"",rep.getContenu(),rqt.getNum(),String.valueOf(693222744));

            //on vérifi si le message respecte le format requit
            if (rqt.isMessageOk()) {
                String Arepondre = "UE1: 12.5 \n"
                        + "UE2: 17 "
                        + "UE3: 14 \n";
                /*ici on appelle la méthode fournie qui permet d'éffectuer une
                 requette dans la base de donnée pour recupérer les notes de l'étudiant
                
                 Arepondre = recuperationNotesEtudiant(rqt.getMatricule(), rqt.getCodeUE(), rqt.Type());
                 */
                
                rep.setContenu(Arepondre);
                sms.setContenu(Arepondre);
            }
            reponseList.add(rep);
        }
        // on envoie la réponse aux differents concernés
        smsFacade.create(sms);
        isj.sauvegarderSmsSucces(sms,candidat);
       gm.envoiMessagePersonnel(reponseList);

       
       
//        //envoie de message de groupe
//        String msg = "bonjour chers parents";
//        ArrayList<String> telParent = new ArrayList<>();
//        telParent.add("5666677");
//        telParent.add("7554731");
//        gm.envoiMessageGroupe(msg, telParent);

        // envoie des message differents aux personnes
        /*ArrayList<MessagePersonnel> msp = new ArrayList<>();
         msp.add(new MessagePersonnel("691621708", "BONSOIR  "));
         // msp.add(new MessagePersonnel( "54566677","message"));
         gm.envoiMessagePersonnel(msp);
 
         // toujours stopper le service à la fin
         */
        im.stopperService();

    }

}
