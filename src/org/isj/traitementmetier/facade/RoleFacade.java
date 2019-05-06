package org.isj.traitementmetier.facade;

import org.isj.traitementmetier.entites.Role;

import java.util.List;

public class RoleFacade extends AbstractFacade<Role> {

    public RoleFacade(){
        super(Role.class);
    }

    public String enregistrer(String libelle,String description){
        Role role = new Role(libelle,description);
        return create(role);
    }

    public String modifier(Role role,String libelle,String description){
        role.setLibelle(libelle);
        role.setDescription(description);
        return merge(role);
    }

    public String supprimer(Role role){
        return remove(role);
    }

    public List<Role> lister(){
        return findAll();
    }

    public List<Role> lister(String requete){
        return findAllNative(requete);
    }
}
