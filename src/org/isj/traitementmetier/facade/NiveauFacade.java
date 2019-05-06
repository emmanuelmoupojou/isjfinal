package org.isj.traitementmetier.facade;

import org.isj.traitementmetier.entites.Niveau;

import java.util.List;

public class NiveauFacade extends AbstractFacade <Niveau> {

    public NiveauFacade(){
        super(Niveau.class);
    }

    public String enregistrer(String libelle,String description,int numero){
        Niveau niveau = new Niveau(libelle,description,numero);
        return create(niveau);
    }

    public String modifier(Niveau niveau,String libelle,String description,int numero){
        niveau.setLibelle(libelle);
        niveau.setDescription(description);
        niveau.setNumero(numero);
        return merge(niveau);
    }

    public String supprimer(Niveau niveau){
        return remove(niveau);
    }

    public List<Niveau> lister(){
        return findAll();
    }

    public List<Niveau> lister(String requete){
        return findAllNative(requete);
    }

}
