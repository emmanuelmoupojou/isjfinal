package org.isj.traitementmetier.facade;

import org.isj.traitementmetier.entites.HistoriqueNote;
import org.isj.traitementmetier.entites.Note;

import java.util.List;

public class HistoriqueNoteFacade extends AbstractFacade<HistoriqueNote> {

    public HistoriqueNoteFacade(){
        super(HistoriqueNote.class);
    }
    public String enregistrer(String libelle, String description, double valeurNote, Note note){
        HistoriqueNote historiqueNote = new HistoriqueNote(libelle,description,valeurNote,note);
        return create(historiqueNote);
    }

    public String modifier(HistoriqueNote historiqueNote,String libelle,String description,double valeurNote,Note note){
        historiqueNote.setLibelle(libelle);
        historiqueNote.setDescription(description);
        historiqueNote.setValeurNote(valeurNote);
        historiqueNote.setNote(note);
        return merge(historiqueNote);
    }

    public String supprimer(HistoriqueNote historiqueNote){
        return remove(historiqueNote);
    }

    public List<HistoriqueNote> lister(){
        return findAll();
    }

    public List<HistoriqueNote> lister(String requete){
        return findAllNative(requete);
    }
}
