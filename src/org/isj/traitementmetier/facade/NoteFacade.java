package org.isj.traitementmetier.facade;

import org.isj.traitementmetier.entites.Anonymat;
import org.isj.traitementmetier.entites.EstInscrit;
import org.isj.traitementmetier.entites.Evaluation;
import org.isj.traitementmetier.entites.Note;

import java.util.List;

public class NoteFacade extends AbstractFacade <Note> {

    public NoteFacade(){
        super(Note.class);
    }

    public String enregistrer(String libelle,String description,double valeurNote,int numeroTable,Anonymat anonymat, EstInscrit estInscrit, Evaluation evaluation){
        Note note = new Note(libelle,description,valeurNote,numeroTable,anonymat,estInscrit,evaluation);
        return create(note);
    }

    public String modifier(Note note, String libelle, String description, double valeurNote, int numeroTable, Anonymat anonymat, EstInscrit estInscrit, Evaluation evaluation){
        note.setLibelle(libelle);
        note.setDescription(description);
        note.setValeurNote(valeurNote);
        note.setNumeroTable(numeroTable);
        note.setAnonymat(anonymat);
        note.setEstInscrit(estInscrit);
        note.setEvaluation(evaluation);
        return merge(note);
    }

    public String supprimer(Note note){
        return remove(note);
    }

    public List<Note> lister(){
        return findAll();
    }

    public List<Note> lister(String requete){
        return findAllNative(requete);
    }
}
