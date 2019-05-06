package org.isj.traitementmetier.facade;

import org.isj.traitementmetier.entites.Enseignant;
import org.isj.traitementmetier.entites.Personne;

import java.util.Date;
import java.util.List;

public class EnseignantFacade extends AbstractFacade<Enseignant> {

    public EnseignantFacade(){
        super(Enseignant.class);
    }

    public String enregistrer(String libelle, String description, String nom, String prenom, String email, int telephone, Date dateNaissance, Personne.Sexe sexe, Personne.Statut statut,String qualification){
        Enseignant enseignant = new Enseignant(libelle,description,nom,prenom,email,telephone,dateNaissance,sexe,statut,qualification);
        return create(enseignant);
    }

    public String modifier(Enseignant enseignant,String libelle, String description, String nom, String prenom, String email, int telephone, Date dateNaissance, Personne.Sexe sexe, Personne.Statut statut,String qualification){
        enseignant.setLibelle(libelle);
        enseignant.setDescription(description);
        enseignant.setNom(nom);
        enseignant.setPrenom(prenom);
        enseignant.setEmail(email);
        enseignant.setTelephone(telephone);
        enseignant.setDateNaissance(dateNaissance);
        enseignant.setSexe(sexe);
        enseignant.setStatut(statut);
        enseignant.setQualification(qualification);
        return merge(enseignant);
    }

    public String supprimer(Enseignant enseignant){
        return remove(enseignant);
    }

    public List<Enseignant> lister(){
        return findAll();
    }

    public List<Enseignant> lister(String requete){
        return findAllNative(requete);
    }
}
