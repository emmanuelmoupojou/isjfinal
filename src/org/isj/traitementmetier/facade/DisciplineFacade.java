package org.isj.traitementmetier.facade;

import org.isj.traitementmetier.entites.Discipline;
import org.isj.traitementmetier.entites.Etudiant;
import org.isj.traitementmetier.entites.Semestre;

import java.util.List;

public class DisciplineFacade extends AbstractFacade<Discipline> {

    public DisciplineFacade() {
        super(Discipline.class);
    }

    public String enregistrer(String libelle, String description, int nbHeures, int nbRetards, Etudiant etudiant, Semestre semestre){
        Discipline discipline = new Discipline(libelle,description,etudiant,semestre,nbHeures,nbRetards);
        return create(discipline);
    }

    public String modifier(Discipline discipline,String libelle,String description,int nbHeures,int nbRetards,Etudiant etudiant, Semestre semestre){
        discipline.setLibelle(libelle);
        discipline.setDescription(description);
        discipline.setNbHeures(nbHeures);
        discipline.setNbRetards(nbRetards);
        discipline.setEtudiant(etudiant);
        discipline.setSemestre(semestre);
        return merge(discipline);
    }

    public String supprimer(Discipline discipline){
        return remove(discipline);
    }

    public List<Discipline> lister(){
        return findAll();
    }

    public List<Discipline> lister(String requete){
        return findAllNative(requete);
    }
}
