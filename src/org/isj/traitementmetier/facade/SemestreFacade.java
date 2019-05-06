package org.isj.traitementmetier.facade;

import org.isj.traitementmetier.entites.AnneeAcademique;
import org.isj.traitementmetier.entites.Semestre;

import java.util.Date;
import java.util.List;

public class SemestreFacade extends AbstractFacade<Semestre> {

    public SemestreFacade(){
        super(Semestre.class);
    }

    public String enregistrer(String libelle, String description, Date dateDebut, Date dateCloture, AnneeAcademique anneeAcademique){
        Semestre semestre = new Semestre(libelle,description,dateDebut,dateCloture,anneeAcademique);
        return create(semestre);
    }

    public String modifier(Semestre semestre,String libelle,String description,Date dataDebut,Date dateCloture,AnneeAcademique anneeAcademique){
        semestre.setLibelle(libelle);
        semestre.setDescription(description);
        semestre.setDateDebut(dataDebut);
        semestre.setDateCloture(dateCloture);
        semestre.setAnneeAcademique(anneeAcademique);
        return merge(semestre);
    }

    public String supprimer(Semestre semestre){
        return remove(semestre);
    }

    public List<Semestre> lister(){
        return findAll();
    }

    public List<Semestre> lister(String requete){
        return findAllNative(requete);
    }
}
