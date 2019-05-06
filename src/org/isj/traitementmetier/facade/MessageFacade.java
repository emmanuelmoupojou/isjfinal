package org.isj.traitementmetier.facade;

import org.isj.traitementmetier.entites.EnvoiMessage;
import org.isj.traitementmetier.entites.Message;

import java.util.List;

public class MessageFacade extends AbstractFacade<Message> {

    public MessageFacade(){
        super(Message.class);
    }

    public String enregistrer(String libelle, String description, String contenu, String destinataire, String emetteur){
        Message message = new Message(libelle,description,contenu,destinataire,emetteur);
        return create(message);
    }

    public String modifier(Message message,String libelle,String description,String contenu,String destinataire,String emetteur){
        message.setLibelle(libelle);
        message.setDescription(description);
        message.setContenu(contenu);
        message.setDestinataire(destinataire);
        message.setEmetteur(emetteur);
        return merge(message);
    }

    public String supprimer(Message message){
        return remove(message);
    }

    public List<Message> lister(){
        return findAll();
    }

    public List<Message> lister(String requete){
        return findAllNative(requete);
    }
}
