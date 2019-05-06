package org.isj.traitementmetier.facade;

import org.isj.traitementmetier.entites.Filiere;
import org.isj.traitementmetier.entites.Specialite;

import java.util.List;

public class SpecialiteFacade extends AbstractFacade<Specialite> {

    public SpecialiteFacade(){
        super(Specialite.class);
    }

    public String enregistrer(String libelle, String description, Filiere filiere){
        Specialite specialite = new Specialite(libelle,description,filiere);
        return create(specialite);
    }

    public String modifier(Specialite specialite,String libelle,String description,Filiere filiere){
        specialite.setLibelle(libelle);
        specialite.setDescription(description);
        specialite.setFiliere(filiere);
        return merge(specialite);
    }

    public String supprimer(Specialite specialite){
        return remove(specialite);
    }

    public List<Specialite> lister(){
        return findAll();
    }

    public List<Specialite> lister(String requete){
        return findAllNative(requete);
    }
}
