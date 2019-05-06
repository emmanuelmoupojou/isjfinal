package org.isj.traitementmetier.facade;

import org.isj.traitementmetier.entites.Session;
import org.isj.traitementmetier.entites.Utilisateur;

import java.util.Date;
import java.util.List;

public class SessionFacade extends AbstractFacade<Session> {

    public SessionFacade(){
        super(Session.class);
    }

    public String enregistrer(String libelle, String description, Date dateConnection, Date dateDeconnection, Session.Statut statut, String machineCliente, Utilisateur utilisateur){
        Session session = new Session(libelle,description,dateConnection,dateDeconnection,utilisateur,statut,machineCliente);
        return create(session);
    }

    public String modifier(Session session, String libelle, String description, Date dateConnection, Date dateDeconnection, Session.Statut statut, String machineCliente,Utilisateur utilisateur){
        session.setLibelle(libelle);
        session.setDescription(description);
        session.setStatut(statut);
        session.setMachineCliente(machineCliente);
        session.setDateConnection(dateConnection);
        session.setDateDeconnection(dateDeconnection);
        session.setUtilisateur(utilisateur);
        return merge(session);
    }

    public String supprimer(Session session){
        return remove(session);
    }

    public List<Session> lister(){
        return findAll();
    }

    public List<Session> lister(String requete){
        return findAllNative(requete);
    }
}
