package org.isj.traitementmetier.facade;

import org.isj.traitementmetier.entites.Enseignement;
import org.isj.traitementmetier.entites.TypeEvaluation;

import java.util.List;

public class TypeEvaluationFacade extends AbstractFacade<TypeEvaluation> {

    public TypeEvaluationFacade(){
        super(TypeEvaluation.class);
    }

    public String enregistrer(String libelle, String description, float pourcentage, Enseignement enseignement){
        TypeEvaluation typeEvaluation = new TypeEvaluation(libelle,description,pourcentage,enseignement);
        return create(typeEvaluation);
    }

    public String modifier(TypeEvaluation typeEvaluation,String libelle,String description,float pourcentage,Enseignement enseignement){
        typeEvaluation.setLibelle(libelle);
        typeEvaluation.setDescription(description);
        typeEvaluation.setPourcentage(pourcentage);
        typeEvaluation.setEnseignement(enseignement);
        return merge(typeEvaluation);
    }

    public String supprimer(TypeEvaluation typeEvaluation){
        return remove(typeEvaluation);
    }

    public List<TypeEvaluation> lister(){
        return findAll();
    }

    public List<TypeEvaluation> lister(String requete){
        return findAllNative(requete);
    }
}
