package org.isj.traitementmetier.facade;

import org.isj.traitementmetier.entites.Candidat;
import org.isj.traitementmetier.entites.Enseignement;
import org.isj.traitementmetier.entites.EstInscrit;

import java.util.List;

public class EstInscritFacade extends AbstractFacade<EstInscrit> {

    public EstInscritFacade(){
        super(EstInscrit.class);
    }

    public String enregistrer(String libelle, String description, EstInscrit.Statut statut, Candidat candidatInscrit, Enseignement enseignement){
        EstInscrit estInscrit = new EstInscrit(libelle,description,statut,candidatInscrit,enseignement);
        return create(estInscrit);
    }

    public String modifier(EstInscrit estInscrit, String libelle, String description, EstInscrit.Statut statut, Candidat candidatInscrit, Enseignement enseignement){
        estInscrit.setLibelle(libelle);
        estInscrit.setDescription(description);
        estInscrit.setStatut(statut);
        estInscrit.setEnseignement(enseignement);
        estInscrit.setCandidatInscrit(candidatInscrit);
        return merge(estInscrit);
    }

    public String supprimer(EstInscrit estInscrit){
        return remove(estInscrit);
    }

    public List<EstInscrit> lister(){
        return findAll();
    }

    public List<EstInscrit> lister(String requete){
        return findAllNative(requete);
    }
}
