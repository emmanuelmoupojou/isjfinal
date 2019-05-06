package org.isj.traitementmetier.facade;

import org.isj.traitementmetier.entites.Module;

import java.util.List;

public class ModuleFacade extends AbstractFacade <Module> {

    public ModuleFacade(){
        super(Module.class);
    }
    public String enregistrer(String libelle,String description){
        Module module = new Module(libelle,description);
        return create(module);
    }

    public String modifier(Module module,String libelle,String description){
        module.setLibelle(libelle);
        module.setDescription(description);
        return merge(module);
    }

    public String supprimer(Module module){
        return remove(module);
    }

    public List<Module> lister(){
        return findAll();
    }

    public List<Module> lister(String requete){
        return findAllNative(requete);
    }
}
