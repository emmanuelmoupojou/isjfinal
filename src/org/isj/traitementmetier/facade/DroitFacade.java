package org.isj.traitementmetier.facade;

import org.isj.traitementmetier.entites.Droit;
import org.isj.traitementmetier.entites.Role;

import java.util.List;

public class DroitFacade extends AbstractFacade<Droit> {

    public DroitFacade(){
        super(Droit.class);
    }
    public String enregistrer(String libelle, String description, String categorie, boolean lecture, boolean ecriture, boolean modification, boolean suppression, Role role){
        Droit droit = new Droit(libelle,description,categorie,lecture,ecriture,modification,suppression,role);
        return create(droit);
    }

    public String modifier(Droit droit,String libelle,String description,String categorie,boolean lecture,boolean ecriture,boolean modification,boolean suppression,Role role){
        droit.setLibelle(libelle);
        droit.setDescription(description);
        droit.setCategorie(categorie);
        droit.setEcriture(ecriture);
        droit.setLecture(lecture);
        droit.setModification(modification);
        droit.setSuppression(suppression);
        droit.setRole(role);
        return merge(droit);
    }

    public String supprimer(Droit droit){
        return remove(droit);
    }

    public List<Droit> lister(){
        return findAll();
    }

    public List<Droit> lister(String requete){
        return findAllNative(requete);
    }
}
