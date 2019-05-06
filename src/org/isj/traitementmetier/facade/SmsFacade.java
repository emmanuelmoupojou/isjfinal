package org.isj.traitementmetier.facade;

import org.isj.traitementmetier.entites.EnvoiMessage;
import org.isj.traitementmetier.entites.Sms;

import java.util.List;

public class SmsFacade extends AbstractFacade<Sms> {

    public SmsFacade(){
        super(Sms.class);
    }

    public String enregistrer(String libelle, String description, String contenu, String destinataire, String emetteur) {
        Sms sms = new Sms(libelle,description,contenu,destinataire,emetteur);
        return create(sms);
    }

    public String modifier(Sms sms,String libelle,String description,String contenu,String destinataire,String emetteur){
        sms.setLibelle(libelle);
        sms.setDescription(description);
        sms.setContenu(contenu);
        sms.setDestinataire(destinataire);
        sms.setEmetteur(emetteur);
        return merge(sms);
    }

    public String supprimer(Sms sms){
        return remove(sms);
    }

    public List<Sms> lister(){
        return findAll();
    }

    public List<Sms> lister(String requete){
        return findAllNative(requete);
    }
}
