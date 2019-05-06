package org.isj.traitementmetier.facade;

import org.isj.traitementmetier.entites.Email;
import org.isj.traitementmetier.entites.EnvoiMessage;

import java.util.List;

public class EmailFacade extends AbstractFacade<Email> {

    public EmailFacade(){
        super(Email.class);
    }

    public String enregistrer(String libelle, String description, String contenu, String destinataire, String emetteur,String objet){
        Email email = new Email(libelle,description,contenu,destinataire,emetteur,objet);
        return create(email);
    }

    public String modifier(Email email,String libelle,String description,String contenu,String destinataire,String emetteur,String objet, EnvoiMessage envoiMessage){
        email.setLibelle(libelle);
        email.setDescription(description);
        email.setContenu(contenu);
        email.setDestinataire(destinataire);
        email.setEmetteur(emetteur);
        email.setObjet(objet);
        email.setEnvoiMessage(envoiMessage);
        return merge(email);
    }

    public String supprimer(Email classe){
        return remove(classe);
    }

    public List<Email> lister(){
        return findAll();
    }

    public List<Email> lister(String requete){
        return findAllNative(requete);
    }
}
