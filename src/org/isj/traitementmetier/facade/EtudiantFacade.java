package org.isj.traitementmetier.facade;

import org.isj.traitementmetier.entites.Classe;
import org.isj.traitementmetier.entites.Etudiant;
import org.isj.traitementmetier.entites.Personne;

import java.util.Date;
import java.util.List;

public class EtudiantFacade extends AbstractFacade<Etudiant> {

    public EtudiantFacade(){
        super(Etudiant.class);
    }

    public String enregistrer(String libelle, String description, String nom, String prenom, String email, int telephone, Date dateNaissance, Personne.Sexe sexe, Personne.Statut statut, String nomMere, String nomPere, int telephoneMere, int telephonePere, String professionPere, String professionMere, String regionOrigine, String ecoleOrigine, String matricule, String codeAuthentification, Classe classe){
        Etudiant etudiant = new Etudiant(libelle,description,nom,prenom,email,telephone,dateNaissance,sexe,statut,nomMere,nomPere,telephoneMere,telephonePere,professionPere,professionMere,regionOrigine,ecoleOrigine,classe,matricule,codeAuthentification);
        return create(etudiant);
    }

    public String modifier(Etudiant etudiant,String libelle, String description, String nom, String prenom, String email, int telephone, Date dateNaissance, Personne.Sexe sexe, Personne.Statut statut,String nomMere,String nomPere,int telephoneMere,int telephonePere,String professionPere,String professionMere,String regionOrigine,String ecoleOrigine,String matricule,String codeAuthentification,Classe classe){
        etudiant.setLibelle(libelle);
        etudiant.setDescription(description);
        etudiant.setNom(nom);
        etudiant.setPrenom(prenom);
        etudiant.setEmail(email);
        etudiant.setTelephone(telephone);
        etudiant.setDateNaissance(dateNaissance);
        etudiant.setSexe(sexe);
        etudiant.setStatut(statut);
        etudiant.setNomDeLaMere(nomMere);
        etudiant.setNomDuPere(nomPere);
        etudiant.setTelephoneDeLaMere(telephoneMere);
        etudiant.setTelephoneDuPere(telephonePere);
        etudiant.setProfessionDuPere(professionPere);
        etudiant.setProfessionDelaMere(professionMere);
        etudiant.setRegionOrigine(regionOrigine);
        etudiant.setEcoleOrigine(ecoleOrigine);
        etudiant.setClasse(classe);
        return merge(etudiant);
    }

    public String supprimer(Etudiant etudiant){
        return remove(etudiant);
    }

    public List<Etudiant> lister(){
        return findAll();
    }

    public List<Etudiant> lister(String requete){
        return findAllNative(requete);
    }
}
