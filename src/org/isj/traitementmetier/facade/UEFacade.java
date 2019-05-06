package org.isj.traitementmetier.facade;

import org.isj.traitementmetier.entites.Module;
import org.isj.traitementmetier.entites.Niveau;
import org.isj.traitementmetier.entites.Specialite;
import org.isj.traitementmetier.entites.UE;

import java.util.List;

public class UEFacade extends AbstractFacade <UE> {

    public UEFacade(){
        super(UE.class);
    }

    public String enregistrer(String libelle, String description, String codeUE, UE.Statut statut, Module module, Niveau niveau, Specialite specialite){
        UE ue = new UE(libelle,description,codeUE,statut,module,niveau,specialite);
        return create(ue);
    }

    public String modifier(UE ue, String libelle, String description, String codeUE, UE.Statut statut,Module module, Niveau niveau, Specialite specialite){
        ue.setLibelle(libelle);
        ue.setDescription(description);
        ue.setCodeUE(codeUE);
        ue.setStatut(statut);
        ue.setModule(module);
        ue.setNiveau(niveau);
        ue.setSpecialite(specialite);
        return merge(ue);
    }

    public String supprimer(UE ue){
        return remove(ue);
    }

    public List<UE> lister(){
        return findAll();
    }

    public List<UE> lister(String requete){
        return findAllNative(requete);
    }
}
