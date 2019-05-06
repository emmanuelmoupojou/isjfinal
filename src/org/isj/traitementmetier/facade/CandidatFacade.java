package org.isj.traitementmetier.facade;

import org.isj.traitementmetier.entites.Candidat;
import org.isj.traitementmetier.entites.Classe;
import org.isj.traitementmetier.entites.Personne;

import java.util.Date;
import java.util.List;

public class CandidatFacade extends AbstractFacade<Candidat> {
    public CandidatFacade(){
        super(Candidat.class);
    }

    public String enregistrer(String libelle, String description, String nom, String prenom, String email, int telephone, Date dateNaissance, Personne.Sexe sexe, Personne.Statut statut, String nomMere, String nomPere, int telephoneMere, int telephonePere, String professionPere, String professionMere, String regionOrigine, String ecoleOrigine, Classe classe){
        Candidat candidat = new Candidat(libelle,description,nom,prenom,email,telephone,dateNaissance,sexe,statut,nomMere,nomPere,telephoneMere,telephonePere,professionPere,professionMere,regionOrigine,ecoleOrigine,classe);
        return create(candidat);
    }

    public String modifier(Candidat candidat,String libelle, String description, String nom, String prenom, String email, int telephone, Date dateNaissance, Personne.Sexe sexe, Personne.Statut statut,String nomMere,String nomPere,int telephoneMere,int telephonePere,String professionPere,String professionMere,String regionOrigine,String ecoleOrigine,Classe classe){
        candidat.setLibelle(libelle);
        candidat.setDescription(description);
        candidat.setNom(nom);
        candidat.setPrenom(prenom);
        candidat.setEmail(email);
        candidat.setTelephone(telephone);
        candidat.setDateNaissance(dateNaissance);
        candidat.setSexe(sexe);
        candidat.setStatut(statut);
        candidat.setNomDeLaMere(nomMere);
        candidat.setNomDuPere(nomPere);
        candidat.setTelephoneDeLaMere(telephoneMere);
        candidat.setTelephoneDuPere(telephonePere);
        candidat.setProfessionDuPere(professionPere);
        candidat.setProfessionDelaMere(professionMere);
        candidat.setRegionOrigine(regionOrigine);
        candidat.setEcoleOrigine(ecoleOrigine);
        candidat.setClasse(classe);
        return merge(candidat);
    }

    public String supprimer(Candidat candidat){
        return remove(candidat);
    }

    public List<Candidat> lister(){
        return findAll();
    }

    public List<Candidat> lister(String requete){
        return findAllNative(requete);
    }
}
