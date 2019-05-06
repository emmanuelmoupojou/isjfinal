/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.isj.traitementmetier.facade;

import org.isj.traitementmetier.entites.Personne;
import org.isj.traitementmetier.entites.Utilisateur;

import java.util.Date;
import java.util.List;


public class UtilisateurFacade extends AbstractFacade<Utilisateur> {

    public UtilisateurFacade() {
        super(Utilisateur.class);
    }

    public String enregistrer(String libelle, String description, String nom, String prenom, String email, int telephone, Date dateNaissance, Personne.Sexe sexe, Personne.Statut statut, String login, String motDePasse){
        Utilisateur utilisateur = new Utilisateur(libelle,description,nom,prenom,email,telephone,dateNaissance,sexe,statut,login,motDePasse);
        return create(utilisateur);
    }

    public String modifier(Utilisateur utilisateur,String libelle, String description, String nom, String prenom, String email, int telephone, Date dateNaissance, Personne.Sexe sexe, Personne.Statut statut,String login,String motDePasse){
        utilisateur.setLibelle(libelle);
        utilisateur.setDescription(description);
        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        utilisateur.setEmail(email);
        utilisateur.setTelephone(telephone);
        utilisateur.setDateNaissance(dateNaissance);
        utilisateur.setSexe(sexe);
        utilisateur.setStatut(statut);
        utilisateur.setLogin(login);
        utilisateur.setMotDePasse(motDePasse);
        return merge(utilisateur);
    }

    public String supprimer(Utilisateur utilisateur){
        return remove(utilisateur);
    }

    public List<Utilisateur> lister(){
        return findAll();
    }

    public List<Utilisateur> lister(String requete){
        return findAllNative(requete);
    }
    
}
