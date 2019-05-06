/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.isj.traitementmetier.facade;

import org.isj.traitementmetier.entites.Filiere;

import java.util.List;


public class FiliereFacade extends AbstractFacade<Filiere> {

    public FiliereFacade() {
        super(Filiere.class);
    }

    public String enregistrer(String libelle,String description){
        Filiere filiere = new Filiere(libelle,description);
        return create(filiere);
    }

    public String modifier(Filiere filiere,String libelle,String description){
        filiere.setLibelle(libelle);
        filiere.setDescription(description);
        return merge(filiere);
    }

    public String supprimer(Filiere filiere){
        return remove(filiere);
    }

    public List <Filiere> lister(){
        return findAll();
    }

    public List<Filiere> lister(String requete){
        return findAllNative(requete);
    }
}
