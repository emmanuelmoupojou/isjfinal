package org.isj.traitementmetier.facade;

import org.isj.traitementmetier.entites.Anonymat;
import org.isj.traitementmetier.entites.Evaluation;
import org.isj.traitementmetier.entites.Note;

import java.util.List;

public class AnonymatFacade extends AbstractFacade<Anonymat> {
    public AnonymatFacade() {
        super(Anonymat.class);
    }

    public String enregistrer(String libelle, String description, int numeroAnonymat, Note note, Evaluation evaluation){
        Anonymat anonymat = new Anonymat(libelle,description,numeroAnonymat,note,evaluation);
        return create(anonymat);
    }

    public String modifier(Anonymat anonymat,String libelle,String description,int numeroAnonymat,Note note, Evaluation evaluation){
        anonymat.setLibelle(libelle);
        anonymat.setDescription(description);
        anonymat.setNumeroAnonymat(numeroAnonymat);
        anonymat.setNote(note);
        anonymat.setEvaluation(evaluation);
        return merge(anonymat);
    }

    public String supprimer(Anonymat anonymat){
        return remove(anonymat);
    }

    public List<Anonymat> lister(){
        return findAll();
    }

    public List<Anonymat> lister(String requete){
        return findAllNative(requete);
    }
}
