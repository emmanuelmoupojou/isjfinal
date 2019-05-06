package org.isj.traitementmetier.facade;

import org.isj.traitementmetier.entites.AnneeAcademique;

import java.util.Date;
import java.util.List;

public class AnneeAcademiqueFacade extends AbstractFacade <AnneeAcademique> {

    public AnneeAcademiqueFacade(){super(AnneeAcademique.class);}

    public String enregistrer(String libelle, String description, Date dateDebut,Date dateCloture){
        AnneeAcademique anneeAcademique = new AnneeAcademique(libelle,description,dateDebut,dateCloture);
        return create(anneeAcademique);
    }

    public String modifier(AnneeAcademique anneeAcademique,String libelle,String description,Date dataDebut,Date dateCloture){
        anneeAcademique.setLibelle(libelle);
        anneeAcademique.setDescription(description);
        anneeAcademique.setDateDebut(dataDebut);
        anneeAcademique.setDateCloture(dateCloture);
        return merge(anneeAcademique);
    }

    public String supprimer(AnneeAcademique anneeAcademique){
        return remove(anneeAcademique);
    }

    public List<AnneeAcademique> lister(){
        return findAll();
    }

    public List<AnneeAcademique> lister(String requete){
        return findAllNative(requete);
    }
}
