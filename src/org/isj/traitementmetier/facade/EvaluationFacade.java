package org.isj.traitementmetier.facade;

import org.isj.traitementmetier.entites.Evaluation;
import org.isj.traitementmetier.entites.TypeEvaluation;

import java.util.Date;
import java.util.List;

public class EvaluationFacade extends AbstractFacade <Evaluation> {

    public EvaluationFacade(){
        super(Evaluation.class);
    }

    public String enregistrer(String libelle, String description, Date dateEval, Evaluation.Statut statut, TypeEvaluation typeEvaluation){
        Evaluation evaluation = new Evaluation(libelle,description,dateEval,statut,typeEvaluation);
        return create(evaluation);
    }

    public String modifier(Evaluation evaluation, String libelle, String description, Date dateEval, Evaluation.Statut statut,TypeEvaluation typeEvaluation){
        evaluation.setLibelle(libelle);
        evaluation.setDescription(description);
        evaluation.setDateEval(dateEval);
        evaluation.setStatut(statut);
        evaluation.setTypeEvaluation(typeEvaluation);
        return merge(evaluation);
    }

    public String supprimer(Evaluation evaluation){
        return remove(evaluation);
    }

    public List<Evaluation> lister(){
        return findAll();
    }

    public List<Evaluation> lister(String requete){
        return findAllNative(requete);
    }
}
