package org.isj.traitementmetier.facade;

import org.isj.traitementmetier.entites.Candidat;
import org.isj.traitementmetier.entites.EnvoiMessage;
import org.isj.traitementmetier.entites.Message;

import java.util.Date;
import java.util.List;

public class EnvoiMessageFacade extends AbstractFacade <EnvoiMessage> {

    public EnvoiMessageFacade(){
        super(EnvoiMessage.class);
    }
    public String enregistrer(String libelle, String description, Date dateEnvoi, EnvoiMessage.Statut statut, Message message, Candidat candidat){
        EnvoiMessage envoiMessage = new EnvoiMessage(libelle,description,dateEnvoi,statut,message, candidat);
        return create(envoiMessage);
    }

    public String modifier(EnvoiMessage envoiMessage, String libelle, String description, Date dateEnvoi, EnvoiMessage.Statut statut,Message message, Candidat candidat){
        envoiMessage.setLibelle(libelle);
        envoiMessage.setDescription(description);
        envoiMessage.setDateEnvoi(dateEnvoi);
        envoiMessage.setStatut(statut);
        envoiMessage.setCandidat(candidat);
        envoiMessage.setMessage(message);
        return merge(envoiMessage);
    }

    public String supprimer(EnvoiMessage envoiMessage){
        return remove(envoiMessage);
    }

    public List<EnvoiMessage> lister(){
        return findAll();
    }

    public List<EnvoiMessage> lister(String requete){
        return findAllNative(requete);
    }
}
