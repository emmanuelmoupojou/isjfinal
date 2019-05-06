package org.isj.traitementmetier.facade;

import org.isj.traitementmetier.entites.Enseignement;
import org.isj.traitementmetier.entites.Semestre;
import org.isj.traitementmetier.entites.UE;

import java.util.List;

public class EnseignementFacade extends AbstractFacade <Enseignement> {

    public EnseignementFacade(){
        super(Enseignement.class);
    }

    public String enregistrer(String libelle, String description, int heuresCours, String programmeDeCours, Semestre semestre, UE ue){
        Enseignement enseignement = new Enseignement(libelle,description,heuresCours,programmeDeCours,semestre,ue);
        return create(enseignement);
    }

    public String modifier(Enseignement enseignement,String libelle,String description,int heuresCours,String programmeCours,Semestre semestre, UE ue){
        enseignement.setLibelle(libelle);
        enseignement.setDescription(description);
        enseignement.setHeuresDeCours(heuresCours);
        enseignement.setProgrammeDeCours(programmeCours);
        enseignement.setSemestre(semestre);
        enseignement.setUe(ue);
        return merge(enseignement);
    }

    public String supprimer(Enseignement enseignement){
        return remove(enseignement);
    }

    public List<Enseignement> lister(){
        return findAll();
    }

    public List<Enseignement> lister(String requete){
        return findAllNative(requete);
    }
}
