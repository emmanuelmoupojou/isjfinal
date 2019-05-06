package org.isj.traitementmetier.facade;

import org.isj.traitementmetier.entites.Classe;

import java.util.List;

public class ClasseFacade extends AbstractFacade<Classe>{

    public ClasseFacade(){
        super(Classe.class);
    }

    public String enregistrer(String libelle,String description){
        Classe classe = new Classe(libelle,description);
        return create(classe);
    }

    public String modifier(Classe classe,String libelle,String description){
        classe.setLibelle(libelle);
        classe.setDescription(description);
        return merge(classe);
    }

    public String supprimer(Classe classe){
        return remove(classe);
    }

    public List<Classe> lister(){
        return findAll();
    }

    public List<Classe> lister(String requete){
        return findAllNative(requete);
    }
}
