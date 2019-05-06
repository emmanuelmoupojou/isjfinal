package org.isj.traitementmetier.entites;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

@XmlRootElement
@Entity
@Table(name = "historique_note")
public class HistoriqueNote extends Securite implements Serializable {

    
    @Column(name = "valeur_note", nullable = false)
    private double valeurNote;
    
    @ManyToOne
    @JoinColumn(name = "note")
    private Note note;

    public HistoriqueNote(String libelle, String description, double valeurNote, Note note) {
        super(libelle, description);
        this.valeurNote = valeurNote;
        this.note = note;
    }

    public HistoriqueNote(){}

    public double getValeurNote() {
        return valeurNote;
    }

    public void setValeurNote(double valeurNote) {
        this.valeurNote = valeurNote;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    @Override
    public String getLibelle(){
        return note.getLibelle() + "-" + valeurNote;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), note);
    }

    @Override
    public String toString() {
        return "HistoriqueNote{" +
                ", note=" + note.toString() +
                ", valeurNote=" + valeurNote +
                "} " + super.toString();
    }
}
